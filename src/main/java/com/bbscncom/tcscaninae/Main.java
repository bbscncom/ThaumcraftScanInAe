package com.bbscncom.tcscaninae;

import net.blay09.mods.tcinventoryscan.CommonProxy;
import net.blay09.mods.tcinventoryscan.net.NetworkHandler;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies
        = "required-after:appliedenergistics2;" +
        "required-after:mixinbooter;" +
        "required-after:thaumcraft",
        acceptedMinecraftVersions = "[1.12.2]")
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
//@MixinLoader
public class Main
        implements ILateMixinLoader {
    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_ID);

    public Main() {
    }

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins." + Tags.MOD_ID + ".json");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

}
