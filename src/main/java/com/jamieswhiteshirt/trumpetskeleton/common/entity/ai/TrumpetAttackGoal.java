package com.jamieswhiteshirt.trumpetskeleton.common.entity.ai;

import com.jamieswhiteshirt.trumpetskeleton.common.item.TrumpetSkeletonItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileUtil;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;

import java.util.EnumSet;

public class TrumpetAttackGoal<T extends HostileEntity> extends Goal {
    private final T actor;
    private final double speed;
    private int attackInterval;
    private final float squaredRange;
    private int cooldown = -1;
    private int seeCounter;
    private boolean strafeLeft;
    private boolean strafeBack;
    private int strafeChangeTimer = -1;

    public TrumpetAttackGoal(T actor, double speed, int attackInterval, float range) {
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }


    public void setAttackInterval(int attackInterval) {
        this.attackInterval = attackInterval;
    }

    @Override
    public boolean canStart() {
        return actor.getTarget() != null && isHoldingTrumpet();
    }

    protected boolean isHoldingTrumpet() {
        return actor.isHolding(TrumpetSkeletonItems.TRUMPET);
    }

    @Override
    public boolean shouldContinue() {
        return (canStart() || !actor.getNavigation().isIdle()) && isHoldingTrumpet();
    }

    @Override
    public void start() {
        super.start();
        actor.setAttacking(true);
    }

    @Override
    public void stop() {
        super.stop();
        actor.setAttacking(false);
        seeCounter = 0;
        cooldown = -1;
        actor.clearActiveItem();
    }

    @Override
    public void tick() {
        LivingEntity target = actor.getTarget();
        if (target != null) {
            double squaredDistance = actor.squaredDistanceTo(target.x, target.getBoundingBox().minY, target.z);
            boolean canSeeTarget = actor.getVisibilityCache().canSee(target);
            boolean boolean_2 = seeCounter > 0;
            if (canSeeTarget != boolean_2) {
                this.seeCounter = 0;
            }

            if (canSeeTarget) {
                ++seeCounter;
            } else {
                --seeCounter;
            }

            if (squaredDistance <= squaredRange && seeCounter >= 20) {
                actor.getNavigation().stop();
                ++strafeChangeTimer;
            } else {
                actor.getNavigation().startMovingTo(target, speed);
                --strafeChangeTimer;
            }

            if (strafeChangeTimer >= 20) {
                if (actor.getRand().nextFloat() < 0.3D) {
                    strafeLeft = !strafeLeft;
                }

                if (actor.getRand().nextFloat() < 0.3D) {
                    strafeBack = !strafeBack;
                }

                this.strafeChangeTimer = 0;
            }

            if (strafeChangeTimer > -1) {
                if (squaredDistance > squaredRange * 0.75F) {
                    strafeBack = false;
                } else if (squaredDistance < squaredRange * 0.25F) {
                    strafeBack = true;
                }

                actor.getMoveControl().strafeTo(strafeBack ? -0.5F : 0.5F, strafeLeft ? 0.5F : -0.5F);
                actor.lookAtEntity(target, 30.0F, 30.0F);
            } else {
                actor.getLookControl().lookAt(target, 30.0F, 30.0F);
            }

            if (actor.isUsingItem()) {
                if (!canSeeTarget && seeCounter < -60) {
                    actor.clearActiveItem();
                }
            } else if (--cooldown <= 0 && seeCounter >= -60) {
                actor.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(actor, TrumpetSkeletonItems.TRUMPET));
                cooldown = actor.getRand().nextInt(attackInterval);
            }

        }
    }
}
