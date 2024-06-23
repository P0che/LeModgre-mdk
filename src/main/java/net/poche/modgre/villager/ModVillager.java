package net.poche.modgre.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;
import net.poche.modgre.block.ModBlocks;

public class ModVillager {
    public static final DeferredRegister<PoiType> POI_TYPE = DeferredRegister.create(ForgeRegistries.POI_TYPES, Modgre.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Modgre.MOD_ID);


    public static final RegistryObject<PoiType> BILLIONAIR_POI = POI_TYPE.register("billionair_poi",() ->
            new PoiType(ImmutableSet.copyOf(ModBlocks.COIN_VENDOR.get().getStateDefinition().getPossibleStates()),1,1));

    public static final RegistryObject<VillagerProfession> BILLIONAIR_MASTER = VILLAGER_PROFESSION.register("billionair_master",
    ()-> new VillagerProfession("billionair_master",holder-> holder.get() == BILLIONAIR_POI.get(),
            holder -> holder.get() == BILLIONAIR_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));
    public static void register(IEventBus event){
        POI_TYPE.register(event);
        VILLAGER_PROFESSION.register(event);
    }
}
