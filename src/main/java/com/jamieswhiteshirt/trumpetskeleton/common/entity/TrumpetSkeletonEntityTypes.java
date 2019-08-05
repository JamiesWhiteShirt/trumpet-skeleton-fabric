package com.jamieswhiteshirt.trumpetskeleton.common.entity;

import com.jamieswhiteshirt.trumpetskeleton.common.entity.mob.TrumpetSkeletonEntity;
import com.jamieswhiteshirt.trumpetskeleton.mixin.SpawnRestrictionMixin;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class TrumpetSkeletonEntityTypes {
    public static final EntityType<TrumpetSkeletonEntity> TRUMPET_SKELETON = register("trumpet_skeleton", FabricEntityTypeBuilder.create(EntityCategory.MONSTER, TrumpetSkeletonEntity::new).size(EntityDimensions.changing(0.6F, 1.99F)));

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> builder) {
        return register(new Identifier("trumpet-skeleton", id), builder);
    }

    private static <T extends Entity> EntityType<T> register(Identifier id, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(Registry.ENTITY_TYPE, id, builder.build());
    }

    public static void init() { }
}
