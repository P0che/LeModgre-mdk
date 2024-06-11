package net.poche.modgre.item.fragment;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StrangeTabletOne extends StrangeTablet{


   private int version;
    public StrangeTabletOne(Properties pProperties, int version) {
        super(pProperties);
        this.version = version;


    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add( Component.translatable("tooltip.modgre.tablet_item.tooltip"));

    }


    public int getVersion() {
        return version;
    }
}
