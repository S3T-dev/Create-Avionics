package dev.s3t.create_avionics.registry;

import dev.s3t.create_avionics.blockentities.AntennaBlockEntity;
import dev.s3t.create_avionics.blockentities.GyroBlockEntity;
import dev.s3t.create_avionics.blockentities.PitotTubeBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "create_avionics");

    public static final Supplier<BlockEntityType<GyroBlockEntity>> GYRO =
        BLOCK_ENTITIES.register("gyro", () ->
            BlockEntityType.Builder.of(
                GyroBlockEntity::new,
                ModBlocks.GYRO.get()
            ).build(null)
        );
    
    public static final Supplier<BlockEntityType<AntennaBlockEntity>> ANTENNA =
            BLOCK_ENTITIES.register("antenna", () ->
                BlockEntityType.Builder.of(
                    AntennaBlockEntity::new,
                    ModBlocks.ANTENNA.get()
                ).build(null)
            );
    
    public static final Supplier<BlockEntityType<PitotTubeBlockEntity>> PITOT_TUBE =
            BLOCK_ENTITIES.register("pitot_tube", () ->
                BlockEntityType.Builder.of(
                    PitotTubeBlockEntity::new,
                    ModBlocks.PITOT_TUBE.get()
                ).build(null)
            );
}