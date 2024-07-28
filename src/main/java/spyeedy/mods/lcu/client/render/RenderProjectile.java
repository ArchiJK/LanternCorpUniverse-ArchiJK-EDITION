 package spyeedy.mods.lcu.client.render;
 
 import javax.annotation.Nullable;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.client.renderer.entity.Render;
 import net.minecraft.client.renderer.entity.RenderManager;
 import net.minecraft.entity.Entity;
 import net.minecraft.util.ResourceLocation;
 import spyeedy.mods.lcu.addonpacks.entity.AddonPackEntityReader;
 import spyeedy.mods.lcu.addonpacks.entity.JsonEntityInfo;
 import spyeedy.mods.lcu.addonpacks.entity.JsonProjectileInfo;
 import spyeedy.mods.lcu.addonpacks.model.AddonPackModelReader;
 import spyeedy.mods.lcu.client.model.ModelJSON;
 import spyeedy.mods.lcu.entity.EntityProjectile;
 
 public class RenderProjectile extends Render<EntityProjectile> {
   public RenderProjectile(RenderManager renderManager) {
     super(renderManager);
   }
 
   
   public void doRender(EntityProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
     super.doRender(entity, x, y, z, entityYaw, partialTicks);
     if (AddonPackEntityReader.PROJECTILES.get(entity.getJsonEntityId()) != null) {
       JsonEntityInfo.JsonRenderInfo renderInfo = AddonPackEntityReader.PROJECTILES.get(entity.getJsonEntityId()).getRenderInfo();
/* 26 */       if (renderInfo != null) {
/* 27 */         ModelJSON model = AddonPackModelReader.MODELS.get(renderInfo.getModelName());
/* 28 */         if (model != null) {
/* 29 */           GlStateManager.pushMatrix();
/* 30 */           GlStateManager.translate(x + renderInfo.getTranslate()[0], y + renderInfo.getTranslate()[1], z + renderInfo.getTranslate()[2]);
           
/* 32 */           GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
/* 33 */           GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
/* 34 */           GlStateManager.rotate(renderInfo.getRotate()[0], 1.0F, 0.0F, 0.0F);
/* 35 */           GlStateManager.rotate(renderInfo.getRotate()[1], 0.0F, 1.0F, 0.0F);
/* 36 */           GlStateManager.rotate(renderInfo.getRotate()[2], 0.0F, 0.0F, 1.0F);
           
/* 38 */           if (renderInfo.getOpacity() < 1.0D) {
/* 39 */             GlStateManager.enableBlend();
           }
/* 41 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
           
/* 43 */           if (model.getColorTexture() != null) {
/* 44 */             GlStateManager.color(entity.getColor().getRed() / 255.0F, entity.getColor().getGreen() / 255.0F, entity.getColor().getBlue() / 255.0F, renderInfo.getOpacity());
/* 45 */             bindTexture(model.getColorTexture());
/* 46 */             model.render(renderInfo.getScale());
/* 47 */             GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
           } 
           
/* 50 */           if (model.getTexture() != null) {
/* 51 */             GlStateManager.color(1.0F, 1.0F, 1.0F, renderInfo.getOpacity());
/* 52 */             bindTexture(model.getTexture());
/* 53 */             model.render(renderInfo.getScale());
/* 54 */             GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
           } 
           
/* 57 */           if (renderInfo.getOpacity() < 1.0D)
             GlStateManager.disableBlend();
/* 59 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 60 */           GlStateManager.disableBlend();
         } 
       } 
     } 
   }
 
   
   @Nullable
   protected ResourceLocation getEntityTexture(EntityProjectile entity) {
/* 69 */     return null;
   }
 }

