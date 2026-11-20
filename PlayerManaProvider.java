package com.aetherium.overlord.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerManaProvider implements ICapabilitySerializable<CompoundTag> {
    private final PlayerMana mana = new PlayerMana();
    private final LazyOptional<PlayerMana> optional = LazyOptional.of(() -> mana);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCapabilities.PLAYER_MANA ? optional.cast() : LazyOptional.empty();
    }

    @Override public CompoundTag serializeNBT() { return mana.serializeNBT(); }
    @Override public void deserializeNBT(CompoundTag nbt) { mana.deserializeNBT(nbt); }
}
