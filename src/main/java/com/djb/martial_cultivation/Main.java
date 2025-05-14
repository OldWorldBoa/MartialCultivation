package com.djb.martial_cultivation;

import com.djb.martial_cultivation.capabilities.ModCapabilities;
import com.djb.martial_cultivation.events.CultivationEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MOD_ID)
public class Main
{
	public static final String MOD_ID = "martial_cultivation";

    public static final Logger LOGGER = LogManager.getLogger();

    public Main() {
    	Registrar.registerBlocksAndItems();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerClientMods);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Executing " + Main.MOD_ID + " preinit processing.");

        Registrar.registerCapabilities();

        LOGGER.info("Finished " + Main.MOD_ID + " preinit processing.");
    }

    private void registerClientMods(final FMLClientSetupEvent event) {
    }
}
