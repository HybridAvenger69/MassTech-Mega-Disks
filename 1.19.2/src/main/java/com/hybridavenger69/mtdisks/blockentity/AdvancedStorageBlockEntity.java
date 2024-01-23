package com.hybridavenger69.mtdisks.blockentity;

import com.hybridavenger69.mtdisks.items.storage.item.ItemStorageType;
import com.hybridavenger69.mtdisks.nodes.AdvancedStorageNetworkNode;
import com.hybridavenger69.mtdisks.setup.Registration;
import com.hybridavenger69.mtstorage.api.storage.AccessType;
import com.hybridavenger69.mtstorage.blockentity.NetworkNodeBlockEntity;
import com.hybridavenger69.mtstorage.blockentity.config.IAccessType;
import com.hybridavenger69.mtstorage.blockentity.config.IComparable;
import com.hybridavenger69.mtstorage.blockentity.config.IPrioritizable;
import com.hybridavenger69.mtstorage.blockentity.config.IWhitelistBlacklist;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import com.hybridavenger69.mtstorage.blockentity.data.RSSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedStorageBlockEntity extends NetworkNodeBlockEntity<AdvancedStorageNetworkNode> {
    public static final BlockEntitySynchronizationParameter<Integer, AdvancedStorageBlockEntity> PRIORITY = IPrioritizable.createParameter();
    public static final BlockEntitySynchronizationParameter<Integer, AdvancedStorageBlockEntity> COMPARE = IComparable.createParameter();
    public static final BlockEntitySynchronizationParameter<Integer, AdvancedStorageBlockEntity> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter();
    public static final BlockEntitySynchronizationParameter<AccessType, AdvancedStorageBlockEntity> ACCESS_TYPE = IAccessType.createParameter();
    public static final BlockEntitySynchronizationParameter<Long, AdvancedStorageBlockEntity> STORED = new BlockEntitySynchronizationParameter<>(RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);
    public static BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
            .addWatchedParameter(REDSTONE_MODE)
            .addWatchedParameter(PRIORITY)
            .addWatchedParameter(COMPARE)
            .addWatchedParameter(WHITELIST_BLACKLIST)
            .addWatchedParameter(STORED)
            .addWatchedParameter(ACCESS_TYPE)
            .build();
    private final ItemStorageType type;

    public AdvancedStorageBlockEntity(ItemStorageType type, BlockPos pos, BlockState state) {
        super(Registration.ITEM_STORAGE_TILE.get(type).get(), pos, state, SPEC);
        this.type = type;
    }

    public ItemStorageType getItemStorageType() {
        return type;
    }

    @Override
    public AdvancedStorageNetworkNode createNode(Level level, BlockPos pos) {
        return new AdvancedStorageNetworkNode(level, pos, type);
    }
}
