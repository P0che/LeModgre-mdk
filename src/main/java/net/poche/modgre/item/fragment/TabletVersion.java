package net.poche.modgre.item.fragment;

import java.util.Random;

public enum TabletVersion {

    TABLET_ONE(1),
    TABLET_TWO(2),
    TABLET_TREE(3),
    TABLET_FOUR(4),
    TABLET_FIVE(5),
    TABLET_SIX(6),
    TABLET_SEVEN(7),
    TABLET_HIGHT(8),
    TABLET_NINE(9);
    private int type;

    TabletVersion(int type) {
        this.type =  type;
    }

    public int setType() {
        return new Random().nextInt(10)+1;
    }

    public int getType() {
        return type;
    }
}
