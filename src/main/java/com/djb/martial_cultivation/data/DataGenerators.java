package com.djb.martial_cultivation.data;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.data.client.ModBlockStateProvider;
import com.djb.martial_cultivation.data.client.ModItemModelProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	private DataGenerators() {}
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper fileHelper = event.getExistingFileHelper();

		generator.addProvider(new ModBlockStateProvider(generator, fileHelper));
		generator.addProvider(new ModItemModelProvider(generator, fileHelper));

		ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, fileHelper);

		generator.addProvider(blockTagsProvider);
		generator.addProvider(new ModItemTagsProvider(generator, blockTagsProvider, fileHelper));

		generator.addProvider(new ModRecipesProvider(generator));
	}
}
