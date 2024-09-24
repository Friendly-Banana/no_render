package me.banana.no_render.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.banana.no_render.NoRenderConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;runLightUpdates()I"))
    private boolean skipLightUpdates(LevelLightEngine instance) {
        return !NoRenderConfig.CONFIG.skipLightUpdates.get();
    }

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;entitiesForRendering()Ljava/lang/Iterable;"))
    private Iterable<Entity> hideEntities(Iterable<Entity> entitiesForRendering) {
        if (NoRenderConfig.CONFIG.hideAllEntities.get()) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entitiesForRendering.spliterator(), true)
            .filter(NoRenderConfig.HIDE_ENTITY_PREDICATE)
            .toList();
    }

    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSectionLayer(Lnet/minecraft/client/renderer/RenderType;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V"))
    private boolean hideBlocks(LevelRenderer instance, RenderType renderType, double d, double e, double f, Matrix4f matrix4f, Matrix4f matrix4f2) {
        return !NoRenderConfig.CONFIG.hideBlocks.get();
    }

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher$CompiledSection;getRenderableBlockEntities()Ljava/util/List;"))
    private List<BlockEntity> hideBlockEntities(List<BlockEntity> blockEntities) {
        return NoRenderConfig.CONFIG.hideBlockEntities.get() ? Collections.emptyList() : blockEntities;
    }

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/LevelRenderer;globalBlockEntities:Ljava/util/Set;", ordinal = 1))
    private Set<BlockEntity> hideGlobalBlockEntities(Set<BlockEntity> globalBlockEntities) {
        return NoRenderConfig.CONFIG.hideGlobalBlockEntities.get() ? Collections.emptySet() : globalBlockEntities;
    }

    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V"))
    private boolean hideSky(LevelRenderer instance, Matrix4f matrix4f, Matrix4f matrix4f2, float f, Camera camera, boolean bl, Runnable runnable) {
        return !NoRenderConfig.CONFIG.hideSky.get();
    }
}
