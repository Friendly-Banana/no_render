package me.banana.no_render.fabric.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.banana.no_render.NoRenderConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class FabricLevelRendererMixin {
    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleEngine;render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;F)V"))
    private boolean hideParticles(ParticleEngine instance, LightTexture lightTexture, Camera camera, float f) {
        return !NoRenderConfig.CONFIG.hideParticles.get();
    }
}
