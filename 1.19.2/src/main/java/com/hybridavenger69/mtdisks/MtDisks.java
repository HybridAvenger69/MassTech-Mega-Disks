package com.hybridavenger69.mtdisks;

import com.hybridavenger69.mtdisks.setup.ClientSetup;
import com.hybridavenger69.mtdisks.setup.ModSetup;
import com.hybridavenger69.mtdisks.setup.Registration;

import com.mojang.logging.LogUtils;

import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MtDisks.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MtDisks {
    public static final String MODID = "mtdisks";
    public static final String MODNAME = "MassTech Digital Mega Disks";

    public static final Logger LOGGER = LogUtils.getLogger();


    public MtDisks() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientSetup::init);
            eventBus.addListener(ClientSetup::onModelBake);
        });
        Registration.init();


        // Register the setup method for modloading

        eventBus.addListener(ModSetup::init);
    }
}