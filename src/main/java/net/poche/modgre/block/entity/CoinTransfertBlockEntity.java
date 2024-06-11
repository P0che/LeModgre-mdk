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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.poche.modgre.item.ModItems;
import net.poche.modgre.screen.CoinTransfertMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoinTransfertBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

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
    public CoinTransfertBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COIN_TRANSFERT_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> CoinTransfertBlockEntity.this.progress;
                    case 1 -> CoinTransfertBlockEntity.this.maxProgress;

                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {

                switch (pIndex){

                    case 0 -> CoinTransfertBlockEntity.this.progress =pValue;
                    case 1 -> CoinTransfertBlockEntity.this.maxProgress =pValue;

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
        return Component.translatable("block.modgre.coin_transfert");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {

        return new CoinTransfertMenu(pContainerId,pPlayerInventory,this,this.data);
    }




    @Override
    protected void saveAdditional(CompoundTag pTag) {

        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("coin_transfert.progress",progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("coin_transfert.progress");

    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()) {
            if(!(this.itemHandler.getStackInSlot(INPUT).getItem().equals(ModItems.COPPER_COIN.get())&&
                            this.itemHandler.getStackInSlot(INPUT).getCount()!=64))
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
        Item item = this.itemHandler.getStackInSlot(INPUT).getItem();
        if(this.itemHandler.getStackInSlot(INPUT).getCount()==64&&!item.equals(ModItems.DIAMOND_COIN.get())){

            if (item.equals(ModItems.SILVER_COIN.get())) {
                exchangeStackItem(ModItems.SILVER_COIN.get(), ModItems.GOLD_COIN.get());
            } else if (item.equals(ModItems.COPPER_COIN.get())) {
                exchangeStackItem(ModItems.COPPER_COIN.get(), ModItems.BRONZE_COIN.get());
            } else if (item.equals(ModItems.BRONZE_COIN.get())) {
                exchangeStackItem(ModItems.BRONZE_COIN.get(), ModItems.SILVER_COIN.get());
            } else if (item.equals(ModItems.GOLD_COIN.get())) {
                exchangeStackItem(ModItems.GOLD_COIN.get(), ModItems.DIAMOND_COIN.get());
            }
        }else{
            if (item.equals(ModItems.GOLD_COIN.get())) {
                exchangeItem(ModItems.SILVER_COIN.get(), ModItems.GOLD_COIN.get());
            } else if (item.equals(ModItems.BRONZE_COIN.get())) {
                exchangeItem(ModItems.COPPER_COIN.get(), ModItems.BRONZE_COIN.get());
            } else if (item.equals(ModItems.SILVER_COIN.get())) {
                exchangeItem(ModItems.BRONZE_COIN.get(), ModItems.SILVER_COIN.get());
            } else if (item.equals(ModItems.DIAMOND_COIN.get())) {
                exchangeItem(ModItems.GOLD_COIN.get(), ModItems.DIAMOND_COIN.get());
            }

        }
    }

    private void exchangeItem(Item itemOutput, Item itemInput) {

        if(this.itemHandler.getStackInSlot(INPUT).getItem()== itemInput) {
            ItemStack result = new ItemStack(itemOutput,64);
            this.itemHandler.extractItem(INPUT,1,false);

            this.itemHandler.setStackInSlot(OUTPUT, new ItemStack(result.getItem(), this.itemHandler.getStackInSlot(OUTPUT).getCount() + result.getCount()));

        }
    }


    public void exchangeStackItem(Item itemInput, Item itemOutput){

        if(this.itemHandler.getStackInSlot(INPUT).getItem()== itemInput && !resultFromItem(this.itemHandler.getStackInSlot(INPUT).getItem()).equals(Items.AIR)) {
            ItemStack result = new ItemStack(itemOutput,1);
            this.itemHandler.extractItem(INPUT,64,false);

            this.itemHandler.setStackInSlot(OUTPUT, new ItemStack(result.getItem(), this.itemHandler.getStackInSlot(OUTPUT).getCount() + result.getCount()));

        }
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }



    private boolean hasRecipe() {

        boolean hasCraftingItem = craftableCoin(this.itemHandler.getStackInSlot(INPUT).getItem());
        ItemStack result = resultFromItem(this.itemHandler.getStackInSlot(INPUT).getItem());

        return hasCraftingItem && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private ItemStack resultFromItem(Item item) {
        ItemStack result = new ItemStack(Items.AIR);
        if (item.equals(ModItems.SILVER_COIN.get())) {
            result =  new ItemStack(ModItems.GOLD_COIN.get());
        } else if (item.equals(ModItems.COPPER_COIN.get())) {
            result =  new ItemStack(ModItems.BRONZE_COIN.get());
        } else if (item.equals(ModItems.BRONZE_COIN.get())) {
            result =  new ItemStack(ModItems.SILVER_COIN.get());
        } else if (item.equals(ModItems.GOLD_COIN.get())) {
            result =  new ItemStack(ModItems.DIAMOND_COIN.get());
        }
        return result;
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
    public ItemStack getItem(int pSlot)  {
        return this.itemHandler.getStackInSlot(pSlot);
    }
    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return this.itemHandler.extractItem(pSlot,pAmount,false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return this.itemHandler.extractItem(OUTPUT,1,true);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {

            this.itemHandler.setStackInSlot(INPUT,pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        this.itemHandler.setStackInSlot(OUTPUT,new ItemStack(Items.AIR));
    }
}
