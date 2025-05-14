package com.airistotal.martial_cultivation.capabilities;

import com.airistotal.martial_cultivation.Main;
import com.airistotal.martial_cultivation.capabilities.skills.CultivationSkill;
import com.airistotal.martial_cultivation.capabilities.skills.ToolSkillGroup;
import com.airistotal.martial_cultivation.capabilities.skills.ToolSkillSettings;
import com.airistotal.martial_cultivation.exceptions.NotEnoughQiException;
import com.airistotal.martial_cultivation.helpers.ListHelpers;
import com.airistotal.martial_cultivation.helpers.SerializableConverter;
import com.airistotal.martial_cultivation.network.messages.SaveCultivationState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoundationCultivator implements Cultivator, INBTSerializable<CompoundNBT>, Serializable {
    private int storedQi = 0;
    private int maxQi = 100;
    private boolean isEnabled = false;
    private boolean isEnteringCultivation = false;
    private boolean isInCultivation = false;
    private boolean isExitingCultivation = false;

    private final ArrayList<CultivationSkill> learnedSkills;
    private final ArrayList<ToolSkillSettings> toolSkillSettings;

    public FoundationCultivator() {
        this.learnedSkills = new ArrayList<>();
        this.toolSkillSettings = new ArrayList<>();
    }

    @Override
    public void loadCultivator(Cultivator savedCultivator) {
        this.storedQi = savedCultivator.getStoredQi();
        this.maxQi = savedCultivator.getMaxQi();
        this.isEnabled = savedCultivator.isEnabled();
        this.learnedSkills.addAll(ListHelpers.getDistinctFrom(savedCultivator.getSkills(), CultivationSkill::getSkillId));

        if(savedCultivator.getAllToolSkillSettings() != null) {
            this.toolSkillSettings.addAll(savedCultivator.getAllToolSkillSettings());
        }
    }

    @Override
    public void setIsCultivating(boolean isCultivating) {
        this.isInCultivation = isCultivating;
    }

    @Override
    public void cultivate() {
        this.maxQi += 10;
    }

    @Override
    public void regenerateQi() {
        int regenerateQiAmount = 5;
        this.storedQi += regenerateQiAmount;

        this.qiChangeInvariant();
    }

    @Override
    public void setQi(int qi) {
        this.storedQi = qi;

        this.qiChangeInvariant();
    }

    @Override
    public void useQi(int qi) throws NotEnoughQiException {
        if (qi > this.storedQi) {
            throw new NotEnoughQiException();
        }

        this.storedQi -= qi;
    }

    @Override
    public int getStoredQi() {
        return this.storedQi;
    }

    private void qiChangeInvariant() {
        if (this.storedQi > this.maxQi) {
            this.storedQi = this.maxQi;
        }
    }

    @Override
    public int getMaxQi() {
        return this.maxQi;
    }

    @Override
    public void toggleActiveCultivation() {
        if (!this.isEnteringCultivation && !this.isExitingCultivation) {
            if (this.isInCultivation) {
                this.exitCultivation();
            } else {
                this.enterCultivation();
            }
        }
    }

    @Override
    public boolean isEnteringCultivation() {
        return this.isEnteringCultivation;
    }

    @Override
    public boolean isExitingCultivation() {
        return this.isExitingCultivation;
    }

    @Override
    public boolean isCultivating() {
        return this.isInCultivation;
    }

    private void enterCultivation() {
        Main.LOGGER.debug("Entering cultivation");
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;
        mc.player.sendChatMessage("Entering cultivation.");
        this.isEnteringCultivation = true;

        FoundationCultivator context = this;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Main.NETWORK_CHANNEL.sendToServer(new SaveCultivationState(
                                mc.player.getEntityId(),
                                true));

                        mc.gameSettings.setPointOfView(PointOfView.THIRD_PERSON_FRONT);
                        mc.player.movementInput = new MovementInput();

                        Main.LOGGER.debug("Entered cultivation");
                        mc.player.sendChatMessage("Entered cultivation.");
                        context.isEnteringCultivation = false;
                        context.isInCultivation = true;

                        while (Cultivator.getCultivatorFrom(mc.player).isCultivating()) {
                            addCultivationParticles(mc);

                            try {
                                Thread.sleep(150);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                1000
        );
    }

    private void addCultivationParticles(Minecraft mc) {
        Random rand = new Random();
        BlockPos pos = mc.player.getPosition();
        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                if (i > -2 && i < 2 && j == -1) {
                    j = 2;
                }

                mc.world.addParticle(
                        ParticleTypes.ENCHANT,
                        pos.getX() + 0.5,
                        pos.getY() + 2,
                        pos.getZ(),
                        (double)((float)i + rand.nextFloat()) - 0.5D,
                        (float)1 - rand.nextFloat() - 1.0F,
                        (double)((float)j + rand.nextFloat()) - 0.5D);
            }
        }
    }

    private void exitCultivation() {
        Main.LOGGER.debug("Exiting cultivation");
        Minecraft mc = Minecraft.getInstance();
        mc.player.sendChatMessage("Exiting cultivation.");
        this.isExitingCultivation = true;

        FoundationCultivator context = this;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Main.NETWORK_CHANNEL.sendToServer(new SaveCultivationState(
                                mc.player.getEntityId(),
                                false));

                        mc.gameSettings.setPointOfView(PointOfView.FIRST_PERSON);
                        mc.player.movementInput = new MovementInputFromOptions(mc.gameSettings);

                        Main.LOGGER.debug("Exited cultivation");
                        Minecraft.getInstance().player.sendChatMessage("Exited cultivation.");
                        context.isExitingCultivation = false;
                        context.isInCultivation = false;
                    }
                },
                1000
        );
    }

    @Override
    public void learnSkill(CultivationSkill skill) {
        if (!this.learnedSkills.stream().anyMatch(x -> x.getSkillId() == skill.getSkillId())) {
            this.learnedSkills.add(skill);
        }
    }

    @Override
    public void setToolSkillSettings(ToolSkillSettings toolSkillSettings) {
        this.toolSkillSettings.removeIf((x) -> x.toolSkillGroup == toolSkillSettings.toolSkillGroup);
        this.toolSkillSettings.add(toolSkillSettings);
    }

    @Override
    public ArrayList<CultivationSkill> getSkills() {
        return this.learnedSkills;
    }

    @Override
    public ArrayList<ToolSkillSettings> getAllToolSkillSettings() {
        return this.toolSkillSettings;
    }

    @Override
    public ToolSkillSettings getToolSkillSettings(ToolSkillGroup group) {
        if (this.toolSkillSettings.stream().noneMatch(x -> x.toolSkillGroup == group)) {
            ToolSkillSettings newToolSkillSettings = new ToolSkillSettings();
            newToolSkillSettings.toolSkillGroup = group;
            this.toolSkillSettings.add(newToolSkillSettings);

            return newToolSkillSettings;
        }

        return toolSkillSettings.stream().filter(x -> x.toolSkillGroup == group).findFirst().get();
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("storedQi", this.storedQi);
        nbt.putInt("maxQi", this.maxQi);
        nbt.putBoolean("isEnabled", this.isEnabled);
        nbt.putBoolean("isInCultivation", this.isInCultivation);
        nbt.putBoolean("isEnteringCultivation", this.isEnteringCultivation);
        nbt.putBoolean("isExitingCultivation", this.isExitingCultivation);
        nbt.putString("toolSkillSettings", SerializableConverter.Serialize(this.toolSkillSettings));
        nbt.putString("learnedSkills", SerializableConverter.Serialize(this.learnedSkills));

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt != null) {
            this.storedQi = nbt.getInt("storedQi");
            this.maxQi = nbt.getInt("maxQi");
            this.isEnabled = nbt.getBoolean("isEnabled");
            this.isInCultivation = nbt.getBoolean("isInCultivation");
            this.isEnteringCultivation = nbt.getBoolean("isEnteringCultivation");
            this.isExitingCultivation = nbt.getBoolean("isExitingCultivation");

            List<ToolSkillSettings> toolSkillSettings = SerializableConverter.Deserialize(nbt.getString("toolSkillSettings"));
            if (toolSkillSettings != null) {
                this.toolSkillSettings.addAll(toolSkillSettings);
            }

            List<CultivationSkill> skills = SerializableConverter.Deserialize(nbt.getString("learnedSkills"));
            if (skills != null) {
                this.learnedSkills.addAll(skills);
            }
        }
    }
}
