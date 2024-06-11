package net.poche.modgre.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.poche.modgre.Modgre;
import net.poche.modgre.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output,  ExistingFileHelper exFileHelper) {
        super(output, Modgre.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {



        simpleBlockWithItem(ModBlocks.COIN_TRANSFERT.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/coin_transfert")));
        simpleBlockWithItem(ModBlocks.COIN_VENDOR.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/coin_vendor")));
        simpleBlockWithItem(ModBlocks.TROPHEE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/trophee")));
    }


    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}
