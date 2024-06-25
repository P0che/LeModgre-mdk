package net.poche.modgre.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;

public class CommandItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Modgre.MOD_ID);

    private static final String ADD_CLAIM_COMMAND = "ftbchunks admin extra_claim_chunks %player% add 1";


    public static final RegistryObject<Item> ADD_CLAIM = ITEMS.register("add_claim",()-> new Command(
            new Item.Properties().durability(1),
            ADD_CLAIM_COMMAND
    ));




    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}
