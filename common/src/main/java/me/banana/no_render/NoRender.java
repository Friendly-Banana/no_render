package me.banana.no_render;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.minecraftforge.fml.config.ModConfig;

public class NoRender {
    public static final String MOD_ID = "no_render";

    public static void init() {
        MixinExtrasBootstrap.init();
    }

    public static void checkConfigReload(ModConfig config) {
        if (config.getModId().equals(MOD_ID)) {
            NoRenderConfig.CONFIG.onConfigReload();
        }
    }
}