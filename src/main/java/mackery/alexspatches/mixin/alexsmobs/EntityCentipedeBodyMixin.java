package mackery.alexspatches.mixin.alexsmobs;

import com.github.alexthe666.alexsmobs.entity.EntityCentipedeBody;
import mackery.alexspatches.Config;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityCentipedeBody.class)
public class EntityCentipedeBodyMixin {

    @Unique
    private double alexspatches$cachedLowPartHeight = 0;

    @Unique
    private double alexspatches$cachedHighPartHeight = 0;

    @Unique
    private boolean alexspatches$isThrottledTick() {
        return Config.THROTTLE_CENTIPEDE_HEIGHT_SCAN.get()
                && ((Entity) (Object) this).tickCount % Config.CENTIPEDE_HEIGHT_SCAN_INTERVAL.get() != 0;
    }

    @Inject(method = "getLowPartHeight", at = @At("HEAD"), cancellable = true)
    private void alexspatches$throttleLowPartHeight(
            final double x, final double y, final double z, final CallbackInfoReturnable<Double> cir) {
        if (this.alexspatches$isThrottledTick()) {
            cir.setReturnValue(this.alexspatches$cachedLowPartHeight);
        }
    }

    @Inject(method = "getLowPartHeight", at = @At("RETURN"), cancellable = true)
    private void alexspatches$cacheLowPartHeight(
            final double x, final double y, final double z, final CallbackInfoReturnable<Double> cir) {
        this.alexspatches$cachedLowPartHeight = cir.getReturnValue();
    }

    @Inject(method = "getHighPartHeight", at = @At("HEAD"), cancellable = true)
    private void alexspatches$throttleHighPartHeight(
            final double x, final double y, final double z, final CallbackInfoReturnable<Double> cir) {
        if (this.alexspatches$isThrottledTick()) {
            cir.setReturnValue(this.alexspatches$cachedHighPartHeight);
        }
    }

    @Inject(method = "getHighPartHeight", at = @At("RETURN"), cancellable = true)
    private void alexspatches$cacheHighPartHeight(
            final double x, final double y, final double z, final CallbackInfoReturnable<Double> cir) {
        this.alexspatches$cachedHighPartHeight = cir.getReturnValue();
    }
}
