package com.hybridavenger69.mtdisks.setup;


import com.hybridavenger69.mtdisks.MtDisks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {

    public static void init() {
        var SERVER_BUILDER = new ForgeConfigSpec.Builder();
        SERVER_BUILDER.comment(MtDisks.MODNAME + "'s config");


        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}
