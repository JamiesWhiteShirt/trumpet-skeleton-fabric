package com.jamieswhiteshirt.trumpetskeleton.common.entity.mob;

import com.jamieswhiteshirt.trumpetskeleton.common.entity.ai.TrumpetAttackGoal;
import com.jamieswhiteshirt.trumpetskeleton.common.item.TrumpetSkeletonItems;
import com.jamieswhiteshirt.trumpetskeleton.common.sound.TrumpetSkeletonSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

public class TrumpetSkeletonEntity extends SkeletonEntity {
    private boolean constructed; // ugh.
    private final TrumpetAttackGoal<TrumpetSkeletonEntity> trumpetAttackGoal = new TrumpetAttackGoal<>(this, 1.0D, 40, 6.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false) {
        @Override
        public void stop() {
            super.stop();
            setAttacking(false);
        }

        @Override
        public void start() {
            super.start();
            setAttacking(true);
        }
    };

    public TrumpetSkeletonEntity(EntityType<? extends TrumpetSkeletonEntity> entityType, World world) {
        super(entityType, world);
        constructed = true;
        updateAttackType();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return TrumpetSkeletonSoundEvents.ENTITY_TRUMPET_SKELETON_AMBIENT;
    }

    @Override
    protected void initEquipment(LocalDifficulty localDifficulty_1) {
        super.initEquipment(localDifficulty_1);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(TrumpetSkeletonItems.TRUMPET));
    }

    @Override
    public void playAmbientSound() {
        if (!isAttacking()) {
            super.playAmbientSound();
        }
    }

    @Override
    public void updateAttackType() {
        if (constructed && world != null && !world.isClient) {
            goalSelector.remove(meleeAttackGoal);
            goalSelector.remove(trumpetAttackGoal);
            ItemStack stack = getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, TrumpetSkeletonItems.TRUMPET));
            if (stack.getItem() == TrumpetSkeletonItems.TRUMPET) {
                int attackInterval = 40;
                if (world.getDifficulty() != Difficulty.HARD) {
                    attackInterval = 80;
                }

                trumpetAttackGoal.setAttackInterval(attackInterval);
                goalSelector.add(4, trumpetAttackGoal);
            } else {
                goalSelector.add(4, meleeAttackGoal);
            }

        }
    }
}
