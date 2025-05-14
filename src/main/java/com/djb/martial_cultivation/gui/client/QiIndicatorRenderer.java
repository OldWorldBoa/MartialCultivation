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

    public void renderQiIndicator(MatrixStack mStack) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = (PlayerEntity)mc.getRenderViewEntity();

        if (isCultivationEnabled(player)) {
            IProfiler profiler = mc.getProfiler();
            mc.getTextureManager().bindTexture(qi_indicator);

            profiler.startSection("qi_indicator");
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.scalef(0.3f, 0.3f, 0.3f);

            int qiTextureSize = 129;
            double qiRemainingRatio = getRemainingQiRatio(player);
            mc.ingameGUI.blit(mStack, 50, 530, 0, 0, qiTextureSize, qiTextureSize);
            mc.ingameGUI.blit(mStack, 50, 530, qiTextureSize-2, 0, qiTextureSize, (int) Math.floor(qiTextureSize * qiRemainingRatio));

            RenderSystem.popMatrix();
            profiler.endSection();
        }
    }

    private double getRemainingQiRatio(PlayerEntity player) {
        try {
            assert player != null;

            Cultivator cultivator = Cultivator.getCultivatorFrom(player);

            return cultivator.getStoredQi()/(double)cultivator.getMaxQi();
        } catch (Exception e) {
            Main.LOGGER.warn("Error getting qi ratio for " + player.getScoreboardName());

            return 1;
        }
    }

    private boolean isCultivationEnabled(PlayerEntity player) {
        try {
            assert player != null;

            Cultivator cultivator = Cultivator.getCultivatorFrom(player);

            return cultivator.isEnabled();
        } catch (Exception e) {
            Main.LOGGER.warn("Error getting cultivation enable state for " + player.getScoreboardName());

            return false;
        }
    }
}
