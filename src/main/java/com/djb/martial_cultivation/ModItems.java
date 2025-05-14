package com.djb.martial_cultivation;

import com.djb.martial_cultivation.tools.combat.BasicStaff;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
	public static final RegistryObject<Item> BASIC_STAFF = Registrar.ITEMS.register("basic_staff", () -> new BasicStaff());
	
	public static void register() {}
}
