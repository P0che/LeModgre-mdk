package net.poche.modgre;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.poche.modgre.block.ModBlocks;
import net.poche.modgre.block.entity.ModBlockEntities;
import net.poche.modgre.item.ModItems;
import net.poche.modgre.item.tab.ModCreativeTabs;
import net.poche.modgre.loot.ModLootModifier;
import net.poche.modgre.screen.CoinTransfertScreen;
import net.poche.modgre.screen.ModMenuTypes;
import net.poche.modgre.screen.coinvendor.CoinVendorScreen;
import net.poche.modgre.villager.ModVillager;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Modgre.MOD_ID)
public class Modgre
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "modgre";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Modgre()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModLootModifier.register(modEventBus);
        ModVillager.register(modEventBus);
//        ModPainting.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

//        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfig.SPEC, "Modgre-client.toml");
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfig.SPEC, "Modgre-common.toml");
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab



    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.COMBAT){

            event.accept(ModItems.SILVER_COIN);
            event.accept(ModItems.DIAMOND_COIN);
            event.accept(ModItems.GOLD_COIN);
            event.accept(ModItems.BRONZE_COIN);
            event.accept(ModItems.COPPER_COIN);

        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {


            MenuScreens.register(ModMenuTypes.COIN_TRANSFERT_MENU.get(), CoinTransfertScreen::new);
            MenuScreens.register(ModMenuTypes.COIN_VENDOR.get(), CoinVendorScreen::new);
        }
    }





}
