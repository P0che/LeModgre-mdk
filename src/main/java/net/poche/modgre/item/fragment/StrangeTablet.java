package net.poche.modgre.item.fragment;


import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.poche.modgre.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class StrangeTablet extends Fragment{


    public StrangeTablet(Properties pProperties) {
        super(pProperties);


    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1,pPlayer,(p)->p.broadcastBreakEvent(pPlayer.getUsedItemHand()));
        pPlayer.getInventory().add(new ItemStack(getRandomisedItem()));

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    public Item getRandomisedItem(){
        int random = new Random().nextInt(1,10);
        switch (random){
            case 1 -> {
                return ModItems.STRANGE_TABLET_ONE.get();
            }
            case 2 -> {
                return ModItems.STRANGE_TABLET_TWO.get();
            }
            case 3 -> {
                return ModItems.STRANGE_TABLET_TREE.get();
            }

            case 4 -> {
                return ModItems.STRANGE_TABLET_FOUR.get();
            }
            case 5 -> {
                return ModItems.STRANGE_TABLET_FIVE.get();
            }
            case 6 -> {
                return ModItems.STRANGE_TABLET_SIX.get();
            }

            case 7 -> {
                return ModItems.STRANGE_TABLET_SEVEN.get();
            }
            case 8 -> {
                return ModItems.STRANGE_TABLET_EIGH.get();
            }

            case 9 -> {
                return ModItems.STRANGE_TABLET_NINE.get();
            }

        }
        return ModItems.STRANGE_TABLET.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add( Component.translatable("tooltip.modgre.tablet.tooltip"));

    }
}
