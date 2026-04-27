package dev.s3t.create_avionics.peripherals;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joml.Quaterniondc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.companion.math.Pose3dc;
import dev.s3t.create_avionics.blockentities.GyroBlockEntity;

public class GyroPeripheral implements IPeripheral {

    private final GyroBlockEntity blockEntity;
    private final Set<IComputerAccess> computers = new HashSet<>();

    public GyroPeripheral(GyroBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }
    
    @LuaFunction
    public final Map<String, Double> getRotation() {
    	SubLevelAccess subLevelAccess = SableCompanion.INSTANCE.getContaining(blockEntity);
    	Pose3dc pose = subLevelAccess.logicalPose();
    	Quaterniondc q = pose.orientation();
    	
    	return Map.of(
    	        "s", (double) q.x(),
    	        "i", (double) q.y(),
    	        "j", (double) q.z(),
    	        "k", (double) q.w()
    	    );
    }

    @Override
    public String getType() {
        return "gyro";
    }

    @Override
    public void attach(IComputerAccess computer) {
        computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        computers.remove(computer);
    }

    @Override
    public boolean equals(IPeripheral other) {
        return this == other;
    }
}