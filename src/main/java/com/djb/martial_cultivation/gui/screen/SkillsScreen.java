package com.djb.martial_cultivation.gui.screen;

import com.djb.martial_cultivation.containers.SkillContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class SkillsScreen extends Screen {
    private final int buttonSize = 128;

    private Minecraft mc;
    private SkillContainer skillContainer;
    private boolean isOpen;
    private int height;
    private int width;

    protected SkillsScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    public void init(int widthIn, int heightIn, Minecraft minecraftIn) {
        this.mc = minecraftIn;
        this.width = widthIn;
        this.height = heightIn;


    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    }
}
