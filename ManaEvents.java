package com.aetherium.overlord.events;

import com.aetherium.overlord.AetheriumOverlordMod;
import com.aetherium.overlord.capability.*;
import com.aetherium.overlord.network.ModNetwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AetheriumOverlordMod.MODID)
public class ManaEvents {

    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof Player) {
            e.addCapability(new ResourceLocation(AetheriumOverlordMod.MODID, "mana"), new PlayerManaProvider());
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone e) {
        e.getOriginal().reviveCaps();
        e.getOriginal().getCapability(ModCapabilities.PLAYER_MANA).ifPresent(old ->
                e.getEntity().getCapability(ModCapabilities.PLAYER_MANA).ifPresent(n -> n.copyFrom(old)));
        e.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END || e.player.level().isClientSide) return;
        e.player.getCapability(ModCapabilities.PLAYER_MANA).ifPresent(m -> {
            m.setMaxMultiplier(ArmorBonuses.manaMultiplier(e.player)); // см. раздел 3
            m.tick();
            if (e.player.tickCount % 10 == 0 && m.needsSync()) ModNetwork.syncMana((ServerPlayer) e.player);
        });
    }

    @SubscribeEvent
    public static void login(PlayerEvent.PlayerLoggedInEvent e) {
        if (e.getEntity() instanceof ServerPlayer sp) { sp.getCapability(ModCapabilities.PLAYER_MANA).ifPresent(PlayerMana::forceSync); ModNetwork.syncMana(sp); }
    }
    @SubscribeEvent
    public static void respawn(PlayerEvent.PlayerRespawnEvent e) {
        if (e.getEntity() instanceof ServerPlayer sp) ModNetwork.syncMana(sp);
    }
    @SubscribeEvent
    public static void dimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        if (e.getEntity() instanceof ServerPlayer sp) ModNetwork.syncMana(sp);
    }
}
