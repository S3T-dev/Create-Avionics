package dev.s3t.create_avionics.LuaGlobal;

import net.minecraft.core.Position;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

import dan200.computercraft.api.lua.IComputerSystem;
import dan200.computercraft.api.lua.ILuaAPI;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.LuaException;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.companion.math.Pose3dc;
import dev.s3t.create_avionics.AvionicsConfig;
import dev.s3t.create_avionics.data.StreamLevelData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class AvionicsAPI implements ILuaAPI {

    private final IComputerSystem computer;

    public AvionicsAPI(IComputerSystem computer) {
        this.computer = computer;
    }

    @Override
    public String[] getNames() {
        return new String[]{"avionics"};
    }

    @LuaFunction
    public final String getLevel() {
        return computer.getLevel().dimension().location().toString();
    }

    @LuaFunction
    public final Map<String, Double> getWorldPos() throws LuaException {
        if (!AvionicsConfig.ENABLE_GETPOS.get()) throw new LuaException("avionics.getWorldPos() is disabled!");
        Vec3 position = _getWorldPos();
        return Map.of(
            "x", position.x(),
            "y", position.y(),
            "z", position.z()
        );
    }

    public final Vec3 _getWorldPos() {
        Position position = Vec3.atCenterOf(this.computer.getPosition());
        ServerLevel level = this.computer.getLevel();
        SubLevelAccess subLevelAccess = SableCompanion.INSTANCE.getContaining(level, position);

        if (subLevelAccess != null) {
            Pose3dc pose = subLevelAccess.logicalPose();
            position = pose.transformPosition((Vec3) position);
        }
        Position projected = SableCompanion.INSTANCE.projectOutOfSubLevel(level, position);
        return new Vec3(projected.x(), projected.y(), projected.z());
    }

    private ServerLevel _getRealLevel() {
        ServerLevel level = this.computer.getLevel();
        ResourceKey<Level> dimension = level.dimension();
        ServerLevel real = level.getServer().getLevel(dimension);
        return real != null ? real : level;
    }

    @LuaFunction
    public final Map<Integer, Map<String, Double>> getUpstream() {
        ServerLevel realLevel = _getRealLevel();
        Vec3 pos = _getWorldPos();
        StreamLevelData data = StreamLevelData.get(realLevel);
        Map<Integer, StreamLevelData.DimVec3> stream = data.getUpstream();
        Map<Integer, Map<String, Double>> visibleStream = new HashMap<>();

        stream.forEach((id, entry) -> {
            if (!entry.dimension().equals(realLevel.dimension())) return;

            Vec3 diff = entry.pos().subtract(pos);
            if (diff.length() > (AvionicsConfig.STREAM_DISTANCE.get() * 16)) return;

            double azimuth = (Math.atan2(diff.x, -diff.z) + 2 * Math.PI) % (2 * Math.PI);
            double elevation = Math.atan2(diff.y, Math.sqrt(diff.x * diff.x + diff.z * diff.z));

            Map<String, Double> azel = new HashMap<>();
            azel.put("az", azimuth);
            azel.put("el", elevation);
            visibleStream.put(id, azel);
        });

        return visibleStream;
    }
}