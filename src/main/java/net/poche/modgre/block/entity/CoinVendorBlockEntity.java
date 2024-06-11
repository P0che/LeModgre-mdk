package net.poche.modgre.block.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.poche.modgre.item.ModItems;
import net.poche.modgre.item.coin.ItemException;
import net.poche.modgre.screen.coinvendor.CoinVendorMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CoinVendorBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

    private final ItemStackHandler itemHandler = new ItemStackHandler(2);
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0};
    private final int INPUT = 0;
    private final int OUTPUT = 1;




    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    public CoinVendorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COIN_VENDOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> CoinVendorBlockEntity.this.progress;
                    case 1 -> CoinVendorBlockEntity.this.maxProgress;

                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {

                switch (pIndex){

                    case 0 -> CoinVendorBlockEntity.this.progress =pValue;
                    case 1 -> CoinVendorBlockEntity.this.maxProgress =pValue;

                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap== ForgeCapabilities.ITEM_HANDLER){

            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        lazyItemHandler = LazyOptional.of(()-> itemHandler);
    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }


    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));

        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.modgre." +
                "coin_vendor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {

        return new CoinVendorMenu(pContainerId,pPlayerInventory,this,this.data);
    }




    @Override
    protected void saveAdditional(CompoundTag pTag) {

        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("coin_vendor.progress",progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("coin_vendor.progress");

    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()&&!this.itemHandler.getStackInSlot(INPUT).getItem().equals(Items.AIR)&&!craftableCoin(this.itemHandler.getStackInSlot(INPUT).getItem())) {

            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }

    }

    private void resetProgress() {
        progress = 0;

    }

    private void craftItem() {

       if(Objects.nonNull(ItemException.fromItem(itemHandler.getStackInSlot(INPUT).getItem()))) {
            ItemException itemE  =ItemException.fromItem(itemHandler.getStackInSlot(INPUT).getItem());
            exchangeItem(itemE.getCostAmount(),itemE.getAmountOutput(),itemE.getType());
        }
    }


    private void exchangeItem(int amoutOutput, int amountInput, Item itemOutput) {

            ItemStack result = new ItemStack(itemOutput,amoutOutput);
            this.itemHandler.extractItem(INPUT,amountInput,false);
            this.itemHandler.setStackInSlot(OUTPUT, new ItemStack(result.getItem(), this.itemHandler.getStackInSlot(OUTPUT).getCount() + result.getCount()));

    }




    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        if((progress+=2)>maxProgress){
            progress=maxProgress;
        }else{
            progress+=2;
        }
    }



    private boolean hasRecipe() {

       return  Objects.nonNull(ItemException.fromItem(itemHandler.getStackInSlot(INPUT).getItem()));
    }




    private boolean craftableCoin(Item proposedItem){

       return proposedItem == ModItems.BRONZE_COIN.get()||
               proposedItem == ModItems.SILVER_COIN.get()||
               proposedItem == ModItems.COPPER_COIN.get()||
               proposedItem == ModItems.GOLD_COIN.get()||
               proposedItem == ModItems.DIAMOND_COIN.get();
    }


    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT).getMaxStackSize();
    }


    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return pSide == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return this.canPlaceItem(INPUT, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {

            return this.canTakeItem(this,OUTPUT,this.itemHandler.getStackInSlot(OUTPUT));

    }

    @Override
    public int getContainerSize() {
        return 10;
    }

    @Override
    public boolean isEmpty() {
        return this.itemHandler.getStackInSlot(OUTPUT).isEmpty();

    }

    @Override
    public ItemStack getItem(int pSlot) {
        return this.itemHandler.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
       return this.itemHandler.extractItem(pSlot,pAmount,false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot)  {
       return this.itemHandler.extractItem(OUTPUT,1,true);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.itemHandler.setStackInSlot(INPUT,pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        this.itemHandler.setStackInSlot(OUTPUT,new ItemStack(Items.AIR));
    }
}
