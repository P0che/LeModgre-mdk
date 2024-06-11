package net.poche.modgre.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModClientConfig {


    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static{
        BUILDER.push("Configre pour le Modgre ");

        //DEFINE CONFIG
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
