package com.airistotal.martial_cultivation.data.client;

import com.airistotal.martial_cultivation.blocks.ModBlocks;
import com.airistotal.martial_cultivation.Main;

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
