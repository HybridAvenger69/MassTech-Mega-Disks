package com.hybridavenger69.mtdisks.datagen;

import com.hybridavenger69.mtdisks.MtDisks;
import com.hybridavenger69.mtdisks.items.storage.item.ItemStorageType;
import com.hybridavenger69.mtdisks.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MtDisks.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (var type : ItemStorageType.values()) {
            var model = models().cubeAll("block_" + type.getName(), modLoc("blocks/storage/" + type.getName() + "_storage_block"));
            simpleBlock(Registration.ITEM_STORAGE_BLOCK.get(type).get(), model);
        }

    }
}