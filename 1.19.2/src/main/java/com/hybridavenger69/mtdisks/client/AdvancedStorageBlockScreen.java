package com.hybridavenger69.mtdisks.client;


import com.hybridavenger69.mtdisks.blockentity.AdvancedStorageBlockEntity;
import com.hybridavenger69.mtdisks.container.AdvancedStorageBlockContainerMenu;
import com.hybridavenger69.mtstorage.screen.StorageScreen;
import com.hybridavenger69.mtstorage.screen.StorageScreenSynchronizationParameters;


import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AdvancedStorageBlockScreen extends StorageScreen<AdvancedStorageBlockContainerMenu> {
    public AdvancedStorageBlockScreen(AdvancedStorageBlockContainerMenu container, Inventory inventory, Component title) {
        super(
                container,
                inventory,
                title,
                "gui/storage.png",
                new StorageScreenSynchronizationParameters(null,
                        AdvancedStorageBlockEntity.REDSTONE_MODE,
                        AdvancedStorageBlockEntity.COMPARE,
                        AdvancedStorageBlockEntity.WHITELIST_BLACKLIST,
                        AdvancedStorageBlockEntity.PRIORITY,
                        AdvancedStorageBlockEntity.ACCESS_TYPE
                ),
                AdvancedStorageBlockEntity.STORED::getValue,
                () -> (long) ((AdvancedStorageBlockEntity) container.getBlockEntity()).getItemStorageType().getCapacity());
    }
}
