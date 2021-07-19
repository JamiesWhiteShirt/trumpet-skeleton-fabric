package com.jamieswhiteshirt.trumpetskeleton.client;

import com.jamieswhiteshirt.trumpetskeleton.common.entity.TrumpetSkeletonEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;

@Environment(EnvType.CLIENT)
public class TrumpetSkeletonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(TrumpetSkeletonEntityTypes.TRUMPET_SKELETON, SkeletonEntityRenderer::new);
    }
}
