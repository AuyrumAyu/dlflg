package com.aetherium.overlord.capability;

public enum MagicPath {
    NONE("Без пути", 0xFFFFFF),
    PYROMANCER("Пиромант", 0xFF5A1F),
    NECROMANCER("Некромант", 0x7B2CBF),
    CHRONOMANCER("Хрономант", 0x2EC4F1),
    DRUID("Друид", 0x4CAF50);

    public final String title;
    public final int color;

    MagicPath(String title, int color) {
        this.title = title;
        this.color = color;
    }

    public static MagicPath byId(int id) {
        MagicPath[] v = values();
        return id >= 0 && id < v.length ? v[id] : NONE;
    }
}
