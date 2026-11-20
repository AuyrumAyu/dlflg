package com.aetherium.overlord.client;

import com.aetherium.overlord.AetheriumOverlordMod;
import com.aetherium.overlord.capability.MagicPath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AetheriumOverlordMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ManaHud {

    @SubscribeEvent
    public static void register(RegisterGuiOverlaysEvent e) {
        e.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "mana_bar", (gui, g, partialTick, w, h) -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.options.hideGui || mc.player.isSpectator()) return;
            draw(g, w, h, mc);
        });
    }

    private static void draw(GuiGraphics g, int w, int h, Minecraft mc) {
        int barW = 81, barH = 7;
        int x = w / 2 - 91, y = h - 59;
        float ratio = ClientManaData.max <= 0 ? 0 : Math.min(1f, ClientManaData.mana / ClientManaData.max);
        int color = 0xFF000000 | MagicPath.byId(ClientManaData.path).color;
        if (ClientManaData.path == 0) color = 0xFF3A7BFF;

        g.fill(x - 1, y - 1, x + barW + 1, y + barH + 1, 0xFF101018);   // рамка
        g.fill(x, y, x + barW, y + barH, 0xFF1C1C2A);                   // фон
        int filled = (int) (barW * ratio);
        g.fill(x, y, x + filled, y + barH, color);                       // заполнение
        g.fill(x, y, x + filled, y + 2, 0x66FFFFFF);                     // блик
        String text = (int) ClientManaData.mana + " / " + (int) ClientManaData.max;
        g.drawString(mc.font, text, x + barW + 4, y, 0xFFE8E8FF, true);
        if (ClientManaData.path != 0) {
            g.drawString(mc.font, MagicPath.byId(ClientManaData.path).title + " ур." + ClientManaData.level
                    + "  [очки: " + ClientManaData.points + "]", x, y - 10, color, true);
        }
    }
}
