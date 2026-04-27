package dev.s3t.create_avionics.blockentities;

import dev.s3t.create_avionics.peripherals.GyroPeripheral;
import dev.s3t.create_avionics.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import dan200.computercraft.api.peripheral.PeripheralCapability;

public class GyroBlockEntity extends BlockEntity {

    private final GyroPeripheral peripheral = new GyroPeripheral(this);

    public GyroBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GYRO.get(), pos, state);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
        	PeripheralCapability.get(),
            ModBlockEntities.GYRO.get(),
            (be, side) -> be.peripheral
        );
    }

    public GyroPeripheral getPeripheral() {
        return peripheral;
    }
}