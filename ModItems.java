package com.aetherium.overlord.registry;

import com.aetherium.overlord.AetheriumOverlordMod;
import com.aetherium.overlord.capability.MagicPath;
import com.aetherium.overlord.item.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AetheriumOverlordMod.MODID);

    // --- ресурсы и ключевые предметы ---
    public static final RegistryObject<Item> PATH_TOME = ITEMS.register("path_tome",
            () -> new PathTomeItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PATH_SHARD = ITEMS.register("path_shard",
            () -> new PathShardItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ETHER = ITEMS.register("raw_ether",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ETHER_INGOT = ITEMS.register("ether_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLANK_RUNE = ITEMS.register("blank_rune",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ETHER_CORE = ITEMS.register("ether_core",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    // --- посохи (стак 1 обеспечивает durability) ---
    public static final RegistryObject<Item> APPRENTICE_STAFF = ITEMS.register("apprentice_staff",
            () -> new ElementalStaff(new Item.Properties().durability(250),
                    MagicPath.NONE, 1, 5, 20, 40, 400, 3f));
    public static final RegistryObject<Item> EMBER_STAFF = ITEMS.register("ember_staff",
            () -> new ElementalStaff(new Item.Properties().durability(400),
                    MagicPath.PYROMANCER, 1, 10, 30, 60, 500, 5f));
    public static final RegistryObject<Item> SOUL_STAFF = ITEMS.register("soul_staff",
            () -> new ElementalStaff(new Item.Properties().durability(400),
                    MagicPath.NECROMANCER, 1, 10, 30, 60, 500, 5f));
    public static final RegistryObject<Item> HOURGLASS_STAFF = ITEMS.register("hourglass_staff",
            () -> new ElementalStaff(new Item.Properties().durability(400),
                    MagicPath.CHRONOMANCER, 1, 10, 20, 60, 400, 4f));
    public static final RegistryObject<Item> GROVE_STAFF = ITEMS.register("grove_staff",
            () -> new ElementalStaff(new Item.Properties().durability(400),
                    MagicPath.DRUID, 1, 10, 30, 60, 500, 4f));
    public static final RegistryObject<Item> WORLD_CREATOR_STAFF = ITEMS.register("world_creator_staff",
            () -> new CrystalStaffItem(new Item.Properties().durability(5000).rarity(Rarity.EPIC),
                    MagicPath.NONE, 6, 60, 80, 800, 12000, 20f));

    // --- оружие ---
    public static final RegistryObject<Item> ETHER_SWORD = ITEMS.register("ether_sword",
            () -> new SwordItem(Tiers.NETHERITE, 4, -2.4f, new Item.Properties().rarity(Rarity.RARE)));

    // --- броня Эфирного Архимага ---
    public static final RegistryObject<Item> ARCHMAGE_HELMET = ITEMS.register("archmage_helmet",
            () -> new ArmorItem(ModArmorMaterial.ARCHMAGE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARCHMAGE_CHESTPLATE = ITEMS.register("archmage_chestplate",
            () -> new ArmorItem(ModArmorMaterial.ARCHMAGE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ARCHMAGE_LEGGINGS = ITEMS.register("archmage_leggings",
            () -> new ArmorItem(ModArmorMaterial.ARCHMAGE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ARCHMAGE_BOOTS = ITEMS.register("archmage_boots",
            () -> new ArmorItem(ModArmorMaterial.ARCHMAGE, ArmorItem.Type.BOOTS, new Item.Properties()));
}
// Итого предметов в ModItems: 18. Вместе с блоками из ModBlocks (11) — 29 зарегистрированных позиций.
