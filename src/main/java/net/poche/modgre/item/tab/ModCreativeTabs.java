package net.poche.modgre.item.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;
import net.poche.modgre.block.ModBlocks;
import net.poche.modgre.item.CommandItems;
import net.poche.modgre.item.ModItems;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Modgre.MOD_ID);


    public static final RegistryObject<CreativeModeTab> MODGRE_TAB = CREATIVE_MODE_TAB.register("modgre_tab",
            ()-> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.GOLD_COIN.get())).title(Component.translatable("creative.tutorial_tab")).displayItems(((pParameters, pOutput) -> {

                pOutput.accept(ModItems.GOLD_COIN.get());
                pOutput.accept(ModItems.SILVER_COIN.get());
                pOutput.accept(ModItems.COPPER_COIN.get());
                pOutput.accept(ModItems.DIAMOND_COIN.get());
                pOutput.accept(ModItems.BRONZE_COIN.get());

                pOutput.accept(ModBlocks.COIN_TRANSFERT.get());
                pOutput.accept(ModBlocks.COIN_VENDOR.get());

                pOutput.accept(CommandItems.ADD_CLAIM.get());
            })).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
