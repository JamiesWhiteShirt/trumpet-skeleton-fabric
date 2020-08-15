package com.jamieswhiteshirt.trumpetskeleton.mixin;

import com.jamieswhiteshirt.trumpetskeleton.TrumpetSkeleton;
import com.jamieswhiteshirt.trumpetskeleton.common.entity.TrumpetSkeletonEntityTypes;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(
        method = "addMonsters(Lnet/minecraft/world/biome/SpawnSettings$Builder;III)V",
        at = @At("TAIL")
    )
    private static void addMonsters(SpawnSettings.Builder builder, int zombieWeight, int zombieVillagerWeight, int skeletonWeight, CallbackInfo ci) {
        if (skeletonWeight > 0) {
            int weight = (int) Math.ceil(skeletonWeight * TrumpetSkeleton.relativeSpawnWeight);
            builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(TrumpetSkeletonEntityTypes.TRUMPET_SKELETON, weight, 1, 1));
        }
    }
}
