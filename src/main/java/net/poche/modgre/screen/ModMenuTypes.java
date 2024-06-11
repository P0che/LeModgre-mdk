package net.poche.modgre.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;
import net.poche.modgre.screen.coinvendor.CoinVendorMenu;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Modgre.MOD_ID);


    public static final RegistryObject<MenuType<CoinTransfertMenu>> COIN_TRANSFERT_MENU = registerMenuType("coin_transfert", CoinTransfertMenu::new);
    public static final RegistryObject<MenuType<CoinVendorMenu>> COIN_VENDOR = registerMenuType("coin_vendor", CoinVendorMenu::new);


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> aNew) {
        return MENUS.register(name,()-> IForgeMenuType.create(aNew));
    }





    public static void register(IEventBus event){
        MENUS.register(event);
    }
}
