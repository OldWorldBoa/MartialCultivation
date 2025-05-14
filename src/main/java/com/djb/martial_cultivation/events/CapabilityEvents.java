package com.djb.martial_cultivation.events;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.CultivatorCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityEvents {
    public static final ResourceLocation CULTIVATOR = new ResourceLocation(Main.MOD_ID, "capabilities/foundationcultivator.class");

    @SubscribeEvent
    public static void attachCapabilitiesToEntities(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();

        if (entity instanceof PlayerEntity) {
            event.addCapability(CULTIVATOR, new CultivatorCapabilityProvider());
        }
    }
}
