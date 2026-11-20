package com.aetherium.overlord.client.renderer;

import com.aetherium.overlord.AetheriumOverlordMod;
import com.aetherium.overlord.item.CrystalStaffItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CrystalStaffModel extends GeoModel<CrystalStaffItem> {
    @Override public ResourceLocation getModelResource(CrystalStaffItem a) {
        return new ResourceLocation(AetheriumOverlordMod.MODID, "geo/item/crystal_staff.geo.json");
    }
    @Override public ResourceLocation getTextureResource(CrystalStaffItem a) {
        return new ResourceLocation(AetheriumOverlordMod.MODID, "textures/item/crystal_staff.png");
    }
    @Override public ResourceLocation getAnimationResource(CrystalStaffItem a) {
        return new ResourceLocation(AetheriumOverlordMod.MODID, "animations/item/crystal_staff.animation.json");
    }
}
