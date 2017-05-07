package com.idtech.enchantment;

import java.util.ArrayList;
import java.util.List;

import com.idtech.item.ItemBattery;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EnchantmentMod {

		public static final  EnumEnchantmentType BATTERY = EnumHelper.addEnchantmentType("BATTERY", (item) -> item instanceof ItemBattery);
		
		public static final List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
		
		public static final Enchantment SUPERCHARGED = addEnchantment(new EnchantmentSuperCharged(), "supercharged");
		
		private static Enchantment addEnchantment(Enchantment enchantment, String name) {
			ENCHANTMENTS.add(enchantment);
			return enchantment.setRegistryName(name).setName(name);
		}
		
		@SubscribeEvent
		public static void registerEnchantments(Register<Enchantment> event) {
			for(Enchantment enchantment: ENCHANTMENTS)
				event.getRegistry().register(enchantment);
		}
	
}
