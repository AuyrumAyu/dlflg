package com.aetherium.overlord.client;

import com.aetherium.overlord.capability.MagicPath;
import com.aetherium.overlord.network.ChoosePathPacket;
import com.aetherium.overlord.network.ModNetwork;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PathSelectScreen extends Screen {
    public PathSelectScreen() { super(Component.literal("Выбор Пути Магии")); }

    @Override
    protected void init() {
        int y = this.height / 2 - 40;
        for (MagicPath p : MagicPath.values()) {
            if (p == MagicPath.NONE) continue;
            addRenderableWidget(Button.builder(Component.literal(p.title), b -> {
                ModNetwork.CHANNEL.sendToServer(new ChoosePathPacket(p.ordinal()));
                onClose();
            }).bounds(this.width / 2 - 70, y, 140, 20).build());
            y += 26;
        }
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt) {
        renderBackground(g);
        g.drawCenteredString(this.font, "Выбери свой Путь. Выбор необратим!", this.width / 2, this.height / 2 - 65, 0xFFD700);
        super.render(g, mx, my, pt);
    }

    @Override public boolean isPauseScreen() { return false; }
}
