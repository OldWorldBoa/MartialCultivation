package com.djb.martial_cultivation;

import com.djb.martial_cultivation.blocks.ModBlocks;
import com.djb.martial_cultivation.containers.ModContainers;
import com.djb.martial_cultivation.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

public class Registrar {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MOD_ID);
	public static KeyBinding[] keyBindings;
	
	public static void registerBlocksAndItems() {
		Main.LOGGER.debug("Registering items and blocks.");

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		CONTAINERS.register(modEventBus);
		
		ModItems.register();
		ModBlocks.register();
		ModContainers.register();

		Main.LOGGER.debug("Done registering items and blocks.");
	}

	public static void registerKeybindings() {
		keyBindings = new KeyBinding[1];
		keyBindings[0] = new KeyBinding(
			"key." + Main.MOD_ID + ".cultivate",
			GLFW.GLFW_KEY_Z,
			"key." + Main.MOD_ID + ".category");

		for (KeyBinding keyBinding : keyBindings) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
	}
}
