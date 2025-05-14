package com.djb.martial_cultivation.gui.screen;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkill;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillGroup;
import com.djb.martial_cultivation.exceptions.SkillNotImplementedException;
import com.djb.martial_cultivation.gui.widget.SkillSelector;
import com.djb.martial_cultivation.gui.widget.ToolGroupSelector;
import com.djb.martial_cultivation.gui.widget.ToolSkillSettingsWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

public class SkillScreen extends Screen {
    private static final ITextComponent title = new StringTextComponent("Skill Screen");
    private static final ResourceLocation skill_screen = new ResourceLocation(Main.MOD_ID, "textures/gui/skill_screen.png");
    private static final ResourceLocation black_pixel = new ResourceLocation(Main.MOD_ID, "textures/black_pixel.png");

    private final int screenWidth = 176;
    private final int screenHeight = 166;

    private CultivationSkill selectedSkill;
    private ToolGroupSelector toolGroupSelector;
    private SkillSelector skillSelector;
    private ToolSkillSettingsWidget toolSkillSettingsWidget;

    private int screenTop = 0;
    private int screenLeft = 0;

    public SkillScreen() {
        super(title);
    }

    @Override
    protected void init() {
        this.minecraft = Minecraft.getInstance();

        assert this.minecraft.currentScreen != null;
        this.screenLeft = (this.minecraft.currentScreen.width - this.screenWidth) / 2;
        this.screenTop = (this.minecraft.currentScreen.height - this.screenHeight) / 2;

        this.toolGroupSelector = new ToolGroupSelector(
                this.minecraft,
                88,
                56,
                this.screenTop + 11,
                this.screenLeft + (this.screenWidth / 2) - 11,
                this::selectTool);

        try {
            this.skillSelector = new SkillSelector(
                    this.minecraft,
                    this.screenLeft + 10,
                    this.screenTop + 80,
                    100,
                    88,
                    this.minecraft.player,
                    this::selectSkill);
        } catch (SkillNotImplementedException e) {
            Main.LOGGER.error(e);
        }

        try {
            this.toolSkillSettingsWidget = new ToolSkillSettingsWidget(
                    this.minecraft,
                    this.screenLeft + 6,
                    this.screenTop + 6,
                    this.minecraft.player);
        } catch (SkillNotImplementedException e) {
            Main.LOGGER.error(e);
        }

        this.addListener(this.toolGroupSelector);

        for (IGuiEventListener listener: this.skillSelector.getNestedListeners()) {
            this.addListener(listener);
        }

        this.addListener(this.toolSkillSettingsWidget);
    }

    private void selectTool(ToolSkillGroup toolSkillGroup) {
        if (toolSkillGroup != null) {
            Main.LOGGER.debug("Selected tool skill group: " + toolSkillGroup);
            this.toolSkillSettingsWidget.setSelectedToolSkillGroup(toolSkillGroup);
        }
    }

    private void selectSkill(CultivationSkill skill) {
        if (skill != null) {
            Main.LOGGER.debug("Selected skill: " + skill.getSkillId());
            this.selectedSkill = skill;
            this.toolSkillSettingsWidget.setSelectedSkill(skill);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        assert this.minecraft != null;
        assert this.minecraft.currentScreen != null;

        renderBackground(matrixStack);

        this.minecraft.getTextureManager().bindTexture(skill_screen);
        this.blit(matrixStack, this.screenLeft, this.screenTop, 0, 0,
                screenWidth, screenHeight);

        this.toolGroupSelector.render(matrixStack, mouseX, mouseY, partialTicks);
        this.skillSelector.render(matrixStack, mouseX, mouseY, partialTicks);
        this.toolSkillSettingsWidget.render(matrixStack, mouseX, mouseY, partialTicks);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        renderForeground(matrixStack, mouseX, mouseY);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4d(1, 1, 1, 0.5);

        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(black_pixel);

        assert this.minecraft.currentScreen != null;
        blit(matrixStack, 0, 0, 0, 0,
                this.minecraft.currentScreen.width, this.minecraft.currentScreen.height);

        GL11.glColor4d(1, 1, 1,1);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void renderForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.selectedSkill != null) {
            GL11.glPushMatrix();

            this.minecraft.getTextureManager().bindTexture(this.selectedSkill.getTextureLocation());
            blit(matrixStack, mouseX - 9, mouseY - 9, 0, 0, 18, 18);

            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.selectedSkill = null;

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
