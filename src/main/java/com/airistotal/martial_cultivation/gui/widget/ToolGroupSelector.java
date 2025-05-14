package com.airistotal.martial_cultivation.gui.widget;

import com.airistotal.martial_cultivation.Main;
import com.airistotal.martial_cultivation.capabilities.skills.ToolSkillGroup;
import com.airistotal.martial_cultivation.gui.renderer.ToolSkillGroupRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.function.Consumer;

public class ToolGroupSelector extends Widget {
    private static final ResourceLocation black_pixel = new ResourceLocation(Main.MOD_ID, "textures/black_pixel.png");

    private final ToolSkillGroup[] toolSkillGroups = ToolSkillGroup.values();

    private final Minecraft mc;
    private final Consumer<ToolSkillGroup> itemConsumer;

    private final int toolHeight = 18;
    private final int toolWidth = 18;
    private final int padding = 3;

    public ToolGroupSelector(Minecraft mc, int width, int height, int top, int left, Consumer<ToolSkillGroup> itemConsumer) {
        super(left, top, width, height, new StringTextComponent(""));

        this.mc = mc;
        this.itemConsumer = itemConsumer;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int currX = x;
        int currY = y;
        int rowItem = 0;



        for (int i = 0; i < this.toolSkillGroups.length; i++) {
            ToolSkillGroupRenderer.RenderIconFor(
                    this.toolSkillGroups[i],
                    this,
                    matrixStack,
                    currX + padding,
                    currY + padding);

            currX += toolWidth + padding;
            rowItem++;

            if (rowItem == 4) {
                currX = x;
                rowItem = 0;
                currY += toolHeight + padding;
            }
        }
    }

    private ToolSkillGroup findTool(final int mouseX, final int mouseY) {
        double yOffset = (mouseY - this.y);
        double xOffset = (mouseX - this.x);

        if (yOffset <= 0 || xOffset <= 0){
            return null;
        }

        int rowIndex = (int) (yOffset / (this.toolHeight + padding));
        int columnIndex = (int) (xOffset / (this.toolWidth + padding));
        int toolIndex = (rowIndex * 4) + columnIndex;

        if (toolIndex >= this.toolSkillGroups.length || toolIndex < 0)
            return null;

        return this.toolSkillGroups[toolIndex];
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        this.itemConsumer.accept(findTool((int) mouseX, (int) mouseY));

        return super.mouseClicked(mouseX, mouseY, button);
    }
}

