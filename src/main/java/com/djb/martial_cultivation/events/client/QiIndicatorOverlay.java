package com.djb.martial_cultivation.events.client;

import com.djb.martial_cultivation.gui.renderer.QiIndicatorRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class QiIndicatorOverlay {

    @SubscribeEvent
    public static void renderQiIndicator(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            QiIndicatorRenderer qiIndicatorRenderer  = new QiIndicatorRenderer();
            qiIndicatorRenderer.renderQiIndicator(event.getMatrixStack());
        }
    }
}
