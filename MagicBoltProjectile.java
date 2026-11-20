package com.aetherium.overlord.entity;

import com.aetherium.overlord.capability.MagicPath;
import com.aetherium.overlord.registry.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.AABB;

public class MagicBoltProjectile extends AbstractHurtingProjectile {
    private float damage = 6f, aoe = 0f;
    private ParticleOptions particle = ParticleTypes.ENCHANT;
    private MagicPath element = MagicPath.NONE;
    private int life = 0;

    public MagicBoltProjectile(EntityType<? extends MagicBoltProjectile> type, Level level) { super(type, level); }

    public MagicBoltProjectile(Level level, LivingEntity owner, double dx, double dy, double dz) {
        super(ModEntities.MAGIC_BOLT.get(), owner, dx, dy, dz, level);
    }

    public void configure(float damage, float aoe, ParticleOptions particle, MagicPath element) {
        this.damage = damage; this.aoe = aoe; this.particle = particle; this.element = element;
    }

    @Override protected ParticleOptions getTrailParticle() { return particle; }
    @Override protected boolean shouldBurn() { return false; }
    @Override public boolean isOnFire() { return false; }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && ++life > 80) discard();
        if (level().isClientSide) {
            level().addParticle(particle, getX(), getY(), getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult res) {
        super.onHitEntity(res);
        if (level().isClientSide) return;
        res.getEntity().hurt(damageSources().indirectMagic(this, getOwner()), damage);
        if (element == MagicPath.PYROMANCER) res.getEntity().setSecondsOnFire(4);
    }

    @Override
    protected void onHit(HitResult res) {
        super.onHit(res);
        if (level().isClientSide) return;
        if (aoe > 0f) {
            AABB box = getBoundingBox().inflate(aoe);
            for (LivingEntity e : level().getEntitiesOfClass(LivingEntity.class, box, e -> e != getOwner())) {
                e.hurt(damageSources().indirectMagic(this, getOwner()), damage * 0.6f);
            }
            ((ServerLevel) level()).sendParticles(particle, getX(), getY(), getZ(), 40, aoe / 2, aoe / 2, aoe / 2, 0.05);
        }
        discard();
    }
}
