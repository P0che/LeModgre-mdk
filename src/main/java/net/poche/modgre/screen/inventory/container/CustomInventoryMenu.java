package net.poche.modgre.screen.inventory.container;

import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class CustomInventoryMenu  extends RecipeBookMenu<AnotherWalletContainer>{

    private final Player owner;
    public CustomInventoryMenu(Inventory pPlayerInventory, boolean pActive, final Player pOwner) {
        super(MenuType.GENERIC_9x1, 0);
        this.owner = pOwner;
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents pItemHelper) {

    }

    @Override
    public void clearCraftingContent() {

    }


    @Override
    public boolean recipeMatches(Recipe<? super AnotherWalletContainer> pRecipe) {
        return false;
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return 0;
    }

    @Override
    public int getGridHeight() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return null;
    }

    @Override
    public boolean shouldMoveToInventory(int pSlotIndex) {
        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}
