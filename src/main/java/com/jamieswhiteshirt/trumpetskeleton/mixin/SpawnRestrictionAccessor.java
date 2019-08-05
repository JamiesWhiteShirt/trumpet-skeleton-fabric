package com.jamieswhiteshirt.trumpetskeleton.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpawnRestriction.class)
public interface SpawnRestrictionAccessor {
    @Invoker("method_20637(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/Heightmap$Type;Lnet/minecraft/entity/SpawnRestriction$class_4306;)V")
    static <T extends MobEntity> void trumpetskeleton$setRestrictions(EntityType<T> entityType_1, SpawnRestriction.Location spawnRestriction$Location_1, Heightmap.Type heightmap$Type_1, SpawnRestriction.class_4306<T> spawnRestriction$class_4306_1) {
    }
}
