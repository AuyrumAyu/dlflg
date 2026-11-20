package com.aetherium.overlord.network;

import com.aetherium.overlord.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public record ManaSyncPacket(float mana, float max, int path, int level, int points) {
    public static void encode(ManaSyncPacket p, FriendlyByteBuf buf) {
        buf.writeFloat(p.mana); buf.writeFloat(p.max);
        buf.writeInt(p.path); buf.writeInt(p.level); buf.writeInt(p.points);
    }
    public static ManaSyncPacket decode(FriendlyByteBuf buf) {
        return new ManaSyncPacket(buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt());
    }
    public static void handle(ManaSyncPacket p, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ClientManaData.set(p.mana, p.max, p.path, p.level, p.points)));
        ctx.get().setPacketHandled(true);
    }
}
