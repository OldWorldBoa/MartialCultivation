package com.djb.martial_cultivation.gui.widget;

import com.djb.martial_cultivation.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.gui.ScrollPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ToolSelector extends ScrollPanel {
    private final List<Item> tools = new ArrayList<Item>() {
        { add(ModItems.BASIC_STAFF.get()); }
    };

    private Minecraft mc;
    private Consumer<Item> itemConsumer;

    private int toolHeight = 35;
    private int toolWidth = 35;

    public ToolSelector(Minecraft mc, int width, int height, int top, int left, Consumer<Item> itemConsumer) {
        super(mc, width, height, top, left);

        this.mc = mc;
        this.itemConsumer = itemConsumer;
    }

    @Override
    public int getContentHeight()
    {
        int height = (this.tools.size() / 5) * 35;

        if (height < this.bottom - this.top - 8)
            height = this.bottom - this.top - 8;

        return height;
    }

    @Override
    protected int getScrollAmount()
    {
        return 10;
    }

    @Override
    protected void drawPanel(MatrixStack mStack, int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY)
    {
        for (Item tool : this.tools) {
            ItemStack tempStack = new ItemStack(tool);

            this.mc.getItemRenderer().renderItemAndEffectIntoGUI(tempStack, entryRight, relativeY);
        }
    }

    private Item findTool(final int mouseX, final int mouseY) {
        double yOffset = (mouseY - top) + border + scrollDistance + 1;
        double xOffset = (mouseX - left);

        if (yOffset <= 0 || xOffset <= 0){
            return null;
        }

        int rowIndex = (int) (yOffset / this.toolHeight);
        int columnIndex = (int) (xOffset / this.toolWidth);
        int toolIndex = rowIndex * columnIndex;

        if (toolIndex >= this.tools.size() || toolIndex < 1)
            return null;

        return this.tools.get(toolIndex);
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        this.itemConsumer.accept(findTool((int) mouseX, (int) mouseY));

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground() {
    }
}

