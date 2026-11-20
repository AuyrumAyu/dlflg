package com.aetherium.overlord;

import com.aetherium.overlord.network.ModNetwork;
import com.aetherium.overlord.registry.ModBlocks;
import com.aetherium.overlord.registry.ModCreativeTabs;
import com.aetherium.overlord.registry.ModEntities;
import com.aetherium.overlord.registry.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(AetheriumOverlordMod.MODID)
public class AetheriumOverlordMod {
    public static final String MODID = "aetherium_overlord";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AetheriumOverlordMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModEntities.ENTITIES.register(modBus);
        ModCreativeTabs.TABS.register(modBus);

        modBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetwork::register);
        LOGGER.info("Aetherium Overlord: инициализация завершена");
    }
}
