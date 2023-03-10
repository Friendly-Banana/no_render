package me.banana.no_render.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import me.banana.no_render.NoRenderConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;runUpdates(IZZ)I"))
    private boolean skipLightUpdates(LevelLightEngine lightEngine, int i, boolean skylight, boolean skipEdgeLightPropagation) {
        return !NoRenderConfig.CONFIG.skipLightUpdates.get();
    }

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;entitiesForRendering()Ljava/lang/Iterable;"))
    private Iterable<Entity> hideEntities(Iterable<Entity> entitiesForRendering) {
        if (NoRenderConfig.CONFIG.hideAllEntities.get()) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entitiesForRendering.spliterator(), true)
            .filter(entity -> NoRenderConfig.hiddenTypes.stream().noneMatch(type -> type.isInstance(entity)))
            .toList();
    }

    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderChunkLayer(Lnet/minecraft/client/renderer/RenderType;Lcom/mojang/blaze3d/vertex/PoseStack;DDDLcom/mojang/math/Matrix4f;)V"))
    private boolean hideBlocks(LevelRenderer levelRenderer, RenderType renderType, PoseStack poseStack, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix) {
        return !NoRenderConfig.CONFIG.hideBlocks.get();
    }

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$CompiledChunk;getRenderableBlockEntities()Ljava/util/List;"))
    private List<BlockEntity> hideBlockEntities(List<BlockEntity> blockEntities) {
        return NoRenderConfig.CONFIG.hideBlockEntities.get() ? Collections.emptyList() : blockEntities;
    }

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/LevelRenderer;globalBlockEntities:Ljava/util/Set;", ordinal = 1))
    private Set<BlockEntity> hideGlobalBlockEntities(Set<BlockEntity> globalBlockEntities) {
        return NoRenderConfig.CONFIG.hideGlobalBlockEntities.get() ? Collections.emptySet() : globalBlockEntities;
    }

    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/math/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V"))
    private boolean hideSky(LevelRenderer levelRenderer, PoseStack poseStack, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable) {
        return !NoRenderConfig.CONFIG.hideSky.get();
    }
}
