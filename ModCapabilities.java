package com.aetherium.overlord.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<PlayerMana> PLAYER_MANA =
            CapabilityManager.get(new CapabilityToken<>() {});
}
