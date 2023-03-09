package me.banana.no_render.fabric;

import me.banana.no_render.NoRender;
import me.banana.no_render.NoRenderConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.config.ModConfig;

import static me.banana.no_render.NoRender.MOD_ID;

public class NoRenderFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NoRender.init();
        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.CLIENT, NoRenderConfig.GENERAL_SPEC);
        ModConfigEvent.RELOADING.register(NoRender::checkConfigReload);
    }
}