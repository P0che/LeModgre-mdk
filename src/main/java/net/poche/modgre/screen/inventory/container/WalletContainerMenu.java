package net.poche.modgre.screen.inventory.container;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.poche.modgre.screen.ModMenuTypes;

import java.util.List;

public class WalletContainerMenu extends AbstractContainerMenu {


    public static final ResourceLocation BLOCK_ATLAS = new ResourceLocation("textures/atlas/blocks.png");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = new ResourceLocation("item/empty_armor_slot_shield");
    static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};


    /**
     * The list of items in this container.
     */
    public final NonNullList<ItemStack> items = NonNullList.create();
//    static final SimpleContainer CONTAINER = new SimpleContainer(100);
    private  WalletContainer WALLET_CONTAINER = new WalletContainer(this,6,1);
    private final AbstractContainerMenu inventoryMenu;

    public WalletContainerMenu(Inventory pPlayerInventory, boolean pActive,Player pPlayer) {
        super(MenuType.GENERIC_9x3, 0);
        this.inventoryMenu = pPlayer.inventoryMenu;
        Inventory inventory = pPlayerInventory;

//        for (int i = 0; i < 5; ++i) {
//            for (int j = 0; j < 9; ++j) {
                this.addSlot(new MoneySlot(WALLET_CONTAINER, 0, 120, 120));
//            }
//        }
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);


//        for (int k = 0; k < 9; ++k) {
//            this.addSlot(new Slot(inventory, k, 9 + k * 18, 112));
//        }

        this.scrollTo(0.0F);
    }




    /**
     * Determines whether supplied player can use this container
     */
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    protected int calculateRowCount() {
        return Mth.positiveCeilDiv(this.items.size(), 9) - 5;
    }

    protected int getRowIndexForScroll(float pScrollOffs) {
        return Math.max((int) ((double) (pScrollOffs * (float) this.calculateRowCount()) + 0.5D), 0);
    }

    protected float getScrollForRowIndex(int pRowIndex) {
        return Mth.clamp((float) pRowIndex / (float) this.calculateRowCount(), 0.0F, 1.0F);
    }

    protected float subtractInputFromScroll(float pScrollOffs, double pInput) {
        return Mth.clamp(pScrollOffs - (float) (pInput / (double) this.calculateRowCount()), 0.0F, 1.0F);
    }

    /**
     * Updates the gui slot's ItemStacks based on scroll position.
     */
    public void scrollTo(float pPos) {
        int i = this.getRowIndexForScroll(pPos);

        for (int j = 0; j < 5; ++j) {
            for (int k = 0; k < 9; ++k) {
                int l = k + (j + i) * 9;
                if (l >= 0 && l < this.items.size()) {
                    WALLET_CONTAINER.setItem(k + j * 9, this.items.get(l));
                } else {
                    WALLET_CONTAINER.setItem(k + j * 9, ItemStack.EMPTY);
                }
            }
        }

    }

    public boolean canScroll() {
        return this.items.size() > 100;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
            if (pIndex == 0) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex >= 1 && pIndex < 5) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 5 && pIndex < 9) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - equipmentslot.getIndex()).hasItem()) {
                int i = 8 - equipmentslot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot == EquipmentSlot.OFFHAND && !this.slots.get(45).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 9 && pIndex < 36) {
                if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 36 && pIndex < 45) {
                if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            if (pIndex == 0) {
                pPlayer.drop(itemstack1, false);
            }
        }

        return itemstack;

    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
     * is null for the initial slot that was double-clicked.
     */
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != WALLET_CONTAINER;
    }

    /**
     * Returns {@code true} if the player can "drag-spilt" items into this slot. Returns {@code true} by default.
     * Called to check if the slot can be added to a list of Slots to split the held ItemStack across.
     */
    public boolean canDragTo(Slot pSlot) {
        return pSlot.container != WALLET_CONTAINER;
    }

    public ItemStack getCarried() {
        return this.inventoryMenu.getCarried();
    }

    public void setCarried(ItemStack pStack) {
        this.inventoryMenu.setCarried(pStack);
    }

    private void addPlayerInventory(Inventory playerInventory) {
//        for (int i = 0; i < 3; ++i) {
//            for (int l = 0; l < 9; ++l) {
//                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
//                LogUtils.getLogger().error("slop = " +l + i * 9 + 9 );
//            }
//        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {

            this.addSlot(new Slot(playerInventory, 0, 8, 142));
            this.addSlot(new Slot(playerInventory, 1, 26, 142));


    }

    public class MoneySlot extends Slot {
        private final int slot;
        public final Container container;
        public final int x;
        public final int y;

        public MoneySlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
            this.slot = pSlot;
            this.container = pContainer;
            this.x = pX;
            this.y = pY;
            LogUtils.getLogger().error(
                    String.valueOf(this.container.getItem(slot).getDisplayName())
            );
        }

        @Override
        public void onTake(Player pPlayer, ItemStack pStack) {
            this.setChanged();
        }

        @Override
        public void set(ItemStack pStack) {
                this.container.setItem(this.slot, pStack);
                this.setChanged();

        }

        public void setChanged() {
            this.container.setChanged();
        }

        public boolean mayPlace(ItemStack itemStack) {
            return true;
        }

        @Override
        public ItemStack getItem() {
            return this.container.getItem(slot);
        }

        /**
         * Return whether this slot's stack can be taken from this slot.
         */
        public boolean mayPickup(Player pPlayer) {
            return true;
        }

    }
}

