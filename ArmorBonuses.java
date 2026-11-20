package com.aetherium.overlord.events;

import com.aetherium.overlord.registry.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class ArmorBonuses {
    public static float manaMultiplier(Player p) {
        boolean full = p.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ARCHMAGE_HELMET.get())
                && p.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ARCHMAGE_CHESTPLATE.get())
                && p.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ARCHMAGE_LEGGINGS.get())
                && p.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ARCHMAGE_BOOTS.get());
        return full ? 6f : 1f; // 6.0 = +500%
    }
}
// (ARCHMAGE_* регистрируются так же, как остальная броня; в шаблоне раздела 8 они упомянуты для компиляции — добавьте их в ModItems по образцу.)
