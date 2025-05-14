package com.djb.martial_cultivation.items;

import com.djb.martial_cultivation.Registrar;
import com.djb.martial_cultivation.items.books.CultivationIntroBook;
import com.djb.martial_cultivation.items.materials.QiEssence;
import com.djb.martial_cultivation.items.tools.combat.BasicStaff;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
	public static final RegistryObject<Item> BASIC_STAFF = Registrar.ITEMS.register(BasicStaff.name, BasicStaff::new);
	public static final RegistryObject<Item> QI_ESSENCE = Registrar.ITEMS.register(QiEssence.name, QiEssence::new);
	public static final RegistryObject<Item> CULTIVATION_INTRO_BOOK = Registrar.ITEMS.register(CultivationIntroBook.name, CultivationIntroBook::new);
	
	public static void register() {}
}
