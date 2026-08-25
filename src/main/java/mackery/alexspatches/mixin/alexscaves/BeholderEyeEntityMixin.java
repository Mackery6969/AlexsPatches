package mackery.alexspatches.mixin.alexscaves;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mackery.alexspatches.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

@Mixin(targets = "com.github.alexmodguy.alexscaves.server.entity.item.BeholderEyeEntity", remap = false)
public class BeholderEyeEntityMixin {

    @Unique
    private BlockPos alexspatches$loadedChunkOriginPos;

    @Redirect(
            method = "loadChunksAround",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;blockPosition()Lnet/minecraft/core/BlockPos;",
                    ordinal = 1
            )
    )
    private BlockPos alexspatches$fixLeakedChunkTickets(Entity self, boolean load) {
        BlockPos currentPos = self.blockPosition();
        if (!Config.BEHOLDER_EYE_CHUNK_LEAK_GUARD.get()) {
            return currentPos;
        }
        if (load) {
            alexspatches$loadedChunkOriginPos = currentPos;
            return currentPos;
        }
        return alexspatches$loadedChunkOriginPos != null ? alexspatches$loadedChunkOriginPos : currentPos;
    }
}
