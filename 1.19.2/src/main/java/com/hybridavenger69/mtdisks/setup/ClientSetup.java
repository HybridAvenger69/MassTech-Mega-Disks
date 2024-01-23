package com.hybridavenger69.mtdisks.setup;

import com.hybridavenger69.mtdisks.items.storage.item.ItemStorageType;
import com.hybridavenger69.mtdisks.client.AdvancedStorageBlockScreen;
import com.hybridavenger69.mtstorage.render.BakedModelOverrideRegistry;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    private static final BakedModelOverrideRegistry BAKED_MODEL_OVERRIDE_REGISTRY = new BakedModelOverrideRegistry();

    private ClientSetup() {
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        //Version checker


        for (var type : ItemStorageType.values())
            MenuScreens.register(Registration.ITEM_STORAGE_CONTAINER.get(type).get(), AdvancedStorageBlockScreen::new);



    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.BakingCompleted e) {
        for (var id : e.getModels().keySet()) {
            var factory = BAKED_MODEL_OVERRIDE_REGISTRY.get(new ResourceLocation(id.getNamespace(), id.getPath()));
            if (factory != null) {
                e.getModels().put(id, factory.create(e.getModels().get(id), e.getModels()));
            }
        }
    }
}