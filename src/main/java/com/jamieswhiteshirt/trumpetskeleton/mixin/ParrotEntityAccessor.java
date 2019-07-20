package com.jamieswhiteshirt.trumpetskeleton.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ParrotEntity.class)
public interface ParrotEntityAccessor {
    @Accessor("MOB_SOUNDS")
    static Map<EntityType<?>, SoundEvent> trumpetskeleton$getMobSounds() {
        return null;
    }
}
