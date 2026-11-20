package com.aetherium.overlord.network;

import com.aetherium.overlord.AetheriumOverlordMod;
import com.aetherium.overlord.capability.ModCapabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(AetheriumOverlordMod.MODID, "main"),
            () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, ManaSyncPacket.class,
                ManaSyncPacket::encode, ManaSyncPacket::decode, ManaSyncPacket::handle);
        CHANNEL.registerMessage(id++, ChoosePathPacket.class,
                ChoosePathPacket::encode, ChoosePathPacket::decode, ChoosePathPacket::handle);
    }

    public static void syncMana(ServerPlayer player) {
        player.getCapability(ModCapabilities.PLAYER_MANA).ifPresent(m ->
                CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                        new ManaSyncPacket(m.getMana(), m.getEffectiveMax(),
                                m.getPath().ordinal(), m.getPathLevel(), m.getPathPoints())));
    }
}
