package mackery.alexspatches;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue WORLDGEN_DECORATION_CRASH_GUARD = BUILDER
                    .comment(
                                    "Alex's Caves issue #172: a mixin in Alex's Caves that guards against an out-of-range",
                                    "biome-decoration feature index still throws IndexOutOfBoundsException when the feature",
                                    "list is completely empty (e.g. after another mod's biome/registry reload), which hangs or",
                                    "crashes chunk generation. This wraps the vanilla biome-decoration step for a chunk in a",
                                    "try/catch so one bad chunk is skipped (partially decorated) instead of taking down the server.",
                                    "Default: true")
                    .define("worldgenDecorationCrashGuard", true);

    public static final ModConfigSpec.BooleanValue DINOSAUR_STUCK_WATCHDOG = BUILDER
                    .comment(
                                    "Alex's Caves issue #148: large dinosaurs (e.g. in Primordial Caves) that get stuck in",
                                    "terrain can repeatedly retry expensive pathfinding and lag out the server. This watches",
                                    "for a dinosaur that has an active path but hasn't actually moved for a while, and stops",
                                    "its navigation so it gives up instead of grinding forever.",
                                    "Default: true")
                    .define("dinosaurStuckWatchdog", true);

    public static final ModConfigSpec.IntValue DINOSAUR_STUCK_TICK_THRESHOLD = BUILDER
                    .comment(
                                    "How many ticks a dinosaur can go without meaningful movement (while following an active",
                                    "path) before the stuck watchdog kicks in and stops its navigation. 100 ticks = 5 seconds.",
                                    "Default: 100")
                    .defineInRange("dinosaurStuckTickThreshold", 100, 20, 1200);

    static final ModConfigSpec SPEC = BUILDER.build();
}
