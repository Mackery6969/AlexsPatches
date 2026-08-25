package mackery.alexspatches.mixin.alexsmobs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mackery.alexspatches.AlexsPatches;
import mackery.alexspatches.Config;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

@Mixin(targets = {
        "com.github.alexthe666.alexsmobs.entity.EntityManedWolf",
        "com.github.alexthe666.alexsmobs.entity.EntityRainFrog"
}, remap = false)
public class DancingJukeboxPacketMixin {

    @Redirect(
            method = "setRecordPlayingNearby",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/alexthe666/alexsmobs/AlexsMobs;sendMSGToServer(Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"
            )
    )
    private static void alexspatches$suppressBrokenServerboundDancePacket(CustomPacketPayload message) {
        if (!Config.DANCING_JUKEBOX_PACKET_GUARD.get()) {
            net.neoforged.neoforge.network.PacketDistributor.sendToServer(message);
            return;
        }
        AlexsPatches.LOGGER.debug("[AlexsPatches] Suppressed a serverbound {} packet that would have crashed "
                + "the connection (see Alex's Mobs issue #51/#59)", message.type().id());
    }
}
