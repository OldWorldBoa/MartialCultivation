package com.djb.martial_cultivation;

import com.djb.martial_cultivation.capabilities.ModCapabilities;
import com.djb.martial_cultivation.data.network.NetworkMessages;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MOD_ID)
public class Main
{
	public static final String MOD_ID = "martial_cultivation";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(Main.MOD_ID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public Main() {
    	Registrar.registerBlocksAndItems();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerClientMods);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Executing " + Main.MOD_ID + " preinit processing.");

        ModCapabilities.register();
        NetworkMessages.registerMessages();

        LOGGER.info("Finished " + Main.MOD_ID + " preinit processing.");
    }

    private void registerClientMods(final FMLClientSetupEvent event) {
    }
}
