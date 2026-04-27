package dev.s3t.create_avionics;

import net.neoforged.neoforge.common.ModConfigSpec;


public class AvionicsConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_GETPOS = BUILDER
            .comment("Whether to enable avionics.getWorldPos()")
            .define("getposEnabled", true);

    public static final ModConfigSpec.IntValue STREAM_DISTANCE = BUILDER
            .comment("Antenna stream distance (in chunks)")
            .defineInRange("streamDistance", 64, 4, 128);

    static final ModConfigSpec SPEC = BUILDER.build();
}
