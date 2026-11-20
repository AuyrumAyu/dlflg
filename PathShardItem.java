package com.aetherium.overlord.item;

import com.aetherium.overlord.capability.ModCapabilities;
import com.aetherium.overlord.network.ModNetwork;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PathShardItem extends Item {
    public PathShardItem(Properties p) { super(p); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            sp.getCapability(ModCapabilities.PLAYER_MANA).ifPresent(m -> {
                if (!m.isPathChosen()) {
                    sp.displayClientMessage(Component.literal("§cСначала выбери Путь (фолиант)"), true);
                    return;
                }
                m.addPathPoints(1);
                m.levelUp();            // автоповышение, если хватает очков
                m.forceSync();
                stack.shrink(1);
                ModNetwork.syncMana(sp);
            });
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
