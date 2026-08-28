package mackery.alexspatches.mixin.alexsmobs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import com.github.alexthe666.alexsmobs.entity.EntityAnteater;
import mackery.alexspatches.Config;
import net.minecraft.client.renderer.MultiBufferSource;

@Mixin(targets = "com.github.alexthe666.alexsmobs.client.render.layer.LayerAnteaterTongueItem", remap = false)
public class LayerAnteaterTongueItemMixin {

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/alexthe666/alexsmobs/client/model/ModelLeafcutterAnt;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V",
                    shift = At.Shift.AFTER
            )
    )
    private void alexspatches$fixUnbalancedPoseStack(
            PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityAnteater anteater,
                    float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (Config.ANTEATER_TONGUE_POSE_STACK_GUARD.get()) {
            matrixStackIn.popPose();
        }
    }
}
