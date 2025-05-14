package com.djb.martial_cultivation.data.client;

import com.djb.martial_cultivation.Main;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Main.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		withExistingParent("condensed_qi", modLoc("block/condensed_qi"));
		
		build("basic_staff", getExistingFile(mcLoc("item/generated")));
	}
	
	private void build(String name, ModelFile modFile) {
		getBuilder(name).parent(modFile).texture("layer0", "item/" + name);
	}
}
