package com.djb.martial_cultivation;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ModBlocks {
	public static final RegistryObject<Block> STAFF_DROP = registerNoItem("condensed_qi", () -> 
		new Block(Block.Properties.create(Material.ROCK)));
	
	public static void register() {}
	
	private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
		return Registrar.BLOCKS.register(name, block);
	}
	
	private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
		RegistryObject<T> registeredBlock = registerNoItem(name, block);
		Registrar.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties().group(ItemGroup.COMBAT)));
		
		return registeredBlock;
	}
}
