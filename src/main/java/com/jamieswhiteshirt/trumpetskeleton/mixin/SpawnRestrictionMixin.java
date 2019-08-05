package com.jamieswhiteshirt.trumpetskeleton.mixin;

import com.jamieswhiteshirt.trumpetskeleton.common.entity.TrumpetSkeletonEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnRestriction.class)
public abstract class SpawnRestrictionMixin {
    @Shadow private static <T extends MobEntity> void method_20637(EntityType<T> entityType_1, SpawnRestriction.Location spawnRestriction$Location_1, Heightmap.Type heightmap$Type_1, SpawnRestriction.class_4306<T> spawnRestriction$class_4306_1) {}

    @Inject(
        method = "<clinit>",
        at = @At("TAIL")
    )
    private static void clinit(CallbackInfo ci) {
        method_20637(TrumpetSkeletonEntityTypes.TRUMPET_SKELETON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::method_20680);
    }
}
