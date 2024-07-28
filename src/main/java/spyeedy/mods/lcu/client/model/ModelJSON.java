 package spyeedy.mods.lcu.client.model;
 
 import java.util.ArrayList;
 import javax.annotation.Nullable;
 import net.minecraft.client.model.ModelBase;
 import net.minecraft.client.model.ModelRenderer;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.util.ResourceLocation;
 import spyeedy.mods.lcu.addonpacks.model.JsonModelCube;
 import spyeedy.mods.lcu.addonpacks.model.JsonModelInfo;
 
 public class ModelJSON
   extends ModelBase
 {
   private final ArrayList<JsonModelCube> cubesList;
   private final ModelRenderer[] cubes;
   private final JsonModelInfo info;
   
   public ModelJSON(JsonModelInfo info) {
     this.textureWidth = info.textureWidth;
     this.textureHeight = info.textureHeight;
     this.info = info;
     
     this.cubesList = info.cubes;
     this.cubes = new ModelRenderer[this.cubesList.size()];
     
/* 27 */     for (JsonModelCube cube : this.cubesList) {
/* 28 */       if (cube != null) {
/* 29 */         int index = this.cubesList.indexOf(cube);
/* 30 */         this.cubes[index] = new ModelRenderer(this, cube.texture_offset[0], cube.texture_offset[1]);
/* 31 */         this.cubes[index].addBox(cube.offset[0], cube.offset[1], cube.offset[2], cube.size[0], cube.size[1], cube.size[2], cube.scale);
/* 32 */         this.cubes[index].setRotationPoint(cube.rotation_point[0], cube.rotation_point[1], cube.rotation_point[2]);
/* 33 */         (this.cubes[index]).mirror = cube.mirror;
/* 34 */         if (cube.rotation != null) {
/* 35 */           (this.cubes[index]).rotateAngleX = (float)Math.toRadians(cube.rotation[0]);
/* 36 */           (this.cubes[index]).rotateAngleY = (float)Math.toRadians(cube.rotation[1]);
/* 37 */           (this.cubes[index]).rotateAngleZ = (float)Math.toRadians(cube.rotation[2]);
         } 
/* 39 */         if (cube.children.size() > 0) {
/* 40 */           makeModelRenderer(cube, this.cubes[index]);
         }
       } 
     } 
   }
   
   public void render(float scale) {
/* 47 */     for (int cube = 0; cube < this.cubesList.size(); cube++) {
/* 48 */       ModelRenderer modelCube = this.cubes[cube];
       
/* 50 */       GlStateManager.pushMatrix();
/* 51 */       GlStateManager.translate(modelCube.offsetX, modelCube.offsetY, modelCube.offsetZ);
/* 52 */       GlStateManager.translate(modelCube.rotationPointX * scale, modelCube.rotationPointY * scale, modelCube.rotationPointZ * scale);
/* 53 */       GlStateManager.scale(this.cubesList.get(cube).custom_scale[0], this.cubesList.get(cube).custom_scale[1], this.cubesList.get(cube).custom_scale[2]);
/* 54 */       GlStateManager.translate(-modelCube.offsetX, -modelCube.offsetY, -modelCube.offsetZ);
/* 55 */       GlStateManager.translate(-modelCube.rotationPointX * scale, -modelCube.rotationPointY * scale, -modelCube.rotationPointZ * scale);
/* 56 */       modelCube.render(scale);
/* 57 */       GlStateManager.popMatrix();
     } 
   }
   
   @Nullable
   public ResourceLocation getTexture() {
     if (!this.info.texture.equalsIgnoreCase(""))
       return new ResourceLocation(this.info.texture); 
/* 65 */     return null;
   }
   
   @Nullable
   public ResourceLocation getColorTexture() {
/* 70 */     if (this.info.color_texture.equalsIgnoreCase(""))
/* 71 */       return null; 
/* 72 */     return new ResourceLocation(this.info.color_texture);
   }
   
   private void makeModelRenderer(JsonModelCube parentInfo, ModelRenderer cuboid) {
/* 76 */     if (parentInfo.children.size() > 0)
/* 77 */       for (JsonModelCube info : parentInfo.children) {
/* 78 */         ModelRenderer c = new ModelRenderer(this, info.texture_offset[0], info.texture_offset[1]);
/* 79 */         c.addBox(info.offset[0], info.offset[1], info.offset[2], info.size[0], info.size[1], info.size[2], info.scale);
/* 80 */         c.setRotationPoint(info.rotation_point[0], info.rotation_point[1], info.rotation_point[2]);
/* 81 */         c.mirror = info.mirror;
/* 82 */         if (info.rotation != null) {
/* 83 */           c.rotateAngleX = (float)(info.rotation[0] * 0.017453292519943295D);
/* 84 */           c.rotateAngleY = (float)(info.rotation[1] * 0.017453292519943295D);
/* 85 */           c.rotateAngleZ = (float)(info.rotation[2] * 0.017453292519943295D);
         } 
/* 87 */         makeModelRenderer(info, c);
/* 88 */         cuboid.addChild(c);
       }  
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\client\model\ModelJSON.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */