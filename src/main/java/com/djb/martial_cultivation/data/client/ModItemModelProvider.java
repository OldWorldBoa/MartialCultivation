package com.djb.martial_cultivation.data.client;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.blocks.materials.CondensedQi;
import com.djb.martial_cultivation.items.books.CultivationIntroBook;
import com.djb.martial_cultivation.items.materials.QiEssence;
import com.djb.martial_cultivation.items.tools.combat.BasicStaff;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Main.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		withExistingParent(CondensedQi.name, modLoc("block/" + CondensedQi.name));

		build(QiEssence.name);
		build(BasicStaff.name);
		build(CultivationIntroBook.name);
	}
	
	private void build(String name) {
		getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
	}
}
