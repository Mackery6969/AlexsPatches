package mackery.alexspatches.mixin.alexscaves;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mackery.alexspatches.AlexsPatches;
import mackery.alexspatches.Config;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.status.ChunkStatusTasks;

@Mixin(ChunkStatusTasks.class)
public class ChunkStatusTasksMixin {

    @Redirect(
            method = "generateFeatures",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/chunk/ChunkGenerator;applyBiomeDecoration(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/StructureManager;)V"
            )
    )
    private static void alexspatches$guardBiomeDecoration(ChunkGenerator generator, WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
        if (!Config.WORLDGEN_DECORATION_CRASH_GUARD.get()) {
            generator.applyBiomeDecoration(level, chunk, structureManager);
            return;
        }
        try {
            generator.applyBiomeDecoration(level, chunk, structureManager);
        } catch (Exception e) {
            AlexsPatches.LOGGER.error(
                    "[AlexsPatches] Biome decoration threw while generating chunk {} - skipping decoration for "
                            + "this chunk instead of hanging/crashing the server (see Alex's Caves issue #172). "
                            + "The chunk will generate without some/all of its decoration features.",
                    chunk.getPos(), e);
        }
    }
}
