package net.poche.modgre.item.fragment;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Fragment extends Item {

    private int version ;
    public Fragment(Properties pProperties) {
        super(pProperties);
        this.version = version;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

            pTooltipComponents.add( Component.translatable("tooltip.modgre.fragment.tooltip.shift"));

    }
}

