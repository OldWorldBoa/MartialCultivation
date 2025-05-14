package com.djb.martial_cultivation.gui.widget;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ToolSelector extends Widget {
    private final ResourceLocation black_pixel = new ResourceLocation(Main.MOD_ID, "textures/gui/black_pixel.png");

    private final List<Item> tools = new ArrayList<Item>() {
        { add(ModItems.BASIC_STAFF.get()); }
        { add(Items.STONE_SWORD); }
        { add(Items.IRON_AXE); }
        { add(Items.STONE_PICKAXE); }
        { add(Items.STONE_HOE); }
    };

    private Minecraft mc;
    private Consumer<Item> itemConsumer;

    private int toolHeight = 18;
    private int toolWidth = 18;
    private int padding = 3;

    public ToolSelector(Minecraft mc, int width, int height, int top, int left, Consumer<Item> itemConsumer) {
        super(left, top, width, height, new StringTextComponent(""));

        this.mc = mc;
        this.itemConsumer = itemConsumer;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        int currX = x;
        int currY = y;
        int rowItem = 0;

        for (Item tool : this.tools) {
            this.mc.getTextureManager().bindTexture(black_pixel);
            this.mc.getItemRenderer().renderItemAndEffectIntoGUI(
                    new ItemStack(tool), currX + padding, currY + padding);

            currX += toolWidth + padding;
            rowItem++;

            if (rowItem == 4) {
                currX = x;
                rowItem = 0;
                currY += toolHeight + padding;
            }
        }
    }

    private Item findTool(final int mouseX, final int mouseY) {
        double yOffset = (mouseY - this.y);
        double xOffset = (mouseX - this.x);

        if (yOffset <= 0 || xOffset <= 0){
            return null;
        }

        int rowIndex = (int) (yOffset / (this.toolHeight + padding));
        int columnIndex = (int) (xOffset / (this.toolWidth + padding));
        int toolIndex = (rowIndex * 4) + columnIndex;

        if (toolIndex >= this.tools.size() || toolIndex < 0)
            return null;

        return this.tools.get(toolIndex);
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        this.itemConsumer.accept(findTool((int) mouseX, (int) mouseY));

        return super.mouseClicked(mouseX, mouseY, button);
    }
}

