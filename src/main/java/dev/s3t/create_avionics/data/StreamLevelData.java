package dev.s3t.create_avionics.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

public class StreamLevelData extends SavedData {

    public record DimVec3(ResourceKey<Level> dimension, Vec3 pos) {}

    private final Map<Integer, DimVec3> upstream = new HashMap<>();

    public static StreamLevelData get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
            new SavedData.Factory<>(StreamLevelData::new, StreamLevelData::load),
            "create_avionics"
        );
    }

    public void putUpstream(int id, ResourceKey<Level> dimension, Vec3 pos) {
        System.out.printf("Adding to upstream: id-%d dim-%s pos-%s%n", id, dimension.location(), pos);
        upstream.put(id, new DimVec3(dimension, pos));
        setDirty();
    }

    public void removeUpstream(int id) {
        upstream.remove(id);
        setDirty();
    }

    public Map<Integer, DimVec3> getUpstream() {
        return Collections.unmodifiableMap(upstream);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        CompoundTag upstreamTag = new CompoundTag();
        upstream.forEach((id, entry) -> {
            CompoundTag compound = new CompoundTag();
            compound.putString("dim", entry.dimension().location().toString());
            compound.putDouble("x", entry.pos().x);
            compound.putDouble("y", entry.pos().y);
            compound.putDouble("z", entry.pos().z);
            upstreamTag.put(String.valueOf(id), compound);
        });
        tag.put("upstream", upstreamTag);
        return tag;
    }

    public static StreamLevelData load(CompoundTag tag, HolderLookup.Provider provider) {
        StreamLevelData data = new StreamLevelData();
        CompoundTag upstreamTag = tag.getCompound("upstream");
        upstreamTag.getAllKeys().forEach(key -> {
            CompoundTag compound = upstreamTag.getCompound(key);
            ResourceKey<Level> dim = ResourceKey.create(
                net.minecraft.core.registries.Registries.DIMENSION,
                ResourceLocation.parse(compound.getString("dim"))
            );
            Vec3 pos = new Vec3(
                compound.getDouble("x"),
                compound.getDouble("y"),
                compound.getDouble("z")
            );
            data.upstream.put(Integer.parseInt(key), new DimVec3(dim, pos));
        });
        return data;
    }
}
