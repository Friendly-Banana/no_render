package me.banana.no_render.neoforge;

import me.banana.no_render.NoRender;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = NoRender.MOD_ID, dist = Dist.CLIENT)
public class NoRenderNeoForgeClient {
    public NoRenderNeoForgeClient(ModContainer container) {
        // Perform logic that should only be executed on the physical client
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
