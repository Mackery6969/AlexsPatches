package mackery.alexspatches.mixin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import mackery.alexspatches.AlexsPatches;

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
        if (mixinClassName.startsWith(ALEXSCAVES_PACKAGE) || mixinClassName.startsWith(ALEXSMOBS_PACKAGE)) {
            boolean present = alexspatches$classExists(targetClassName);
            if (!present) {
                AlexsPatches.LOGGER.info("Skipping {} - target class {} was not found (its mod is not installed)",
                        mixinClassName, targetClassName);
            }
            return present;
        }
        return true;
    }

    private static boolean alexspatches$classExists(String className) {
        try {
            Class.forName(className, false, AlexsPatchesMixinPlugin.class.getClassLoader());
            return true;
        } catch (Throwable t) {
            return false;
        }
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
