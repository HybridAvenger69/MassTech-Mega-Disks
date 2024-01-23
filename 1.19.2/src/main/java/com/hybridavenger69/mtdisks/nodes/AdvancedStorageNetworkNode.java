package com.hybridavenger69.mtdisks.nodes;


import com.hybridavenger69.mtdisks.MtDisks;
import com.hybridavenger69.mtdisks.blockentity.AdvancedStorageBlockEntity;
import com.hybridavenger69.mtdisks.items.storage.item.ItemStorageType;
import com.hybridavenger69.mtstorage.api.storage.IStorage;
import com.hybridavenger69.mtstorage.api.storage.disk.IStorageDisk;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.apiimpl.network.node.storage.ItemStorageWrapperStorageDisk;
import com.hybridavenger69.mtstorage.apiimpl.network.node.storage.StorageNetworkNode;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdvancedStorageNetworkNode extends StorageNetworkNode {
    public static final ResourceLocation BLOCK_102400K_ID = new ResourceLocation(MtDisks.MODID, "block_102400k");
    public static final ResourceLocation BLOCK_204800K_ID = new ResourceLocation(MtDisks.MODID, "block_204800k");
    public static final ResourceLocation BLOCK_409600K_ID = new ResourceLocation(MtDisks.MODID, "block_409600k");
    public static final ResourceLocation BLOCK_819200K_ID = new ResourceLocation(MtDisks.MODID, "block_819200k");
    public static final ResourceLocation BLOCK_2048000K_ID = new ResourceLocation(MtDisks.MODID, "block_2048000k");



    private final ItemStorageType type;
    private IStorageDisk<ItemStack> storage;

    public AdvancedStorageNetworkNode(Level level, BlockPos pos, ItemStorageType type) {
        super(level, pos, null);
        this.type = type;
    }

    @Override
    public int getEnergyUsage() {
        return 10 + (type.ordinal() * 2);
    }

    @Override
    public ResourceLocation getId() {
        return switch (type) {
            case TIER_9 -> BLOCK_102400K_ID;
            case TIER_10 -> BLOCK_204800K_ID;
            case TIER_11 -> BLOCK_409600K_ID;
            case TIER_12 -> BLOCK_819200K_ID;
            case TIER_13 -> BLOCK_2048000K_ID;


        };
    }

    @Override
    public void addItemStorages(List<IStorage<ItemStack>> storages) {
        if (storage == null) {
            loadStorage(null);
        }

        storages.add(storage);
    }

    @Override
    public void loadStorage(@Nullable Player owner) {
        IStorageDisk disk = API.instance().getStorageDiskManager((ServerLevel) level).get(getStorageId());

        if (disk == null) {
            disk = API.instance().createDefaultItemDisk((ServerLevel) level, type.getCapacity(), owner);
            API.instance().getStorageDiskManager((ServerLevel) level).set(getStorageId(), disk);
            API.instance().getStorageDiskManager((ServerLevel) level).markForSaving();
        }

        this.storage = new ItemStorageWrapperStorageDisk(this, disk);
    }

    public IStorageDisk<ItemStack> getStorage() {
        return storage;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block." + MtDisks.MODID + ".block_" + type.getName());
    }

    @Override
    public long getStored() {
        return AdvancedStorageBlockEntity.STORED.getValue();
    }

    @Override
    public long getCapacity() {
        return type.getCapacity();
    }
}
