package com.aetherium.overlord.network;

import com.aetherium.overlord.capability.MagicPath;
import com.aetherium.overlord.capability.ModCapabilities;
import com.aetherium.overlord.item.PathTomeItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public record ChoosePathPacket(int path) {
    public static void encode(ChoosePathPacket p, FriendlyByteBuf buf) { buf.writeInt(p.path); }
    public static ChoosePathPacket decode(FriendlyByteBuf buf) { return new ChoosePathPacket(buf.readInt()); }

    public static void handle(ChoosePathPacket p, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null || !(player.getMainHandItem().getItem() instanceof PathTomeItem)) return;
            MagicPath chosen = MagicPath.byId(p.path);
            if (chosen == MagicPath.NONE) return;
            player.getCapability(ModCapabilities.PLAYER_MANA).ifPresent(m -> {
                if (m.isPathChosen()) return;
                m.setPath(chosen);
                m.addPathPoints(1);
                m.forceSync();
                player.getMainHandItem().shrink(1);
                ModNetwork.syncMana(player);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
