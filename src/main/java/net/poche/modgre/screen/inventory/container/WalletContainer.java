package net.poche.modgre.screen.inventory.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class WalletContainer implements Container{
    private final NonNullList<ItemStack> items;
    private final int width;
    private final int height;
    private final AbstractContainerMenu menu;
    public WalletContainer(AbstractContainerMenu pMenu, int pWidth, int pHeight) {
        this(pMenu, pWidth, pHeight, NonNullList.withSize(pWidth * pHeight, ItemStack.EMPTY));
    }

    public WalletContainer(AbstractContainerMenu pMenu, int pWidth, int pHeight, NonNullList<ItemStack> pItems) {
        this.items = pItems;
        this.menu = pMenu;
        this.width = pWidth;
        this.height = pHeight;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return pSlot >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
