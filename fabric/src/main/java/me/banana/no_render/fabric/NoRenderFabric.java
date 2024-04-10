package me.banana.no_render.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import me.banana.no_render.NoRender;
import me.banana.no_render.NoRenderConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;

import static me.banana.no_render.NoRender.MOD_ID;

public class NoRenderFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, NoRenderConfig.GENERAL_SPEC);
        ModConfigEvents.reloading(MOD_ID).register(NoRender::checkConfigReload);
    }
}