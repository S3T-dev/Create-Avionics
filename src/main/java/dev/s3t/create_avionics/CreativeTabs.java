package dev.s3t.create_avionics;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import dev.s3t.create_avionics.registry.ModBlocks;

public class CreativeTabs {
	public static final DeferredRegister<CreativeModeTab> TABS =
	        DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
	            CreateAvionics.MODID); 
	
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_AVIONICS_TAB =
	        TABS.register("create_avionics", () ->
	            CreativeModeTab.builder()
	                .icon(() -> new ItemStack(ModBlocks.GYRO_ITEM.get()))
	                .title(Component.translatable("itemGroup.create_avionics.create_avionics"))
	                .displayItems((params, output) -> {
	                    output.accept(ModBlocks.GYRO_ITEM.get());
	                    output.accept(ModBlocks.ANTENNA_ITEM.get());
	                    output.accept(ModBlocks.PITOT_TUBE_ITEM.get());
	                })
	                .build());
}
