package me.banana.no_render.neoforge;

import me.banana.no_render.NoRender;
import me.banana.no_render.NoRenderConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

@Mod(NoRender.MOD_ID)
public final class NoRenderNeoForge {
    public NoRenderNeoForge(ModContainer container, IEventBus modBus) {
        container.registerConfig(ModConfig.Type.CLIENT, NoRenderConfig.CONFIG_SPEC);
        modBus.addListener((ModConfigEvent modConfigEvent) -> checkConfigReload(modConfigEvent.getConfig()));
    }

    public static void checkConfigReload(ModConfig config) {
        if (config.getModId().equals(NoRender.MOD_ID)) {
            NoRenderConfig.CONFIG.onConfigReload();
        }
    }
}
