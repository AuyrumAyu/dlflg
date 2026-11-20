package com.aetherium.overlord.registry;

import com.aetherium.overlord.AetheriumOverlordMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AetheriumOverlordMod.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.aetherium_overlord"))
                    .icon(() -> new ItemStack(ModItems.WORLD_CREATOR_STAFF.get()))
                    .displayItems((params, out) ->
                            ModItems.ITEMS.getEntries().forEach(e -> out.accept(e.get())))
                    .build());
}
