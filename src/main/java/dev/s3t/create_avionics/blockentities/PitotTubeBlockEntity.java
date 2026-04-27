package dev.s3t.create_avionics.blockentities;

import dan200.computercraft.api.peripheral.PeripheralCapability;
import dev.s3t.create_avionics.peripherals.PitotTubePeripheral;
import dev.s3t.create_avionics.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class PitotTubeBlockEntity extends BlockEntity {
	
	private final PitotTubePeripheral peripheral = new PitotTubePeripheral(this);
	
	public PitotTubeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PITOT_TUBE.get(), pos, state);
	}
	
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
        	PeripheralCapability.get(),
            ModBlockEntities.PITOT_TUBE.get(),
            (be, side) -> be.peripheral
        );
    }
	
	public PitotTubePeripheral getPeripheral() {
        return peripheral;
    }
}
