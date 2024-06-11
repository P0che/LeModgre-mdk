package net.poche.modgre.painting;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;

public class ModPainting {

    public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Modgre.MOD_ID);

    public static final RegistryObject<PaintingVariant> BRUNOGRE_PANNEL = PAINTINGS.register("brunogre_painting",
            () -> new PaintingVariant(64, 64));

    public static void register(IEventBus eventBus){
        PAINTINGS.register(eventBus);
    }

}
