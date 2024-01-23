package com.hybridavenger69.mtdisks.container;

import com.hybridavenger69.mtdisks.blockentity.AdvancedStorageBlockEntity;
import com.hybridavenger69.mtdisks.setup.Registration;
import com.hybridavenger69.mtstorage.container.BaseContainerMenu;
import com.hybridavenger69.mtstorage.container.slot.filter.FilterSlot;
import net.minecraft.world.entity.player.Player;

public class AdvancedStorageBlockContainerMenu extends BaseContainerMenu {
    public AdvancedStorageBlockContainerMenu(int windowId, Player player, AdvancedStorageBlockEntity storageBlockEntity) {
        super(Registration.ITEM_STORAGE_CONTAINER.get(storageBlockEntity.getItemStorageType()).get(), storageBlockEntity, player, windowId);

        for (int i = 0; i < 9; ++i) {
            addSlot(new FilterSlot(storageBlockEntity.getNode().getFilters(), i, 8 + (18 * i), 20));
        }

        addPlayerInventory(8, 141);

        transferManager.addItemFilterTransfer(player.getInventory(), storageBlockEntity.getNode().getFilters());
    }


}
