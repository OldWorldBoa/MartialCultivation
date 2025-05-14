package com.djb.martial_cultivation.gui.widget;

import com.djb.martial_cultivation.capabilities.Cultivator;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkill;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkillFactory;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkillType;
import com.djb.martial_cultivation.exceptions.SkillNotImplementedException;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.function.Consumer;

public class SkillSelector extends Widget {
    private final ArrayList<SkillContainer> skills = new ArrayList<>();

    private final Minecraft mc;
    private final Consumer<CultivationSkill> skillConsumer;

    public SkillSelector(
            Minecraft mc,
            int x,
            int y,
            int width,
            int height,
            PlayerEntity player,
            Consumer<CultivationSkill> skillConsumer) throws SkillNotImplementedException {
        super(x, y, width, height, new StringTextComponent(""));

        this.mc = mc;
        this.skillConsumer = skillConsumer;

        createSkillContainers(Cultivator.getCultivatorFrom(player).getSkills());
    }

    private void createSkillContainers(ArrayList<CultivationSkill> cultivationSkills) throws SkillNotImplementedException {
        IdentityHashMap<CultivationSkillType, ArrayList<CultivationSkill>> groupedSkills = getGroupedSkills(cultivationSkills);

        if (groupedSkills.size() > 0) {
            int padding = 3;
            int currX = this.x + padding;
            int currY = this.y + padding;

            for (CultivationSkillType type: groupedSkills.keySet()) {
                this.skills.add(
                        new SkillContainer(this.mc, CultivationSkillFactory.createFromType(type), currX, currY));

                int skillWidth = 18;
                currX += skillWidth + padding;

                for (CultivationSkill skill: groupedSkills.get(type)) {
                    this.skills.add(new SkillContainer(this.mc, skill, currX, currY, this.skillConsumer));

                    currX += skillWidth + padding;
                }

                int skillHeight = 18;
                currY += skillHeight + padding;
                currX = this.x + padding;
            }
        }
    }

    private IdentityHashMap<CultivationSkillType, ArrayList<CultivationSkill>> getGroupedSkills(ArrayList<CultivationSkill> cultivationSkills) {
        IdentityHashMap<CultivationSkillType, ArrayList<CultivationSkill>> groupedSkills = new IdentityHashMap<>();

        for (CultivationSkill skill : cultivationSkills) {
            if (!groupedSkills.containsKey(skill.getSkillType())) {
                groupedSkills.put(skill.getSkillType(), new ArrayList<>());
            }

            groupedSkills.get(skill.getSkillType()).add(skill);
        }

        return groupedSkills;
    }

    public ArrayList<SkillContainer> getNestedListeners() {
        return this.skills;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        for (SkillContainer skillContainer : this.skills) {
            skillContainer.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }
}
