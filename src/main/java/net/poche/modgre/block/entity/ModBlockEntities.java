package net.poche.modgre.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;
import net.poche.modgre.block.ModBlocks;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Modgre.MOD_ID);

    public static final RegistryObject<BlockEntityType<CoinTransfertBlockEntity>> COIN_TRANSFERT_BE = BLOCK_ENTITIES.register("coin_transfert_be", ()-> BlockEntityType.Builder.of(CoinTransfertBlockEntity::new,
            ModBlocks.COIN_TRANSFERT.get()).build(null));
    public static final RegistryObject<BlockEntityType<CoinVendorBlockEntity>> COIN_VENDOR_BE = BLOCK_ENTITIES.register("coin_vendor_be", ()-> BlockEntityType.Builder.of(CoinVendorBlockEntity::new,
            ModBlocks.COIN_VENDOR.get()).build(null));




    public static void register(IEventBus event ){
        BLOCK_ENTITIES.register(event);
    }
}
