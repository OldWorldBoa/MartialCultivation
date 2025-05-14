package com.djb.martial_cultivation.gui.client;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QiIndicatorRenderer {
    private final ResourceLocation qi_indicator = new ResourceLocation(Main.MOD_ID, "textures/gui/qi_indicator_4.png");
    private final Minecraft mc;

    public QiIndicatorRenderer(Minecraft mc) {
        this.mc = mc;
    }

    public void renderQiIndicator(MatrixStack mStack) {
        IProfiler profiler = this.mc.getProfiler();
        this.mc.getTextureManager().bindTexture(qi_indicator);

        profiler.startSection("qi_indicator");
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.scalef(0.3f, 0.3f, 0.3f);

        int qiTextureSize = 129;
        double qiRemainingRatio = getRemainingQiRatio();
        this.mc.ingameGUI.blit(mStack, 50, 530, 0, 0, qiTextureSize, qiTextureSize);
        this.mc.ingameGUI.blit(mStack, 50, 530, qiTextureSize-2, 0, qiTextureSize, (int) Math.floor(qiTextureSize * qiRemainingRatio));

        RenderSystem.popMatrix();
        profiler.endSection();
    }

    private double getRemainingQiRatio() {
        PlayerEntity player = (PlayerEntity)mc.getRenderViewEntity();

        try {
            assert player != null;

            Cultivator cultivator = Cultivator.getCultivatorFrom(player);

            return cultivator.getStoredQi()/(double)cultivator.getMaxQi();
        } catch (Exception e) {
            Main.LOGGER.warn("Error getting qi ratio for " + player.getScoreboardName());
        }

        return 1;
    }
}
