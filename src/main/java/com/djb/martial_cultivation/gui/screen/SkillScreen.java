package com.djb.martial_cultivation.gui.screen;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.gui.widget.ToolSelector;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

public class SkillScreen extends Screen {
    private static final ITextComponent title = new StringTextComponent("Skill Screen");
    private static final ResourceLocation skill_screen = new ResourceLocation(Main.MOD_ID, "textures/gui/skill_screen.png");
    private static final ResourceLocation black_pixel = new ResourceLocation(Main.MOD_ID, "textures/gui/black_pixel.png");

    private final int screenWidth = 176;
    private final int screenHeight = 166;

    private PlayerEntity player;
    private Item selectedTool;
    private ToolSelector toolSelector;
    private Widget skillSelector;

    private int screenTop = 0;
    private int screenLeft = 0;

    public SkillScreen() {
        super(title);
    }

    @Override
    protected void init() {
        this.minecraft = Minecraft.getInstance();
        this.player = this.minecraft.player;

        this.screenLeft = (this.minecraft.currentScreen.width - this.screenWidth) / 2;
        this.screenTop = (this.minecraft.currentScreen.height - this.screenHeight) / 2;

        this.toolSelector = new ToolSelector(
                this.minecraft,
                88,
                56,
                this.screenTop + 11,
                this.screenLeft + (this.screenWidth / 2) - 11,
                this::selectTool);
    }

    private void selectTool(Item tool) {
        Main.LOGGER.debug("Selected tool: " + tool.getTranslationKey());
        this.selectedTool = tool;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        assert this.minecraft != null;
        assert this.minecraft.currentScreen != null;

        renderBackground(matrixStack);

        this.minecraft.getTextureManager().bindTexture(skill_screen);
        this.blit(matrixStack, this.screenLeft, this.screenTop, 0, 0, screenWidth, screenHeight);

        this.toolSelector.render(matrixStack, mouseX, mouseY, partialTicks);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4d(1, 1, 1, 0.5);

        this.minecraft.getTextureManager().bindTexture(black_pixel);
        blit(matrixStack, 0, 0, 0, 0,
                this.minecraft.currentScreen.width, this.minecraft.currentScreen.height);

        GL11.glColor4d(1, 1, 1,1);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
