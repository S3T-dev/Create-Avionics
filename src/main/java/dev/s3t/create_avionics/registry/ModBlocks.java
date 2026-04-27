package dev.s3t.create_avionics.registry;

import dev.s3t.create_avionics.blocks.AntennaBlock;
import dev.s3t.create_avionics.blocks.GyroBlock;
import dev.s3t.create_avionics.blocks.PitotTubeBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(Registries.BLOCK, "create_avionics");

    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(Registries.ITEM, "create_avionics");

    public static final Supplier<Block> GYRO =
        BLOCKS.register("gyro", () -> new GyroBlock(
            BlockBehaviour.Properties.of()
                .strength(1.5f)
                .requiresCorrectToolForDrops()
        ));

    public static final Supplier<Item> GYRO_ITEM =
        ITEMS.register("gyro", () -> new BlockItem(
            GYRO.get(),
            new Item.Properties()
        ));
    
    public static final Supplier<Block> ANTENNA =
        BLOCKS.register("antenna", () -> new AntennaBlock(
            BlockBehaviour.Properties.of()
                .strength(1.5f)
                .requiresCorrectToolForDrops()
        ));

    public static final Supplier<Item> ANTENNA_ITEM =
        ITEMS.register("antenna", () -> new BlockItem(
            ANTENNA.get(),
            new Item.Properties()
        ));
    
    public static final Supplier<Block> PITOT_TUBE =
            BLOCKS.register("pitot_tube", () -> new PitotTubeBlock(
                BlockBehaviour.Properties.of()
                    .strength(1.5f)
                    .requiresCorrectToolForDrops()
            ));

        public static final Supplier<Item> PITOT_TUBE_ITEM =
            ITEMS.register("pitot_tube", () -> new BlockItem(
                PITOT_TUBE.get(),
                new Item.Properties()
            ));
}