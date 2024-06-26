package net.poche.modgre.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.poche.modgre.block.entity.CoinTransfertBlockEntity;
import net.poche.modgre.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class CoinTransfertBlock extends BaseEntityBlock {

    public static final VoxelShape SHAPE = Block.box(0,0,0,16,12,16);
    protected CoinTransfertBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    // sinon invisible
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return super.getExplosionResistance(state, level, pos, explosion);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof CoinTransfertBlockEntity){
                ((CoinTransfertBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState,pLevel,pPos,pNewState,pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
       if(!pLevel.isClientSide()){
           BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
           if(blockEntity instanceof CoinTransfertBlockEntity){

               NetworkHooks.openScreen(((ServerPlayer) pPlayer), (CoinTransfertBlockEntity)blockEntity,pPos);
           }else {
               throw new IllegalStateException("Our Container is missing");
           }
       }
       return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
       if(pLevel.isClientSide){
           return null;
       }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.COIN_TRANSFERT_BE.get(),
                (pLevel1,pPos,pState1,pBlockEntity)-> pBlockEntity.tick(pLevel1,pPos,pState1));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CoinTransfertBlockEntity(pPos,pState);
    }
}
