package dev.s3t.create_avionics.blockentities;

import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.companion.math.Pose3dc;
import dev.s3t.create_avionics.data.StreamLevelData;
import dev.s3t.create_avionics.peripherals.AntennaPeripheral;
import dev.s3t.create_avionics.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.PeripheralCapability;

public class AntennaBlockEntity extends BlockEntity {

    private final AntennaPeripheral peripheral = new AntennaPeripheral(this);

    public AntennaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ANTENNA.get(), pos, state);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
        	PeripheralCapability.get(),
            ModBlockEntities.ANTENNA.get(),
            (be, side) -> be.peripheral
        );
    }

    public AntennaPeripheral getPeripheral() {
        return peripheral;
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, AntennaBlockEntity be) {
        if (level.isClientSide) return;
        if (!be.peripheral.isUpstream()) return;

        ServerLevel serverLevel = (ServerLevel) level;
        
        Vec3 blockPos = Vec3.atCenterOf(pos);
        SubLevelAccess subLevelAccess = SableCompanion.INSTANCE.getContaining(serverLevel, blockPos);
        if (subLevelAccess != null) {
            Pose3dc pose = subLevelAccess.logicalPose();
            blockPos = pose.transformPosition(blockPos);
        }
        blockPos = (Vec3) SableCompanion.INSTANCE.projectOutOfSubLevel(serverLevel, blockPos);
        
        StreamLevelData data = StreamLevelData.get(serverLevel);

        for (IComputerAccess computer : be.peripheral.getComputers()) {
            if (be.peripheral.isStreaming(computer.getID())) {
                data.putUpstream(computer.getID(), serverLevel.dimension(), blockPos);
            }
        }
    }
}