package me.banana.no_render.mixin;

import me.banana.no_render.NoRenderConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Shadow
    public static void setupNoFog() {
    }

    @Inject(method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void hideFog(Camera camera, FogRenderer.FogMode fogMode, float viewDistance, boolean thickFog, float partialTicks, CallbackInfo ci) {
        if (NoRenderConfig.CONFIG.hideTerrainFog.get() && fogMode == FogRenderer.FogMode.FOG_TERRAIN) {
            setupNoFog();
            ci.cancel();
        }
    }
}
