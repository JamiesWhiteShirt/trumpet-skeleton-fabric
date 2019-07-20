package com.jamieswhiteshirt.trumpetskeleton.common.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TrumpetSkeletonSoundEvents {
    public static final SoundEvent ENTITY_TRUMPET_SKELETON_AMBIENT = register("entity.trumpet_skeleton.ambient");
    public static final SoundEvent ENTITY_PARROT_IMITATE_TRUMPET_SKELETON = register("entity.parrot.imitate.trumpet_skeleton");
    public static final SoundEvent TRUMPET_USE = register("item.trumpet.use");

    private static SoundEvent register(String id) {
        return register(new Identifier("trumpet-skeleton", id));
    }

    private static SoundEvent register(Identifier id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void init() { }
}
