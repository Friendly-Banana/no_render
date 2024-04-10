package me.banana.no_render;

import net.minecraftforge.fml.config.ModConfig;

public class NoRender {
    public static final String MOD_ID = "no_render";

    public static void checkConfigReload(ModConfig config) {
        if (config.getModId().equals(MOD_ID)) {
            NoRenderConfig.CONFIG.onConfigReload();
        }
    }
}