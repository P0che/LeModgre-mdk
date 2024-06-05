package net.poche.modgre.screen.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface AnotherWalletContainer extends Container, StackedContentsCompatible {
    int getWidth();

    int getHeight();

    List<ItemStack> getItems();
}
