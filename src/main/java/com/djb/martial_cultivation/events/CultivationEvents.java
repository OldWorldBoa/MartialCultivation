package com.djb.martial_cultivation.events;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import com.djb.martial_cultivation.capabilities.CultivatorCapabilityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CultivationEvents {
    private static int tickCount = 0;

    @SubscribeEvent
    public static void regenerateQi(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            tickCount += 1;

            if (tickCount % 20 == 0) {
                PlayerEntity player = event.player;

                try {
                    Cultivator cultivator = player
                            .getCapability(CultivatorCapabilityProvider.capability)
                            .orElseThrow(IllegalStateException::new);

                    cultivator.storeQi(5);

                    Main.LOGGER.debug("Storing qi for " + player.getScoreboardName());
                } catch (Exception e) {
                    Main.LOGGER.debug("Error storing qi for " + player.getScoreboardName());
                }
            }
        }
    }
}
