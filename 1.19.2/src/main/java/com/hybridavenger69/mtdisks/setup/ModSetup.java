package com.hybridavenger69.mtdisks.setup;


import com.hybridavenger69.mtdisks.MtDisks;
import com.hybridavenger69.mtdisks.items.storage.item.ItemStorageType;
import com.hybridavenger69.mtdisks.nodes.AdvancedStorageNetworkNode;
import com.hybridavenger69.mtstorage.api.network.node.INetworkNode;
import com.hybridavenger69.mtstorage.apiimpl.API;
import com.hybridavenger69.mtstorage.apiimpl.network.node.NetworkNode;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = MtDisks.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final CreativeModeTab moreStorageTab = new CreativeModeTab(MtDisks.MODID + "_tab") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.ITEM_DISK.get(ItemStorageType.TIER_9).get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {

        for (ItemStorageType type : ItemStorageType.values()) {
            API.instance().getNetworkNodeRegistry().add(new ResourceLocation(MtDisks.MODID, "block_" + type.getName()), (tag, world, pos) -> readAndReturn(tag, new AdvancedStorageNetworkNode(world, pos, type)));
            Registration.ITEM_STORAGE_TILE.get(type).get().create(BlockPos.ZERO, null).getDataManager().getParameters().forEach(BlockEntitySynchronizationManager::registerParameter);
        }






        if(ModList.get().isLoaded("inventorysorter")) {

            Registration.ITEM_STORAGE_CONTAINER.values().forEach(v -> InterModComms.sendTo("inventorysorter", "containerblacklist", v::getId));

        }
    }

    private static INetworkNode readAndReturn(CompoundTag tag, NetworkNode node) {
        node.read(tag);
        return node;
    }
}
