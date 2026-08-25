package mackery.alexspatches.mixin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import mackery.alexspatches.AlexsPatches;
import net.neoforged.fml.ModList;

public class AlexsPatchesMixinPlugin implements IMixinConfigPlugin {

    private static final String ALEXSCAVES_PACKAGE = "mackery.alexspatches.mixin.alexscaves.";
    private static final String ALEXSMOBS_PACKAGE = "mackery.alexspatches.mixin.alexsmobs.";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith(ALEXSCAVES_PACKAGE)) {
            boolean loaded = ModList.get().isLoaded(AlexsPatches.ALEXSCAVES_MODID);
            if (!loaded) {
                AlexsPatches.LOGGER.info("Skipping {} - Alex's Caves is not installed", mixinClassName);
            }
            return loaded;
        }
        if (mixinClassName.startsWith(ALEXSMOBS_PACKAGE)) {
            boolean loaded = ModList.get().isLoaded(AlexsPatches.ALEXSMOBS_MODID);
            if (!loaded) {
                AlexsPatches.LOGGER.info("Skipping {} - Alex's Mobs is not installed", mixinClassName);
            }
            return loaded;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
