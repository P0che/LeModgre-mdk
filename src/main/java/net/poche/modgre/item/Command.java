package net.poche.modgre.item;


import com.mojang.logging.LogUtils;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

public class Command extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    public String command;

    public Command(Properties properties, String command) {
        super(properties);
        this.command = command;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41487_) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        boolean commandSuccess = false;
        if (this.command != null) {
            commandSuccess = this.performCommand(this.command, player);
        }

        ItemStack itemStack = player.getItemInHand(hand);

        if (!player.isCreative() && commandSuccess) {
            itemStack.hurtAndBreak(1,player,(p)->p.broadcastBreakEvent(player.getUsedItemHand()));
        }
        if(!commandSuccess){
            player.displayClientMessage(Component.translatable("item.modgre.command.fail"),true);
        }

        return new InteractionResultHolder<>(commandSuccess ? InteractionResult.SUCCESS : InteractionResult.FAIL, player.getItemInHand(hand));
    }

    private boolean performCommand(String command, Player player) {
        if (command == null || command.isEmpty()) {
            return false;
        }

        if (!command.startsWith("/")) {
            command = "/" + command;
        }

        if (player != null) {
            if (command.contains("%player%")) {
                command = command.replace("%player%", player.getName().getString());
            }

            MinecraftServer server = player.getCommandSenderWorld().getServer();
            if (server == null) {
                return true;
            }

            Commands commands = server.getCommands();
            int exec = commands.performPrefixedCommand(server.createCommandSourceStack(), command);
            if(exec <= 0) {
                return false;
            }

            return true;
        }

        return false;
    }
}
