package com.aetherium.overlord.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModArmorMaterial implements ArmorMaterial {
    ARCHMAGE("aetherium_overlord:archmage", 38, new int[]{3, 6, 8, 3}, 30, 3.0f, 0.1f);

    private static final int[] BASE_DURABILITY = {13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protection;
    private final int enchantability;
    private final float toughness, knockback;

    ModArmorMaterial(String name, int mult, int[] prot, int ench, float tough, float kb) {
        this.name = name; this.durabilityMultiplier = mult; this.protection = prot;
        this.enchantability = ench; this.toughness = tough; this.knockback = kb;
    }

    @Override public int getDurabilityForType(ArmorItem.Type type) { return BASE_DURABILITY[type.ordinal()] * durabilityMultiplier; }
    @Override public int getDefenseForType(ArmorItem.Type type) { return protection[type.ordinal()]; }
    @Override public int getEnchantmentValue() { return enchantability; }
    @Override public SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_DIAMOND; }
    @Override public Ingredient getRepairIngredient() { return Ingredient.of(ModItems.ETHER_INGOT.get()); }
    @Override public String getName() { return name; }
    @Override public float getToughness() { return toughness; }
    @Override public float getKnockbackResistance() { return knockback; }
}
// Примечание: порядок ArmorItem.Type в 1.20.1: HELMET, CHESTPLATE, LEGGINGS, BOOTS (массивы выше в этом порядке).
// getName() должен совпадать с путём текстур: assets/aetherium_overlord/textures/models/armor/archmage_layer_1.png и _layer_2.png.
// Так как name содержит "modid:name", Forge подставит namespace автоматически.
