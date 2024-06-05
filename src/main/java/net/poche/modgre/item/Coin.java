package net.poche.modgre.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.poche.modgre.item.coin.CoinType;

public class Coin extends Item {
    public CoinType type ;
    public static final int STACK_SIZE_COIN = 100;

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 100;
    }

    public Coin(Properties pProperties, CoinType coinType) {
        super(pProperties);
        this.type = coinType;

    }



}
