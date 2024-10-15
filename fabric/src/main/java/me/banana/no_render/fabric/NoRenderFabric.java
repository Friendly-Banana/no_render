package me.banana.no_render.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import me.banana.no_render.NoRenderConfig;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;

import static me.banana.no_render.NoRender.MOD_ID;

public class NoRenderFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, NoRenderConfig.CONFIG_SPEC);
        NeoForgeModConfigEvents.reloading(MOD_ID).register(NoRenderFabric::checkConfigReload);
    }

    public static void checkConfigReload(ModConfig config) {
        if (config.getModId().equals(MOD_ID)) {
            NoRenderConfig.CONFIG.onConfigReload();
        }
    }
}