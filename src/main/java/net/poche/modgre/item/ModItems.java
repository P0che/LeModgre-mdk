package net.poche.modgre.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;
import net.poche.modgre.item.coin.CoinType;
import net.poche.modgre.item.fragment.*;

public class ModItems {

    public static  final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Modgre.MOD_ID);

    public static final RegistryObject<Item> SILVER_COIN = ITEMS.register("silver_coin",()-> new Coin(new Coin.Properties(), CoinType.ARGENT));
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin",()-> new Coin(new Coin.Properties(), CoinType.CUIVRE));
    public static final RegistryObject<Item> GOLD_COIN = ITEMS.register("gold_coin",()-> new Coin(new Coin.Properties(), CoinType.GOLD));
    public static final RegistryObject<Item> BRONZE_COIN = ITEMS.register("bronze_coin",()-> new Coin(new Coin.Properties(), CoinType.BRONZE));
    public static final RegistryObject<Item> DIAMOND_COIN = ITEMS.register("diamond_coin",()-> new Coin(new Coin.Properties(), CoinType.DIAMANT));
    public static final RegistryObject<Item> STRANGE_FRAGMENT = ITEMS.register("strange_fragment",()-> new StrangeFragment(new Item.Properties()));
    public static final RegistryObject<Item> STRANGE_TABLET_ONE = ITEMS.register("strange_tablet_one",()-> new StrangeTabletOne(new Item.Properties(),1));
    public static final RegistryObject<Item> STRANGE_TABLET_TWO = ITEMS.register("strange_tablet_two",()-> new StrangeTabletTwo(new Item.Properties(),2));
    public static final RegistryObject<Item> STRANGE_TABLET_TREE = ITEMS.register("strange_tablet_tree",()-> new StrangeTabletThree(new Item.Properties(),3 ));
    public static final RegistryObject<Item> STRANGE_TABLET_FOUR = ITEMS.register("strange_tablet_four",()-> new StrangeTabletFour(new Item.Properties(),4 ));
    public static final RegistryObject<Item> STRANGE_TABLET_FIVE = ITEMS.register("strange_tablet_five",()-> new StrangeTabletFive(new Item.Properties() ,5));
    public static final RegistryObject<Item> STRANGE_TABLET_SIX = ITEMS.register("strange_tablet_six",()-> new StrangeTabletSix(new Item.Properties(),6));
    public static final RegistryObject<Item> STRANGE_TABLET_SEVEN = ITEMS.register("strange_tablet_seven",()-> new StrangeTabletSeven(new Item.Properties(),7));
    public static final RegistryObject<Item> STRANGE_TABLET_EIGH = ITEMS.register("strange_tablet_eigh",()-> new StrangeTabletEigh(new Item.Properties(),8));
    public static final RegistryObject<Item> STRANGE_TABLET_NINE = ITEMS.register("strange_tablet_nine",()-> new StrangeTabletNine(new Item.Properties(),9));
    public static final RegistryObject<Item> STRANGE_TABLET = ITEMS.register("strange_tablet",()-> new StrangeTablet(new Item.Properties().durability(1)));
    public static final RegistryObject<Item> STRANGE_TABLET_FRAGMENT = ITEMS.register("strange_tablet_fragment",()-> new StrangeTabletFragment(new Item.Properties()));




    public static void register(IEventBus eventBus){
       ITEMS.register(eventBus);
    }
}
