 package spyeedy.mods.lcu.item;
 
 import lucraft.mods.lucraftcore.extendedinventory.IItemExtendedInventory;
 import lucraft.mods.lucraftcore.extendedinventory.render.ExtendedInventoryItemRendererRegistry;
 import lucraft.mods.lucraftcore.karma.KarmaHandler;
 import lucraft.mods.lucraftcore.superpowers.SuperpowerHandler;
 import net.minecraft.block.Block;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.model.ModelBiped;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
 import net.minecraft.client.renderer.entity.RenderLivingBase;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemStack;
 import net.minecraft.util.EnumHandSide;
 import net.minecraft.world.World;
 import net.minecraftforge.fml.relauncher.Side;
 import net.minecraftforge.fml.relauncher.SideOnly;
 import spyeedy.mods.lcu.LCUConfig;
 import spyeedy.mods.lcu.LCUCorps;
 import spyeedy.mods.lcu.LCUMod;
 import spyeedy.mods.lcu.block.LCUBlocks;
 import spyeedy.mods.lcu.superpowers.LCUSuperpowers;
 
 public class ItemLanternRing extends Item implements IItemExtendedInventory {
   private final LCUCorps corp;
   
   public ItemLanternRing(LCUCorps corp) {
    setTranslationKey(corp.getName() + "_lantern_ring");
    setRegistryName(corp.getName() + "_lantern_ring");
    setCreativeTab(LCUMod.tabLCU);
     
    this.maxStackSize = 1;
    this.corp = corp;
   }
 
   
   public IItemExtendedInventory.ExtendedInventoryItemType getEIItemType(ItemStack stack) {
    return IItemExtendedInventory.ExtendedInventoryItemType.WRIST;
   }
 
   
   public void onEquipped(ItemStack itemstack, EntityPlayer player) {
    if (!itemstack.isEmpty() && !player.world.isRemote &&
      itemstack.getItem() == LCUItems.GREEN_LANTERN_RING &&
      !SuperpowerHandler.hasSuperpower(player) &&
      KarmaHandler.getKarma(player) <= LCUConfig.GREEN_LANTERN_MAX_KARMA && KarmaHandler.getKarma(player) >= LCUConfig.GREEN_LANTERN_MIN_KARMA) {
      SuperpowerHandler.giveSuperpower(player, LCUSuperpowers.getGreenLanternSuperpower());
     }
   }
 
 
 
 
   
   public boolean canEquip(ItemStack itemstack, EntityPlayer player) {
    return (!SuperpowerHandler.hasSuperpower(player) && KarmaHandler.getKarma(player) <= LCUConfig.GREEN_LANTERN_MAX_KARMA && KarmaHandler.getKarma(player) >= LCUConfig.GREEN_LANTERN_MIN_KARMA);
   }
 
   
   public int getEntityLifespan(ItemStack itemStack, World world) {
    return 0;
   }
 
   
   public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
    for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
      if (!player.inventory.getStackInSlot(slot).isEmpty() && player.inventory.getStackInSlot(slot).getItem() == Item.getItemFromBlock(LCUBlocks.GREEN_LANTERN_POWER_BATTERY)) {
        player.inventory.getStackInSlot(slot);
       }
     } 
    return super.onDroppedByPlayer(item, player);
   }
   
   public LCUCorps getCorp() {
    return this.corp;
   }
   
   @SideOnly(Side.CLIENT)
   public static class ItemRenderer
     implements ExtendedInventoryItemRendererRegistry.IItemExtendedInventoryRenderer {
     public void render(EntityPlayer player, RenderLivingBase<?> render, ItemStack stack, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, boolean isHead) {
      if (isHead) {
         return;
       }
       
      boolean flag = (player.getPrimaryHand() == EnumHandSide.RIGHT);
       
      if (!stack.isEmpty()) {
        GlStateManager.pushMatrix();
         
        if ((render.getMainModel()).isChild) {
          GlStateManager.translate(0.0F, 0.625F, 0.0F);
          GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
          GlStateManager.scale(0.5F, 0.5F, 0.5F);
         } 
         
/* 100 */         renderHeldItem(render, player, stack, flag ? ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, flag ? EnumHandSide.RIGHT : EnumHandSide.LEFT);

/* 102 */         GlStateManager.popMatrix();
       } 
     }
     
     private void renderHeldItem(RenderLivingBase<?> render, EntityLivingBase entity, ItemStack stack, ItemCameraTransforms.TransformType type, EnumHandSide handSide) {
/* 107 */       if (stack != null) {
/* 108 */         GlStateManager.pushMatrix();
         
/* 110 */         if (entity.isSneaking()) {
/* 111 */           GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }
         
/* 114 */         ((ModelBiped)render.getMainModel()).postRenderArm(0.0625F, handSide);
         
/* 116 */         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 117 */         boolean flag = (handSide == EnumHandSide.LEFT);
/* 118 */         GlStateManager.translate((float) -1 / 16.0F, 0.625, -0.03125);
/* 119 */         Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, type, flag);
/* 120 */         GlStateManager.popMatrix();
       } 
     }
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\item\ItemLanternRing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */