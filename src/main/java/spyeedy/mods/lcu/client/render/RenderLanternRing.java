 package spyeedy.mods.lcu.client.render;
 
 import javax.annotation.Nullable;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.client.renderer.RenderItem;
 import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
 import net.minecraft.client.renderer.entity.Render;
 import net.minecraft.client.renderer.entity.RenderManager;
 import net.minecraft.client.renderer.texture.TextureMap;
 import net.minecraft.entity.Entity;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemStack;
 import net.minecraft.util.ResourceLocation;
 import spyeedy.mods.lcu.entity.EntityRing;
 import spyeedy.mods.lcu.item.ItemLanternRing;
 
 public class RenderLanternRing extends Render<EntityRing> {
   private final RenderItem itemRenderer;
   private final ItemLanternRing ring;
   
   public RenderLanternRing(RenderManager manager, ItemLanternRing ring) {
     super(manager);
     this.itemRenderer = Minecraft.getMinecraft().getRenderItem();
     this.ring = ring;
   }
 
   
   public void doRender(EntityRing entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 30 */     GlStateManager.pushMatrix();
/* 31 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 32 */     GlStateManager.enableRescaleNormal();
/* 33 */     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 34 */     GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 35 */     bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
     
/* 37 */     if (this.renderOutlines) {
       
/* 39 */       GlStateManager.enableColorMaterial();
/* 40 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
     } 
     
/* 43 */     this.itemRenderer.renderItem(new ItemStack(this.ring), ItemCameraTransforms.TransformType.GROUND);
     
/* 45 */     if (this.renderOutlines) {
       
/* 47 */       GlStateManager.disableOutlineMode();
/* 48 */       GlStateManager.disableColorMaterial();
     } 
     
/* 51 */     GlStateManager.disableRescaleNormal();
/* 52 */     GlStateManager.popMatrix();
/* 53 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }
 
   
   @Nullable
   protected ResourceLocation getEntityTexture(EntityRing entity) {
/* 59 */     return null;
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\client\render\RenderLanternRing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */