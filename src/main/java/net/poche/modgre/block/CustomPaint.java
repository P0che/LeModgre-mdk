package net.poche.modgre.block;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.level.Level;

public class CustomPaint extends Painting {
    public CustomPaint(EntityType<? extends Painting> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
}
