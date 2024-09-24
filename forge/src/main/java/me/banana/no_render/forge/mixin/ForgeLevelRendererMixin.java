package me.banana.no_render.forge.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.banana.no_render.NoRenderConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class ForgeLevelRendererMixin {
    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/particle/ParticleEngine;render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;)V"))
    private boolean hideParticles(ParticleEngine instance, LightTexture lightTexture, Camera camera, float v, Frustum frustum) {
        return !NoRenderConfig.CONFIG.hideParticles.get();
    }
}
