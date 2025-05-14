package com.djb.martial_cultivation.data.client;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.blocks.ModBlocks;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Main.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(ModBlocks.CONDENSED_QI.get());
	}
}
