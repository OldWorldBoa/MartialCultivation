package com.djb.martial_cultivation.gui.widget;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkill;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkillFactory;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillGroup;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillSettings;
import com.djb.martial_cultivation.exceptions.SkillNotImplementedException;
import com.djb.martial_cultivation.gui.renderer.ToolSkillGroupRenderer;
import com.djb.martial_cultivation.helpers.StringHelpers;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import java.util.IdentityHashMap;

public class ToolSkillSettingsWidget extends Widget {
    private static final ResourceLocation tool_skill_settings = new ResourceLocation(Main.MOD_ID, "textures/gui/tool_skill_settings.png");
    private CultivationSkill selectedSkill;
    private ToolSkillSettings currentToolSkillSetting;
    private final Cultivator cultivator;
    private final Minecraft mc;
    private final IdentityHashMap<String, SkillContainer> skillContainers = new IdentityHashMap<>();

    private static final String LEFT_CLICK = "left_click";
    private static final String LEFT_HOLD = "left_hold";
    private static final String MIDDLE_CLICK = "middle_click";
    private static final String RIGHT_CLICK = "right_click";
    private static final String RIGHT_HOLD = "right_hold";

    private static final int widgetHeight = 64;
    private static final int widgetWidth = 64;

    public ToolSkillSettingsWidget(Minecraft mc, int x, int y, PlayerEntity player) throws SkillNotImplementedException {
        super(x, y, widgetWidth, widgetHeight, new StringTextComponent(" "));

        cultivator = Cultivator.getCultivatorFrom(player);
        this.mc = mc;
        this.currentToolSkillSetting = this.cultivator.getToolSkillSettings(ToolSkillGroup.UNARMED);

        this.addSkillContainers();
    }

    private void addSkillContainers() {
        skillContainers.put(LEFT_CLICK, new SkillContainer(this.mc, null, this.x + 2, this.y + 46));
        skillContainers.put(LEFT_HOLD, new SkillContainer(this.mc, null, this.x + 23, this.y + 46));
        skillContainers.put(MIDDLE_CLICK, new SkillContainer(this.mc, null, this.x + 44, this.y + 46));
        skillContainers.put(RIGHT_HOLD, new SkillContainer(this.mc, null, this.x + 44, this.y + 25));
        skillContainers.put(RIGHT_CLICK, new SkillContainer(this.mc, null, this.x + 44, this.y + 4));

        updateSkillContainers();
    }

    public void setSelectedToolSkillGroup(ToolSkillGroup toolSkillGroup) {
        if (toolSkillGroup != null) {
            this.currentToolSkillSetting = this.cultivator.getToolSkillSettings(toolSkillGroup);

            updateSkillContainers();
        }
    }

    private void updateSkillContainers() {
        updateSkillContainer(RIGHT_CLICK, this.currentToolSkillSetting.rightClickSkillId);
        updateSkillContainer(RIGHT_HOLD, this.currentToolSkillSetting.rightHoldSkillId);
        updateSkillContainer(MIDDLE_CLICK, this.currentToolSkillSetting.middleClickSkillId);
        updateSkillContainer(LEFT_CLICK, this.currentToolSkillSetting.leftClickSkillId);
        updateSkillContainer(LEFT_HOLD, this.currentToolSkillSetting.leftHoldSkillId);
    }

    private void updateSkillContainer(String key, String skillId){
        CultivationSkill currSkill = null;
        if (!StringHelpers.isNullOrWhitespace(skillId)) {
            try {
                currSkill = CultivationSkillFactory.create(skillId);
            } catch (SkillNotImplementedException e) {
                Main.LOGGER.error(e);
            }
        }

        this.skillContainers.get(key).setSkill(currSkill);
    }

    public void setSelectedSkill(CultivationSkill skill) {
        this.selectedSkill = skill;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);

        ToolSkillGroupRenderer.RenderIconFor(
                this.currentToolSkillSetting.toolSkillGroup,
                this,
                matrixStack,
                this.x + 6,
                this.y + 8);

        for (SkillContainer skillContainer : this.skillContainers.values()) {
            skillContainer.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    private void renderBackground(MatrixStack matrixStack) {
        GL11.glPushMatrix();

        this.mc.getTextureManager().bindTexture(tool_skill_settings);
        blit(matrixStack, x, y, 0 ,0, widgetWidth, widgetHeight);

        GL11.glPopMatrix();
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        double relativeX = mouseX - this.x;
        double relativeY = mouseY - this.y;

        if (!isClickInScreen(relativeX, relativeY) || this.selectedSkill == null) {
            return true;
        }

        if (isClickOnRightClickSlot(relativeX, relativeY)) {
            Main.LOGGER.debug("Clicked on right click skill");
            this.currentToolSkillSetting.rightClickSkillId = this.selectedSkill.getSkillId();
            this.skillContainers.get(RIGHT_CLICK).setSkill(this.selectedSkill);
        } else if (isClickOnRightHoldSlot(relativeX, relativeY)) {
            Main.LOGGER.debug("Clicked on right hold skill");
            this.currentToolSkillSetting.rightHoldSkillId = this.selectedSkill.getSkillId();
            this.skillContainers.get(RIGHT_HOLD).setSkill(this.selectedSkill);
        } else if (isClickOnMiddleClickSlot(relativeX, relativeY)) {
            Main.LOGGER.debug("Clicked on middle click skill");
            this.currentToolSkillSetting.middleClickSkillId = this.selectedSkill.getSkillId();
            this.skillContainers.get(MIDDLE_CLICK).setSkill(this.selectedSkill);
        } else if (isClickOnLeftHoldSlot(relativeX, relativeY)) {
            Main.LOGGER.debug("Clicked on left hold skill");
            this.currentToolSkillSetting.leftHoldSkillId = this.selectedSkill.getSkillId();
            this.skillContainers.get(LEFT_HOLD).setSkill(this.selectedSkill);
        } else if (isClickOnLeftClickSlot(relativeX, relativeY)) {
            Main.LOGGER.debug("Clicked on left click skill");
            this.currentToolSkillSetting.leftClickSkillId = this.selectedSkill.getSkillId();
            this.skillContainers.get(LEFT_CLICK).setSkill(this.selectedSkill);
        }

        this.selectedSkill = null;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isClickInScreen(double relativeX, double relativeY) {
        return relativeX >= 0 &&
               relativeY >= 0 &&
               relativeX <= this.width &&
               relativeY <= this.height;
    }

    private boolean isClickOnRightClickSlot(double relativeX, double relativeY) {
        return relativeX >= 44 &&
               relativeX <= 62 &&
               relativeY >= 4 &&
               relativeY <= 21;
    }

    private boolean isClickOnRightHoldSlot(double relativeX, double relativeY) {
        return relativeX >= 44 &&
               relativeX <= 62 &&
               relativeY >= 25 &&
               relativeY <= 43;
    }

    private boolean isClickOnMiddleClickSlot(double relativeX, double relativeY) {
        return relativeX >= 44 &&
                relativeX <= 62 &&
                relativeY >= 46 &&
                relativeY <= 63;
    }

    private boolean isClickOnLeftHoldSlot(double relativeX, double relativeY) {
        return relativeX >= 23 &&
                relativeX <= 40 &&
                relativeY >= 46 &&
                relativeY <= 63;
    }

    private boolean isClickOnLeftClickSlot(double relativeX, double relativeY) {
        return relativeX >= 2 &&
                relativeX <= 19 &&
                relativeY >= 46 &&
                relativeY <= 63;
    }
}
