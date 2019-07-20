package com.jamieswhiteshirt.trumpetskeleton;

import com.google.common.collect.Lists;
import com.jamieswhiteshirt.trumpetskeleton.common.entity.TrumpetSkeletonEntityTypes;
import com.jamieswhiteshirt.trumpetskeleton.common.item.TrumpetSkeletonItems;
import com.jamieswhiteshirt.trumpetskeleton.common.sound.TrumpetSkeletonSoundEvents;
import com.jamieswhiteshirt.trumpetskeleton.mixin.ParrotEntityAccessor;
import com.jamieswhiteshirt.trumpetskeleton.mixin.WeightedPicker$EntryAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.function.Consumer;

public class TrumpetSkeleton implements ModInitializer {
    @Override
    public void onInitialize() {
        TrumpetSkeletonItems.init();
        TrumpetSkeletonSoundEvents.init();
        TrumpetSkeletonEntityTypes.init();

        ParrotEntityAccessor.trumpetskeleton$getMobSounds().put(TrumpetSkeletonEntityTypes.TRUMPET_SKELETON, TrumpetSkeletonSoundEvents.ENTITY_PARROT_IMITATE_TRUMPET_SKELETON);

        addRegistryProcessor(Registry.BIOME, biome -> {
            List<Biome.SpawnEntry> spawnList = biome.getEntitySpawnList(EntityCategory.MONSTER);
            List<Biome.SpawnEntry> toAdd = Lists.newArrayList();
            for (Biome.SpawnEntry spawnEntry : spawnList) {
                if (spawnEntry.type == EntityType.SKELETON) {
                    WeightedPicker$EntryAccessor accessor = (WeightedPicker$EntryAccessor) spawnEntry;
                    int weight = (int) Math.ceil(accessor.getWeight() / 4.0D);
                    toAdd.add(new Biome.SpawnEntry(TrumpetSkeletonEntityTypes.TRUMPET_SKELETON, weight, 1, 1));
                }
            }
            spawnList.addAll(toAdd);
        });
    }

    private static <T> void addRegistryProcessor(Registry<T> registry, Consumer<T> visitor) {
        registry.forEach(visitor);
        RegistryEntryAddedCallback.event(registry).register((rawId, id, object) -> visitor.accept(object));
    }
}
