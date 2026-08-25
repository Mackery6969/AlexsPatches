package mackery.alexspatches;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // -- Shared Patches ---

    // --- Alex's Caves Patches ---
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

    // --- Alex's Mobs patches ---

    public static final ModConfigSpec.BooleanValue DANCING_JUKEBOX_PACKET_GUARD = BUILDER
                    .comment(
                                    "Alex's Mobs issues #51 and #59: Maned Wolf and Rain Frog both send a network packet",
                                    "(MessageStartDancing) to the server when they start/stop dancing near a jukebox, but that",
                                    "packet was only ever registered client-bound, so sending it crashes/disconnects the game",
                                    "with a ClassCastException. This suppresses that broken send. The entity still dances",
                                    "correctly for the player who triggered it; the only loss is other nearby players not",
                                    "seeing it dance too (a visual-only regression, vs. a hard disconnect).",
                                    "Default: true")
                    .define("dancingJukeboxPacketGuard", true);

    public static final ModConfigSpec.BooleanValue ANTEATER_TONGUE_POSE_STACK_GUARD = BUILDER
                    .comment(
                                    "Alex's Mobs issue #52: LayerAnteaterTongueItem pushes the render pose stack 3 times but",
                                    "only pops it twice whenever an anteater has an ant on its tongue, leaking one stack level",
                                    "per frame and crashing the client with \"Pose stack not empty\" almost immediately. This adds",
                                    "the missing pop.",
                                    "Default: true")
                    .define("anteaterTonguePoseStackGuard", true);

    public static final ModConfigSpec.BooleanValue POWER_DOWN_FOG_CRASH_GUARD = BUILDER
                    .comment(
                                    "Alex's Mobs issue #46: the POWER_DOWN effect's fog-dimming visual (used by the Grizzly",
                                    "Bear jump-scare easter egg) does a bad cast left over from an old Minecraft API",
                                    "(Holder<MobEffect> straight to EffectPowerDown), crashing the client 100% of the time the",
                                    "effect is active. This skips just that broken fog-dimming step; the jump-scare itself",
                                    "still happens normally.",
                                    "Default: true")
                    .define("powerDownFogCrashGuard", true);

    public static final ModConfigSpec.BooleanValue BEHOLDER_EYE_CHUNK_LEAK_GUARD = BUILDER
            .comment(
                    "Alex's Caves issue #139: BeholderEyeEntity (the possessable scrying eye) force-loads a",
                    "large chunk radius around itself when a player starts possessing it, but since the eye",
                    "moves while possessed, it force-unloads the wrong (final) position when possession ends -",
                    "permanently leaking the entire originally-loaded area every single time the item is used.",
                    "This remembers the actual loaded position and unloads that instead.",
                    "Default: true")
            .define("beholderEyeChunkLeakGuard", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}
