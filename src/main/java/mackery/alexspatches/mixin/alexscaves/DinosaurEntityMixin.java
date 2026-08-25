package mackery.alexspatches.mixin.alexscaves;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mackery.alexspatches.AlexsPatches;
import mackery.alexspatches.Config;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

@Mixin(targets = "com.github.alexmodguy.alexscaves.server.entity.living.DinosaurEntity", remap = false)
public abstract class DinosaurEntityMixin extends TamableAnimal {

    protected DinosaurEntityMixin(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private double alexspatches$watchdogLastX;
    @Unique
    private double alexspatches$watchdogLastY;
    @Unique
    private double alexspatches$watchdogLastZ;
    @Unique
    private int alexspatches$watchdogStuckTicks;
    @Unique
    private boolean alexspatches$watchdogHasReference;

    @Inject(method = "tick", at = @At("TAIL"))
    private void alexspatches$watchdogStuckPathing(CallbackInfo ci) {
        if (!Config.DINOSAUR_STUCK_WATCHDOG.get() || this.level().isClientSide) {
            return;
        }

        PathNavigation navigation = this.getNavigation();
        if (navigation.isDone() || navigation.getPath() == null) {
            alexspatches$watchdogHasReference = false;
            alexspatches$watchdogStuckTicks = 0;
            return;
        }

        if (!alexspatches$watchdogHasReference) {
            alexspatches$watchdogRememberPosition();
            alexspatches$watchdogHasReference = true;
            return;
        }

        double dx = this.getX() - alexspatches$watchdogLastX;
        double dy = this.getY() - alexspatches$watchdogLastY;
        double dz = this.getZ() - alexspatches$watchdogLastZ;
        boolean madeProgress = (dx * dx + dy * dy + dz * dz) > 0.01D;

        if (madeProgress) {
            alexspatches$watchdogRememberPosition();
            alexspatches$watchdogStuckTicks = 0;
            return;
        }

        alexspatches$watchdogStuckTicks++;
        if (alexspatches$watchdogStuckTicks > Config.DINOSAUR_STUCK_TICK_THRESHOLD.get()) {
            navigation.stop();
            alexspatches$watchdogStuckTicks = 0;
            alexspatches$watchdogHasReference = false;
            AlexsPatches.LOGGER.debug(
                    "[AlexsPatches] Stopped navigation for a stuck dinosaur ({}) at {} that made no progress "
                            + "for {} ticks (see Alex's Caves issue #148)",
                    this.getType(), this.blockPosition(), Config.DINOSAUR_STUCK_TICK_THRESHOLD.get());
        }
    }

    @Unique
    private void alexspatches$watchdogRememberPosition() {
        alexspatches$watchdogLastX = this.getX();
        alexspatches$watchdogLastY = this.getY();
        alexspatches$watchdogLastZ = this.getZ();
    }
}
