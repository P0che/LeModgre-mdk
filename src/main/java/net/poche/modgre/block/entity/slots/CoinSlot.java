package net.poche.modgre.block.entity.slots;

import net.minecraft.world.entity.EquipmentSlot;
import net.poche.modgre.item.Coin;

public enum CoinSlot  {

    WALLET(Type.WALLET,0,0,"Wallet"),
    COPPER_SLOT(Type.COIN,0,1,"Copper"),
    BRONZE_SLOT(Type.COIN,1,2,"Wallet"),
    SILVER_SLOT(Type.COIN,2,3,"Wallet"),
    GOLD_SLOT(Type.COIN,3,4,"Wallet"),
    DIAMOND_SLOT(Type.COIN,4,5,"Wallet");

    private final CoinSlot.Type coinType;
    private final int index;
    private final int filterTag;

    private final String name;


    private CoinSlot(CoinSlot.Type coinType, int pIndex, int pFilterFlag, String pName){
        this.coinType = coinType;
        this.index=    pIndex;
        this.filterTag = pFilterFlag;
        this.name = pName;
    }

    public CoinSlot.Type getType() {
        return this.coinType;
    }

    public static enum Type {
        WALLET,
        COIN;
    }
    public static CoinSlot byName(String pTargetName) {
        for(CoinSlot coinSlot : values()) {
            if (coinSlot.getName().equals(pTargetName)) {
                return coinSlot;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + pTargetName + "'");
    }
    public static CoinSlot byTypeAndIndex(CoinSlot.Type pSlotType, int pSlotIndex) {
        for(CoinSlot coinSlot : values()) {
            if (coinSlot.getType() == pSlotType && coinSlot.getIndex() == pSlotIndex) {
                return coinSlot;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + pSlotType + "': " + pSlotIndex);
    }
    public Type getCoinType() {
        return coinType;
    }

    public int getIndex() {
        return  index;
    }
    public int getIndex(int pBaseIndex) {
        return pBaseIndex + index;
    }

    public int getFilterTag() {
        return filterTag;
    }

    public String getName() {
        return name;
    }
}
