package com.aetherium.overlord.client.renderer;

import com.aetherium.overlord.item.CrystalStaffItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CrystalStaffRenderer extends GeoItemRenderer<CrystalStaffItem> {
    public CrystalStaffRenderer() { super(new CrystalStaffModel()); }
}
