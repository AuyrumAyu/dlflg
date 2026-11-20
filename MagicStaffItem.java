package com.aetherium.overlord.item;

import com.aetherium.overlord.capability.MagicPath;
import com.aetherium.overlord.capability.ModCapabilities;
import com.aetherium.overlord.capability.PlayerMana;
import com.aetherium.overlord.entity.MagicBoltProjectile;
import com.aetherium.overlord.network.ModNetwork;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public abstract class MagicStaffItem extends Item {
    protected final MagicPath path;
    protected final int tier;
    protected final float manaCost, ultimateCost;
    protected final int cooldown, ultimateCooldown;   // в тиках

    public MagicStaffItem(Properties props, MagicPath path, int tier,
                          float manaCost, int cooldownTicks, float ultimateCost, int ultimateCooldownTicks) {
        super(props); // в Properties задавайте .durability(N) (это уже даёт стак 1)
        this.path = path; this.tier = tier;
        this.manaCost = manaCost; this.cooldown = cooldownTicks;
        this.ultimateCost = ultimateCost; this.ultimateCooldown = ultimateCooldownTicks;
    }

    /** ПКМ. Вернуть true, если заклинание сработало. */
    protected abstract boolean castPrimary(ServerLevel level, ServerPlayer player, ItemStack stack);
    /** Shift+ПКМ. */
    protected abstract boolean castUltimate(ServerLevel level, ServerPlayer player, ItemStack stack);

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResultHolder.sidedSuccess(stack, true);
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.fail(stack);

        ServerPlayer sp = (ServerPlayer) player;
        ServerLevel sl = (ServerLevel) level;
        Optional<PlayerMana> opt = sp.getCapability(ModCapabilities.PLAYER_MANA).resolve();
        if (opt.isEmpty()) return InteractionResultHolder.fail(stack);
        PlayerMana mana = opt.get();

        boolean creative = sp.isCreative();
        if (!creative && path != MagicPath.NONE && mana.getPath() != path) {
            sp.displayClientMessage(Component.literal("§cЭтот посох принадлежит Пути: " + path.title), true);
            return InteractionResultHolder.fail(stack);
        }

        boolean ult = sp.isShiftKeyDown();
        float cost = ult ? ultimateCost : manaCost;
        if (!creative && !mana.consume(cost)) {
            sp.displayClientMessage(Component.literal("§9Не хватает маны (" + (int) cost + ")"), true);
            return InteractionResultHolder.fail(stack);
        }

        boolean ok = ult ? castUltimate(sl, sp, stack) : castPrimary(sl, sp, stack);
        if (!ok) {
            if (!creative) mana.addMana(cost); // возврат
            return InteractionResultHolder.fail(stack);
        }
        stack.hurtAndBreak(ult ? 3 : 1, sp, p -> p.broadcastBreakEvent(hand));
        sp.getCooldowns().addCooldown(this, ult ? ultimateCooldown : cooldown);
        ModNetwork.syncMana(sp);
        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    /** Выстрел кастомным снарядом из глаз игрока. */
    protected void shootBolt(ServerLevel level, ServerPlayer player, float damage, float aoeRadius,
                             ParticleOptions particle, float speed) {
        Vec3 look = player.getLookAngle();
        MagicBoltProjectile bolt = new MagicBoltProjectile(level, player, look.x, look.y, look.z);
        bolt.setPos(player.getX() + look.x, player.getEyeY() - 0.1 + look.y, player.getZ() + look.z);
        bolt.configure(damage, aoeRadius, particle, path);
        bolt.shoot(look.x, look.y, look.z, speed, 0f);
        level.addFreshEntity(bolt);
    }

    protected float scaledDamage(float base) { return base * (1f + tier * 0.35f); }

    public MagicPath getPath() { return path; }
    public int getTier() { return tier; }
}
