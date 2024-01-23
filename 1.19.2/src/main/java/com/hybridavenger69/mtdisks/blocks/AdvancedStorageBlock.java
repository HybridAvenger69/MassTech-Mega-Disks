package com.hybridavenger69.mtdisks.blocks;

import com.hybridavenger69.mtdisks.blockentity.AdvancedStorageBlockEntity;
import com.hybridavenger69.mtdisks.container.AdvancedStorageBlockContainerMenu;
import com.hybridavenger69.mtdisks.items.storage.item.ItemStorageType;
import com.hybridavenger69.mtdisks.nodes.AdvancedStorageNetworkNode;
import com.hybridavenger69.mtstorage.block.NetworkNodeBlock;
import com.hybridavenger69.mtstorage.container.factory.BlockEntityMenuProvider;
import com.hybridavenger69.mtstorage.util.BlockUtils;
import com.hybridavenger69.mtstorage.util.NetworkUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdvancedStorageBlock extends NetworkNodeBlock {
    private final ItemStorageType type;

    public AdvancedStorageBlock(ItemStorageType type) {
        super(BlockUtils.DEFAULT_ROCK_PROPERTIES);
        this.type = type;
    }

    public ItemStorageType getType() {
        return type;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        if (!level.isClientSide) {
            AdvancedStorageNetworkNode storage = ((AdvancedStorageBlockEntity) level.getBlockEntity(pos)).getNode();
            if (stack.hasTag() && stack.getTag().hasUUID(AdvancedStorageNetworkNode.NBT_ID)) {
                storage.setStorageId(stack.getTag().getUUID(AdvancedStorageNetworkNode.NBT_ID));
            }
            storage.loadStorage(entity instanceof Player player ? player : null);
        }
        // Call this after loading the storage, so the network discovery can use the loaded storage.
        super.setPlacedBy(level, pos, state, entity, stack);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AdvancedStorageBlockEntity(type, pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!level.isClientSide) {
            return NetworkUtils.attemptModify(level, pos, player, () -> NetworkHooks.openScreen(
                    (ServerPlayer) player,
                    new BlockEntityMenuProvider<AdvancedStorageBlockEntity>(
                            ((AdvancedStorageBlockEntity) level.getBlockEntity(pos)).getNode().getTitle(),
                            (tile, windowId, inventory, p) -> new AdvancedStorageBlockContainerMenu(windowId, player, tile),
                            pos
                    ), pos));
        }

        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add((Component.translatable("Do Not Use(Will Have No Recipe").withStyle(ChatFormatting.RED)));
    }
}
