package com.hybridavenger69.mtdisks.setup;


import com.hybridavenger69.mtdisks.MtDisks;
import com.hybridavenger69.mtdisks.blockentity.AdvancedStorageBlockEntity;
import com.hybridavenger69.mtdisks.blocks.AdvancedStorageBlock;
import com.hybridavenger69.mtdisks.container.AdvancedStorageBlockContainerMenu;
import com.hybridavenger69.mtdisks.items.AdvancedStorageBlockItem;
import com.hybridavenger69.mtdisks.items.storage.item.ExpandedStorageDiskItem;
import com.hybridavenger69.mtdisks.items.storage.item.ItemStorageType;
import com.hybridavenger69.mtdisks.loottable.StorageBlockLootFunction;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationManager;
import com.hybridavenger69.mtstorage.blockentity.data.BlockEntitySynchronizationSpec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class Registration {

    public static final Map<ItemStorageType, RegistryObject<Item>> ITEM_STORAGE_PART = new HashMap<>();

    public static final Map<ItemStorageType, RegistryObject<Item>> ITEM_DISK = new HashMap<>();

    public static final Map<ItemStorageType, RegistryObject<AdvancedStorageBlock>> ITEM_STORAGE_BLOCK = new HashMap<>();
    public static final Map<ItemStorageType, RegistryObject<Item>> ITEM_STORAGE = new HashMap<>();
    public static final Map<ItemStorageType, RegistryObject<BlockEntityType<AdvancedStorageBlockEntity>>> ITEM_STORAGE_TILE = new HashMap<>();
    public static final Map<ItemStorageType, RegistryObject<MenuType<AdvancedStorageBlockContainerMenu>>> ITEM_STORAGE_CONTAINER = new HashMap<>();
    public static final Map<String, RegistryObject<LootItemFunctionType>> REGISTERED_LOOT_ITEM_FUNCTIONS = new HashMap<>();
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MtDisks.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MtDisks.MODID);
    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MtDisks.MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MtDisks.MODID);



    private static final DeferredRegister<LootItemFunctionType> LOOT_ITEM_FUNCTIONS = DeferredRegister.create(Registry.LOOT_FUNCTION_REGISTRY, MtDisks.MODID);
    private static final Item.Properties GLOBAL_PROPERTIES = new Item.Properties().tab(ModSetup.moreStorageTab).stacksTo(64);
    public static final RegistryObject<Item> RAW_CYBERNETIC_PROCESSOR_ITEM = ITEMS.register("raw_cybernetic_processor", () -> new Item(GLOBAL_PROPERTIES));
    public static final RegistryObject<Item> CYBERNETIC_PROCESSOR_ITEM = ITEMS.register("cybernetic_processor", () -> new Item(GLOBAL_PROPERTIES));


    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TILES.register(eventBus);
        CONTAINERS.register(eventBus);
        LOOT_ITEM_FUNCTIONS.register(eventBus);

        //StoragePart
        for (ItemStorageType type : ItemStorageType.values())
            ITEM_STORAGE_PART.put(type, ITEMS.register("storagepart_" + type.getName(), () -> new Item(GLOBAL_PROPERTIES)));

        //Disk
        for (ItemStorageType type : ItemStorageType.values())
            ITEM_DISK.put(type, ITEMS.register("disk_" + type.getName(), () -> new ExpandedStorageDiskItem(type)));


        //Storage Block
        for (ItemStorageType type : ItemStorageType.values()) {
            String name = "block_" + type.getName();

            ITEM_STORAGE_BLOCK.put(type, BLOCKS.register(name, () -> new AdvancedStorageBlock(type)));
            ITEM_STORAGE.put(type, ITEMS.register(name, () -> new AdvancedStorageBlockItem(ITEM_STORAGE_BLOCK.get(type).get(), GLOBAL_PROPERTIES)));
            ITEM_STORAGE_TILE.put(type, TILES.register(name, () -> registerSynchronizationParameters(AdvancedStorageBlockEntity.SPEC, BlockEntityType.Builder.of((pos, state) -> new AdvancedStorageBlockEntity(type, pos, state), ITEM_STORAGE_BLOCK.get(type).get()).build(null))));
            ITEM_STORAGE_CONTAINER.put(type, CONTAINERS.register(name, () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity blockEntity = inv.player.getCommandSenderWorld().getBlockEntity(pos);
                if (!(blockEntity instanceof AdvancedStorageBlockEntity be)) {
                    MtDisks.LOGGER.error("Wrong type of blockentity (expected AdvancedStorageBlockEntity)!");
                    return null;
                }
                return new AdvancedStorageBlockContainerMenu(windowId, inv.player, be);
            })));
        }





        REGISTERED_LOOT_ITEM_FUNCTIONS.put("storage_block", LOOT_ITEM_FUNCTIONS.register("storage_block", () -> new LootItemFunctionType(new StorageBlockLootFunction.Serializer())));

    }

    private static <T extends BlockEntity> BlockEntityType<T> registerSynchronizationParameters(BlockEntitySynchronizationSpec spec, BlockEntityType<T> t) {
        spec.getParameters().forEach(BlockEntitySynchronizationManager::registerParameter);
        return t;
    }



}