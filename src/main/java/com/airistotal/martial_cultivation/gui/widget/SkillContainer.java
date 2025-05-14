package com.airistotal.martial_cultivation.gui.widget;

import com.airistotal.martial_cultivation.capabilities.skills.CultivationSkill;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.util.function.Consumer;

public class SkillContainer extends Widget {
    private static final int skillHeight = 16;
    private static final int skillWidth = 16;

    private final Minecraft mc;
    private CultivationSkill skill;
    private final Consumer<CultivationSkill> clickCallback;

    public SkillContainer(Minecraft mc, CultivationSkill skill, int x, int y) {
        super(x, y, skillWidth, skillHeight, new StringTextComponent(""));

        this.mc = mc;
        this.skill = skill;
        this.clickCallback = null;
    }

    public SkillContainer(Minecraft mc, CultivationSkill skill, int x, int y, Consumer<CultivationSkill> clickCallback) {
        super(x, y, skillWidth, skillHeight, new StringTextComponent(""));

        this.mc = mc;
        this.skill = skill;
        this.clickCallback = clickCallback;
    }

    public void setSkill(CultivationSkill skill) {
        this.skill = skill;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.skill != null) {
            GL11.glPushMatrix();

            mc.getTextureManager().bindTexture(skill.getTextureLocation());
            blit(matrixStack, x, y, 0, 0, skillWidth, skillHeight);

            GL11.glPopMatrix();

            if (mouseX > x && mouseX < x + skillWidth && mouseY > y && mouseY < y + skillHeight) {
                this.renderToolTip(matrixStack, mouseX, mouseY);
            }
        }
    }

    @Override
    public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.skill != null) {
            assert this.mc.currentScreen != null;
            GuiUtils.drawHoveringText(
                    matrixStack,
                    this.skill.getToolTip(),
                    mouseX,
                    mouseY,
                    this.mc.currentScreen.width,
                    this.mc.currentScreen.height,
                    -1,
                    this.mc.fontRenderer);
        }
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        if (this.skill != null && this.clickCallback != null &&
                mouseX > this.x && mouseX < this.x + skillWidth &&
                mouseY > this.y && mouseY < this.y + skillHeight) {
            this.clickCallback.accept(this.skill);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
