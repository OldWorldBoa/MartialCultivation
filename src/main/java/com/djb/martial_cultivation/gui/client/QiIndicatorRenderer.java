package com.djb.martial_cultivation.gui.client;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import com.djb.martial_cultivation.capabilities.CultivatorCapabilityProvider;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QiIndicatorRenderer extends ItemStackTileEntityRenderer {
    private final ResourceLocation qi_indicator = new ResourceLocation(Main.MOD_ID, "textures/gui/qi_indicator_4.png");
    private Minecraft mc;

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

        int qi_texture_size = 129;
        this.mc.ingameGUI.blit(mStack, 50, 530, 0, 0, qi_texture_size, qi_texture_size);
        this.mc.ingameGUI.blit(mStack, 50, 530, qi_texture_size-2, 0, qi_texture_size, qi_texture_size);

        RenderSystem.popMatrix();
        profiler.endSection();
    }

    private void getRemainingQiRatio() {
        PlayerEntity player = (PlayerEntity)mc.getRenderViewEntity();

        try {
            assert player != null;

            Cultivator cultivator = player
                    .getCapability(CultivatorCapabilityProvider.capability)
                    .orElseThrow(IllegalStateException::new);

            int qiRemaining = cultivator.getStoredQi();
            int maxQi = cultivator.getMaxQi();

            Main.LOGGER.debug("Getting remaining qi ratio for " + player.getScoreboardName());
        } catch (Exception e) {
            Main.LOGGER.debug("Error getting qi ratio for " + player.getScoreboardName());
        }
    }
}
