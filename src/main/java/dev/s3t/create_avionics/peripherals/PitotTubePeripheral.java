package dev.s3t.create_avionics.peripherals;

import java.util.HashSet;
import java.util.Set;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.companion.math.Pose3dc;

import dev.s3t.create_avionics.blockentities.PitotTubeBlockEntity;
import net.minecraft.world.phys.Vec3;

public class PitotTubePeripheral implements IPeripheral {
	private final PitotTubeBlockEntity blockEntity;
	private final Set<IComputerAccess> computers = new HashSet<>();
	
	public PitotTubePeripheral(PitotTubeBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }
	
	@LuaFunction
    public final double getSpeed() {
    	SubLevelAccess subLevelAccess = SableCompanion.INSTANCE.getContaining(blockEntity);
    	Pose3dc current = subLevelAccess.logicalPose();
        Pose3dc previous = subLevelAccess.lastPose();
        
        Vec3 currentPos = JOMLConversion.toMojang(current.position());
        Vec3 previousPos = JOMLConversion.toMojang(previous.position());
        Vec3 deltaPos = currentPos.subtract(previousPos);
        
        Vec3 localForward = new Vec3(0, 0, 1);
        Vec3 worldForward = current.transformNormal(localForward);
        
        return deltaPos.dot(worldForward);
    }
	
    @Override
    public String getType() {
        return "pitot_tube";
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
