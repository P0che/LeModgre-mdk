package net.poche.modgre.event;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.poche.modgre.Modgre;
import net.poche.modgre.item.ModItems;
import net.poche.modgre.villager.ModVillager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Modgre.MOD_ID)
public class ModEvents{
    @SubscribeEvent
    public static void addCustoomTrades(VillagerTradesEvent event){
        if(event.getType() == VillagerProfession.ARMORER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD,2),
                    new ItemStack(ModItems.BRONZE_COIN.get(),10),
                    10,8,0.02f
            ));
        }

        if(event.getType() == ModVillager.BILLIONAIR_MASTER.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();


            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COPPER_COIN.get(),60),
                    new ItemStack(Items.DIAMOND_BLOCK,1),
                    10,8,0.02f
            ));
        }


    }
    @SubscribeEvent
    public static void addCustomWanderingTrade(WandererTradesEvent event){
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(

                new ItemStack(Items.EMERALD,12),
                new ItemStack(ModItems.BRONZE_COIN.get(),10),
                10,8,0.02f
        ));

    }
    private static final Map<UUID, Integer> playerTimeInCustomDimension = new HashMap<>();

    public static void startTimer(ServerPlayer player) {
        playerTimeInCustomDimension.put(player.getUUID(), 50); // 6000 ticks = 5 minutes
        LogUtils.getLogger().error("1000 tick started ");
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event ){

        if (event.phase == TickEvent.Phase.END) {
            if (event.player.level().isClientSide) return; // Ignorer côté client

            if (event.player instanceof ServerPlayer player) {
                UUID playerUUID = player.getUUID();
                if (playerTimeInCustomDimension.containsKey(playerUUID)) {
                    int timeLeft = playerTimeInCustomDimension.get(playerUUID) - 1;
                    if (timeLeft <= 0) {
                        teleportPlayerToOverworld(player);
                        playerTimeInCustomDimension.remove(playerUUID);
                        LogUtils.getLogger().error(String.valueOf(timeLeft));
                    } else {
                        playerTimeInCustomDimension.put(playerUUID, timeLeft);
                    }
                }
            }
        }
    }


    private static void teleportPlayerToOverworld(ServerPlayer player) {
        ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
        if (overworld != null) {
//            player.changeDimension(overworld, new I(new Vec3(0, 100, 0), Vec3.ZERO, player.getYRot(), player.getXRot()));
            player.changeDimension(overworld);
            player.level().playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

}
