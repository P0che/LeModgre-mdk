package net.poche.modgre.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;
import net.poche.modgre.item.coin.CoinType;

public class ModItems {

    public static  final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Modgre.MOD_ID);

    public static final RegistryObject<Item> SILVER_COIN = ITEMS.register("silver_coin",()-> new Coin(new Coin.Properties(), CoinType.ARGENT));
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin",()-> new Coin(new Coin.Properties(), CoinType.CUIVRE));
    public static final RegistryObject<Item> GOLD_COIN = ITEMS.register("gold_coin",()-> new Coin(new Coin.Properties(), CoinType.GOLD));
    public static final RegistryObject<Item> BRONZE_COIN = ITEMS.register("bronze_coin",()-> new Coin(new Coin.Properties(), CoinType.BRONZE));
    public static final RegistryObject<Item> DIAMOND_COIN = ITEMS.register("diamond_coin",()-> new Coin(new Coin.Properties(), CoinType.DIAMANT));


    public static void register(IEventBus eventBus){
       ITEMS.register(eventBus);
    }
}
