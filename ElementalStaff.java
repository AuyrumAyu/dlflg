package com.aetherium.overlord.item;

import com.aetherium.overlord.capability.MagicPath;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ElementalStaff extends MagicStaffItem {
    private final float baseDamage;

    public ElementalStaff(Properties p, MagicPath path, int tier, float mana, int cd, float ultMana, int ultCd, float baseDamage) {
        super(p, path, tier, mana, cd, ultMana, ultCd);
        this.baseDamage = baseDamage;
    }

    private ParticleOptions particle() {
        return switch (path) {
            case PYROMANCER -> ParticleTypes.FLAME;
            case NECROMANCER -> ParticleTypes.SOUL_FIRE_FLAME;
            case CHRONOMANCER -> ParticleTypes.END_ROD;
            case DRUID -> ParticleTypes.HAPPY_VILLAGER;
            default -> ParticleTypes.ENCHANT;
        };
    }

    @Override
    protected boolean castPrimary(ServerLevel level, ServerPlayer player, ItemStack stack) {
        float aoe = path == MagicPath.PYROMANCER ? 1.5f + tier * 0.4f : 0f;
        shootBolt(level, player, scaledDamage(baseDamage), aoe, particle(), 1.6f);
        if (path == MagicPath.NECROMANCER) player.heal(scaledDamage(baseDamage) * 0.15f); // вампиризм
        if (path == MagicPath.DRUID) player.heal(1f + tier);
        return true;
    }

    @Override
    protected boolean castUltimate(ServerLevel level, ServerPlayer player, ItemStack stack) {
        double r = 6 + tier * 2;
        AABB box = player.getBoundingBox().inflate(r);
        switch (path) {
            case PYROMANCER -> {
                // метеоритный дождь: снаряды падают сверху вокруг игрока
                for (int i = 0; i < 4 + tier * 2; i++) {
                    double x = player.getX() + (level.random.nextDouble() - 0.5) * r * 2;
                    double z = player.getZ() + (level.random.nextDouble() - 0.5) * r * 2;
                    var m = new com.aetherium.overlord.entity.MagicBoltProjectile(level, player, 0, -1, 0);
                    m.setPos(x, player.getY() + 18, z);
                    m.configure(scaledDamage(baseDamage) * 2f, 3f, ParticleTypes.LAVA, path);
                    m.shoot(0, -1, 0, 1.2f, 0f);
                    level.addFreshEntity(m);
                }
            }
            case NECROMANCER -> {
                for (int i = 0; i < 2 + tier; i++) {
                    IronGolem g = EntityType.IRON_GOLEM.create(level); // заглушка-миньон (замените на своих)
                    if (g == null) continue;
                    g.moveTo(player.getX() + i - 1, player.getY(), player.getZ() + 1);
                    g.setPlayerCreated(true);
                    level.addFreshEntity(g);
                }
            }
            case CHRONOMANCER -> {
                for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, e -> e != player)) {
                    e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 5, 4));
                    e.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * 5, 1));
                }
                Vec3 look = player.getLookAngle().scale(8);
                player.teleportTo(player.getX() + look.x, player.getY(), player.getZ() + look.z); // блинк
            }
            case DRUID -> {
                for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box)) {
                    if (e == player || e instanceof net.minecraft.world.entity.player.Player) {
                        e.heal(6f + tier * 2);
                        e.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 8, 1));
                    } else if (e instanceof Mob) {
                        e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 4, 6)); // корни
                    }
                }
            }
            default -> {
                for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, e -> e != player)) {
                    e.hurt(player.damageSources().indirectMagic(player, player), scaledDamage(baseDamage));
                }
            }
        }
        level.sendParticles(particle(), player.getX(), player.getY() + 1, player.getZ(), 80, r / 3, 1, r / 3, 0.05);
        return true;
    }
}
