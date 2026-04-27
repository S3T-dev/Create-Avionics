package dev.s3t.create_avionics;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import dan200.computercraft.api.ComputerCraftAPI;
import dev.s3t.create_avionics.LuaGlobal.AvionicsAPI;
import dev.s3t.create_avionics.blockentities.AntennaBlockEntity;
import dev.s3t.create_avionics.blockentities.GyroBlockEntity;
import dev.s3t.create_avionics.blockentities.PitotTubeBlockEntity;
import dev.s3t.create_avionics.registry.ModBlockEntities;
import dev.s3t.create_avionics.registry.ModBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(CreateAvionics.MODID)
public class CreateAvionics {
    public static final String MODID = "create_avionics";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateAvionics(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        modEventBus.addListener(GyroBlockEntity::registerCapabilities);
        modEventBus.addListener(AntennaBlockEntity::registerCapabilities);
        modEventBus.addListener(PitotTubeBlockEntity::registerCapabilities);
        ComputerCraftAPI.registerAPIFactory(computer -> new AvionicsAPI(computer));
        modContainer.registerConfig(ModConfig.Type.COMMON, AvionicsConfig.SPEC);
        CreativeTabs.TABS.register(modEventBus);
    }
}
