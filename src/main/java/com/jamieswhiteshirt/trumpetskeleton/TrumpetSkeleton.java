package com.jamieswhiteshirt.trumpetskeleton;

import com.jamieswhiteshirt.trumpetskeleton.common.entity.TrumpetSkeletonEntityTypes;
import com.jamieswhiteshirt.trumpetskeleton.common.item.TrumpetSkeletonItems;
import com.jamieswhiteshirt.trumpetskeleton.common.sound.TrumpetSkeletonSoundEvents;
import com.jamieswhiteshirt.trumpetskeleton.mixin.ParrotEntityAccessor;
import com.jamieswhiteshirt.trumpetskeleton.mixin.WeightedPicker$EntryAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public class TrumpetSkeleton implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("trumpet-skeleton");

    private static double relativeSpawnWeight = 0.05D;

    @Override
    public void onInitialize() {
        TrumpetSkeletonItems.init();
        TrumpetSkeletonSoundEvents.init();
        TrumpetSkeletonEntityTypes.init();

        ParrotEntityAccessor.trumpetskeleton$getMobSounds().put(TrumpetSkeletonEntityTypes.TRUMPET_SKELETON, TrumpetSkeletonSoundEvents.ENTITY_PARROT_IMITATE_TRUMPET_SKELETON);

        Properties configuration = new Properties();
        configuration.setProperty("relativeSpawnWeight", String.valueOf(relativeSpawnWeight));
        File configurationFile = new File(FabricLoader.getInstance().getConfigDirectory(), "trumpet-skeleton.properties");

        if (configurationFile.exists()) {
            try (InputStream in = new FileInputStream(configurationFile)) {
                configuration.load(in);
                LOGGER.info("Loaded configuration file \"" + configurationFile + "\"");
            } catch (IOException e) {
                LOGGER.error("Could not read configuration file \"" + configurationFile + "\"", e);
            }
        } else {
            try (OutputStream out = new FileOutputStream(configurationFile)) {
                configuration.store(out, "Trumpet Skeleton configuration");
                LOGGER.info("Generated configuration file \"" + configurationFile + "\"");
            } catch (IOException e) {
                LOGGER.error("Could not write configuration file \"" + configurationFile + "\"", e);
            }
        }

        String relativeSpawnRateString = configuration.getProperty("relativeSpawnWeight");
        try {
            relativeSpawnWeight = Double.parseDouble(relativeSpawnRateString);
        } catch (NumberFormatException e) {
            LOGGER.error("Error processing configuration file \"" + configurationFile + "\".");
            LOGGER.error("Expected configuration value for relativeSpawnWeight to be a number, found \"" + relativeSpawnRateString + "\".");
            LOGGER.error("Using default value \"" + relativeSpawnWeight + "\" instead.");
        }

        if (relativeSpawnWeight > 0) {
            addRegistryProcessor(Registry.BIOME, biome -> {
                List<Biome.SpawnEntry> spawnList = biome.getEntitySpawnList(EntityCategory.MONSTER);
                int skeletonWeight = 0;
                for (Biome.SpawnEntry spawnEntry : spawnList) {
                    if (spawnEntry.type == EntityType.SKELETON) {
                        WeightedPicker$EntryAccessor accessor = (WeightedPicker$EntryAccessor) spawnEntry;
                        skeletonWeight += accessor.getWeight();
                    }
                }
                if (skeletonWeight > 0) {
                    int weight = (int) Math.ceil(skeletonWeight * relativeSpawnWeight);
                    spawnList.add(new Biome.SpawnEntry(TrumpetSkeletonEntityTypes.TRUMPET_SKELETON, weight, 1, 1));
                }
            });
        }
    }

    private static <T> void addRegistryProcessor(Registry<T> registry, Consumer<T> visitor) {
        registry.forEach(visitor);
        RegistryEntryAddedCallback.event(registry).register((rawId, id, object) -> visitor.accept(object));
    }
}
