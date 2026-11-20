package com.aetherium.overlord.client;

import net.minecraft.client.Minecraft;

public class ClientHooks {
    public static void openPathScreen() { Minecraft.getInstance().setScreen(new PathSelectScreen()); }
}
