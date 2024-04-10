package me.banana.no_render.forge;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import me.banana.no_render.NoRender;
import me.banana.no_render.NoRenderConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static me.banana.no_render.NoRender.MOD_ID;

@Mod(MOD_ID)
public class NoRenderForge {
    public NoRenderForge() {
        MixinExtrasBootstrap.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NoRenderConfig.GENERAL_SPEC);
        FMLJavaModLoadingContext.get()
            .getModEventBus()
            .addListener((ModConfigEvent modConfigEvent) -> NoRender.checkConfigReload(modConfigEvent.getConfig()));
    }
}