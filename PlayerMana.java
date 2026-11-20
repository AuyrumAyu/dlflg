package com.aetherium.overlord.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerMana implements INBTSerializable<CompoundTag> {
    private float mana = 100f;
    private float baseMax = 100f;
    private float regenPerSecond = 2f;
    private MagicPath path = MagicPath.NONE;
    private int pathLevel = 0;
    private int pathPoints = 0;
    private float lastSynced = -1f;
    // не сохраняется: считается каждый тик из брони/артефактов (1.0 = без бонуса, 6.0 = +500%)
    private float maxMultiplier = 1f;

    public float getMana() { return mana; }
    public float getEffectiveMax() { return baseMax * maxMultiplier; }
    public float getBaseMax() { return baseMax; }
    public float getRegen() { return regenPerSecond * (1f + (maxMultiplier - 1f) * 0.2f); }
    public MagicPath getPath() { return path; }
    public int getPathLevel() { return pathLevel; }
    public int getPathPoints() { return pathPoints; }
    public boolean isPathChosen() { return path != MagicPath.NONE; }

    public void setMaxMultiplier(float m) { this.maxMultiplier = Math.max(1f, m); }

    public void setPath(MagicPath p) {
        if (this.path == MagicPath.NONE) {
            this.path = p;
            this.pathLevel = 1;
            this.baseMax += 25f;
        }
    }

    public boolean consume(float amount) {
        if (mana < amount) return false;
        mana -= amount;
        return true;
    }

    public void addMana(float amount) { mana = Math.min(getEffectiveMax(), mana + amount); }

    public void addPathPoints(int pts) { pathPoints += pts; }

    /** Прокачка: тратит очки, +25 к лимиту маны и +0.25 к регену за уровень. */
    public boolean levelUp() {
        int cost = pathLevel + 1;
        if (pathLevel >= 10 || pathPoints < cost) return false;
        pathPoints -= cost;
        pathLevel++;
        baseMax += 25f;
        regenPerSecond += 0.25f;
        return true;
    }

    public void tick() {
        float max = getEffectiveMax();
        if (mana < max) mana = Math.min(max, mana + getRegen() / 20f);
        if (mana > max) mana = max;
    }

    /** true, если значение изменилось и пора слать пакет клиенту */
    public boolean needsSync() {
        if (Math.abs(mana - lastSynced) >= 0.5f) { lastSynced = mana; return true; }
        return false;
    }
    public void forceSync() { lastSynced = -1f; }

    public void copyFrom(PlayerMana o) {
        this.mana = o.mana; this.baseMax = o.baseMax; this.regenPerSecond = o.regenPerSecond;
        this.path = o.path; this.pathLevel = o.pathLevel; this.pathPoints = o.pathPoints;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag t = new CompoundTag();
        t.putFloat("mana", mana);
        t.putFloat("baseMax", baseMax);
        t.putFloat("regen", regenPerSecond);
        t.putInt("path", path.ordinal());
        t.putInt("level", pathLevel);
        t.putInt("points", pathPoints);
        return t;
    }

    @Override
    public void deserializeNBT(CompoundTag t) {
        mana = t.getFloat("mana");
        baseMax = t.contains("baseMax") ? t.getFloat("baseMax") : 100f;
        regenPerSecond = t.contains("regen") ? t.getFloat("regen") : 2f;
        path = MagicPath.byId(t.getInt("path"));
        pathLevel = t.getInt("level");
        pathPoints = t.getInt("points");
    }
}
