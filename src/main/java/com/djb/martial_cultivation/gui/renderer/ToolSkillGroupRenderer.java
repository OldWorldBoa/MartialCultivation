package com.djb.martial_cultivation.gui.renderer;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.skills.ToolSkillGroup;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ToolSkillGroupRenderer {
    public static void RenderIconFor(ToolSkillGroup group, Widget parent, MatrixStack matrixStack, int x, int y) {
        double scale = 0.5;
        double reverseScale = 1.0/scale;

        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, scale);

        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(getTextureFor(group));

        assert mc.currentScreen != null;
        parent.blit(matrixStack, (int) (x*reverseScale), (int) (y*reverseScale), 0, 0, 32, 32);

        GL11.glPopMatrix();
    }

    private static ResourceLocation getTextureFor(ToolSkillGroup group) {
        switch(group) {
            case BLUDGEON:
                return new ResourceLocation(Main.MOD_ID,"textures/skill/group/bludgeon.png");
            case SWORD:
                return new ResourceLocation(Main.MOD_ID,"textures/skill/group/sword.png");
            case AXE:
                return new ResourceLocation(Main.MOD_ID,"textures/skill/group/axe.png");
            case STAFF:
                return new ResourceLocation(Main.MOD_ID, "textures/skill/group/staff.png");
            default: // This is also the unarmed texture
                return new ResourceLocation(Main.MOD_ID, "textures/skill/group/unarmed.png");
        }
    }
}
