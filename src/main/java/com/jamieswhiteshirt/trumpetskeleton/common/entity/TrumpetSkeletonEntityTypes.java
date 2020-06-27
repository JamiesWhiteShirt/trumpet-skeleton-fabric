package com.jamieswhiteshirt.trumpetskeleton.common.entity;

import com.jamieswhiteshirt.trumpetskeleton.common.entity.mob.TrumpetSkeletonEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TrumpetSkeletonEntityTypes {
    public static final EntityType<TrumpetSkeletonEntity> TRUMPET_SKELETON = register("trumpet_skeleton", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TrumpetSkeletonEntity::new).dimensions(EntityDimensions.changing(0.6F, 1.99F)));

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> builder) {
        return register(new Identifier("trumpet-skeleton", id), builder);
    }

    private static <T extends Entity> EntityType<T> register(Identifier id, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(Registry.ENTITY_TYPE, id, builder.build());
    }

    public static void init() { }
}
