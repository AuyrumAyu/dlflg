package com.aetherium.overlord.client;

public class ClientManaData {
    public static float mana, max = 100f;
    public static int path, level, points;
    public static void set(float m, float mx, int p, int l, int pts) { mana = m; max = mx; path = p; level = l; points = pts; }
}
