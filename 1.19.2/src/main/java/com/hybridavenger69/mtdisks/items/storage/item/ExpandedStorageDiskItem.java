package com.hybridavenger69.mtdisks.items.storage.item;

import com.hybridavenger69.mtdisks.items.storage.ExpandedStorageDisk;
import com.hybridavenger69.mtdisks.setup.Registration;
import com.hybridavenger69.mtstorage.api.storage.StorageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ExpandedStorageDiskItem extends ExpandedStorageDisk {
    private final ItemStorageType type;

    public ExpandedStorageDiskItem(ItemStorageType type) {
        super();
        this.type = type;
    }

    public static Item getPartById(ItemStorageType type) {
        return Registration.ITEM_STORAGE_PART.get(type).get();
    }

    @Override
    protected Item getPart() {
        return getPartById(this.type);
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        return this.type.getCapacity();
    }

    @Override
    public StorageType getType() {
        return StorageType.ITEM;
    }
}
