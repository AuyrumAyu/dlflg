package com.aetherium.overlord.registry;

import com.aetherium.overlord.AetheriumOverlordMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AetheriumOverlordMod.MODID);

    // --- руды ---
    public static final RegistryObject<Block> ETHER_ORE = registerBlock("ether_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(3f, 3f).requiresCorrectToolForDrops().sound(SoundType.STONE),
                    UniformInt.of(2, 5)));
    public static final RegistryObject<Block> DEEPSLATE_ETHER_ORE = registerBlock("deepslate_ether_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE)
                    .strength(4.5f, 3f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
                    UniformInt.of(2, 5)));
    public static final RegistryObject<Block> MANA_ORE = registerBlock("mana_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(3f, 3f).requiresCorrectToolForDrops().sound(SoundType.STONE).lightLevel(s -> 5),
                    UniformInt.of(3, 7)));
    public static final RegistryObject<Block> ETHER_BLOCK = registerBlock("ether_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN)
                    .strength(5f, 6f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    // --- декор ---
    public static final RegistryObject<Block> RUNE_BRICKS = registerBlock("rune_bricks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE)
                    .strength(2f, 6f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> ENCHANTED_PLANKS = registerBlock("enchanted_planks",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(2f, 3f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GLOWING_CRYSTAL = registerBlock("glowing_crystal",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(1.5f).sound(SoundType.AMETHYST).lightLevel(s -> 12)));
    public static final RegistryObject<Block> MAGIC_GLASS = registerBlock("magic_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.of().strength(0.4f).sound(SoundType.GLASS)
                    .noOcclusion().lightLevel(s -> 4)));

    // --- механизмы (пока обычные блоки; логику добавьте через BlockEntity) ---
    public static final RegistryObject<Block> RUNE_INSCRIPTION_TABLE = registerBlock("rune_inscription_table",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE)
                    .strength(3.5f).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<Block> FUSION_ALTAR = registerBlock("fusion_altar",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)
                    .strength(5f, 1200f).requiresCorrectToolForDrops().sound(SoundType.STONE)
                    .lightLevel(s -> 8).noOcclusion()));
    public static final RegistryObject<Block> MANA_OBELISK = registerBlock("mana_obelisk",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN)
                    .strength(5f, 1200f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)
                    .lightLevel(s -> 10).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> obj = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(obj.get(), new Item.Properties()));
        return obj;
    }
}
