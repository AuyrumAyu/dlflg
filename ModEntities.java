package com.aetherium.overlord.registry;

import com.aetherium.overlord.AetheriumOverlordMod;
import com.aetherium.overlord.entity.MagicBoltProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES, AetheriumOverlordMod.MODID);

    public static final RegistryObject<EntityType<MagicBoltProjectile>> MAGIC_BOLT = ENTITIES.register("magic_bolt",
            () -> EntityType.Builder.<MagicBoltProjectile>of(MagicBoltProjectile::new, MobCategory.MISC)
                    .sized(0.3f, 0.3f).clientTrackingRange(4).updateInterval(10).build("magic_bolt"));
}
