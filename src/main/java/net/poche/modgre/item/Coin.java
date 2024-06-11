package net.poche.modgre.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.poche.modgre.item.coin.CoinType;

public class Coin extends Item {
    public CoinType type ;

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 64;
    }

    public Coin(Properties pProperties, CoinType coinType) {
        super(pProperties);
        this.type = coinType;

    }



}
