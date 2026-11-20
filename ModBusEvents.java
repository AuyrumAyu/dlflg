package com.aetherium.overlord.events;

import com.aetherium.overlord.AetheriumOverlordMod;
import com.aetherium.overlord.capability.PlayerMana;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AetheriumOverlordMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent e) { e.register(PlayerMana.class); }
}
