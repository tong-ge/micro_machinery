package com.dbydd.micro_machinery.util.handlers;

import com.dbydd.micro_machinery.Reference;
import com.dbydd.micro_machinery.blocks.tileentities.TileEntityBase;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileRegisteryHandler {
	public static void registerTileEntities() {

        GameRegistry.registerTileEntity(TileEntityBase.class, new ResourceLocation(Reference.MODID + ":klin"));
		
	}

}
