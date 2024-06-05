package net.poche.modgre.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.CreativeInventoryListener;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.poche.modgre.Modgre;
import net.poche.modgre.screen.inventory.container.WalletContainerMenu;

import java.util.List;

import static net.minecraft.client.gui.screens.inventory.InventoryScreen.renderEntityInInventoryFollowsMouse;

public class CustomInventoryScreen extends EffectRenderingInventoryScreen<WalletContainerMenu> {
    private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation(Modgre.MOD_ID,"textures/gui/custom_inventory_no_wallet.png");
//    private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
    private float xMouse;
    private boolean buttonClicked;
    private boolean widthTooNarrow;
    /** The old y position of the mouse pointer */
    private float yMouse;

    public CustomInventoryScreen(Player pPlayer) {
        super(new WalletContainerMenu(pPlayer.getInventory(),true,pPlayer), pPlayer.getInventory(), Component.translatable("container.inventory"));
        this.titleLabelX = 97;
    }


    @Override
    protected void init() {


        if (this.minecraft.gameMode.hasInfiniteItems()) {
            this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), this.minecraft.options.operatorItemsTab().get()));
        } else {
            super.init();
            this.widthTooNarrow = this.width < 379;


            this.addRenderableWidget(new ImageButton(this.leftPos + 104, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, (p_289631_) -> {


                p_289631_.setPosition(this.leftPos + 104, this.height / 2 - 22);
                this.buttonClicked = true;
            }));
//            this.addWidget(this.recipeBookComponent);
//            this.setInitialFocus(this.recipeBookComponent);
        }
    }


    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;

        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, 200);
        renderEntityInInventoryFollowsMouse(guiGraphics, i + 51, j + 75, 30, (float)(i + 51) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.minecraft.player);

    }



    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(guiGraphics);
        renderTooltip(guiGraphics, pMouseX, pMouseY);

        if (this.widthTooNarrow) {
            this.renderBg(guiGraphics, pPartialTick, pMouseX, pMouseY);

        } else {
            super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        }

        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        this.xMouse = (float)pMouseX;
        this.yMouse = (float)pMouseY;
    }



    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        return (!this.widthTooNarrow ) && super.isHovering(pX, pY, pWidth, pHeight, pMouseX, pMouseY);
    }
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {

            return super.mouseClicked(pMouseX, pMouseY, pButton);

    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {

            return super.mouseReleased(pMouseX, pMouseY, pButton);

    }

    protected boolean hasClickedOutside(double pMouseX, double pMouseY, int pGuiLeft, int pGuiTop, int pMouseButton) {
        boolean flag = pMouseX < (double)pGuiLeft || pMouseY < (double)pGuiTop || pMouseX >= (double)(pGuiLeft + this.imageWidth) || pMouseY >= (double)(pGuiTop + this.imageHeight);
        return  flag;
    }

    /**
     * Called when the mouse is clicked over a slot or outside the gui.
     */
    protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        super.slotClicked(pSlot, pSlotId, pMouseButton, pType);

        //LogUtils.getLogger().error("clicked" +  pSlot.getItem().getDisplayName());
    }



}
