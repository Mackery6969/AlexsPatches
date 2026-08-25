package mackery.alexspatches;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(AlexsPatches.MODID)
public class AlexsPatches {
    public static final String MODID = "alexspatches";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String ALEXSCAVES_MODID = "alexscaves";
    public static final String ALEXSMOBS_MODID = "alexsmobs";

    public AlexsPatches(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        boolean caves = ModList.get().isLoaded(ALEXSCAVES_MODID);
        boolean mobs = ModList.get().isLoaded(ALEXSMOBS_MODID);
        LOGGER.info("Alex's Patches loaded. Alex's Caves present: {}, Alex's Mobs present: {}", caves, mobs);
        if (!caves && !mobs) {
            LOGGER.warn("Neither Alex's Caves nor Alex's Mobs is installed - Alex's Patches has nothing to patch.");
        }
    }
}
