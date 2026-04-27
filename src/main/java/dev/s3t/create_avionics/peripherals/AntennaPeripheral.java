package dev.s3t.create_avionics.peripherals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dev.s3t.create_avionics.blockentities.AntennaBlockEntity;
import dev.s3t.create_avionics.data.StreamLevelData;
import net.minecraft.server.level.ServerLevel;

public class AntennaPeripheral implements IPeripheral {

    private final AntennaBlockEntity blockEntity;
    private final Set<IComputerAccess> computers = new HashSet<>();
    private final Set<Integer> streamingComputers = new HashSet<>();

    public AntennaPeripheral(AntennaBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public Set<IComputerAccess> getComputers() {
        return Collections.unmodifiableSet(computers);
    }

    public boolean isUpstream() {
        return !streamingComputers.isEmpty();
    }

    public boolean isStreaming(int computerId) {
        return streamingComputers.contains(computerId);
    }

    @LuaFunction
    public final Boolean upstream(IComputerAccess computer) {
        streamingComputers.add(computer.getID());
        return true;
    }

    @LuaFunction
    public final Boolean downstream(IComputerAccess computer) {
        streamingComputers.remove(computer.getID());
        StreamLevelData.get((ServerLevel) blockEntity.getLevel()).removeUpstream(computer.getID());
        return true;
    }

    @LuaFunction
    public Boolean streaming(IComputerAccess computer) {
        return streamingComputers.contains(computer.getID());
    }

    @Override
    public String getType() {
        return "antenna";
    }

    @Override
    public void attach(IComputerAccess computer) {
        computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        computers.remove(computer);
        streamingComputers.remove(computer.getID());
        StreamLevelData.get((ServerLevel) blockEntity.getLevel()).removeUpstream(computer.getID());
    }

    @Override
    public boolean equals(IPeripheral other) {
        return this == other;
    }
}