package net.poche.modgre.config;

import it.unimi.dsi.fastutil.bytes.ByteUnaryOperator;
import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> GOLD_COIN_VALUE_TRANSFERT_FROM_DIAMOND ;


    static{
        BUILDER.push("Configre pour le Modgre ");

        //DEFINE CONFIG

        GOLD_COIN_VALUE_TRANSFERT_FROM_DIAMOND = BUILDER.comment("Combien de pièce d'or pour une pièce de diamant").define("Or par diam ",7);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
