package mackery.alexspatches.mixin.alexsmobs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mackery.alexspatches.Config;
import net.neoforged.neoforge.client.event.ViewportEvent;

@Mixin(targets = "com.github.alexthe666.alexsmobs.client.event.ClientEvents", remap = false)
public class ClientEventsMixin {

    @Inject(
            method = "onFogDensity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;getEffect()Lnet/minecraft/core/Holder;"
            ),
            cancellable = true
    )
    private void alexspatches$fixPowerDownFogCrash(ViewportEvent.RenderFog event, CallbackInfo ci) {
        if (Config.POWER_DOWN_FOG_CRASH_GUARD.get()) {
            ci.cancel();
        }
    }
}
