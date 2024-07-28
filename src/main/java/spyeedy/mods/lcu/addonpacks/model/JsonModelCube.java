 package spyeedy.mods.lcu.addonpacks.model;
 
 import com.google.gson.JsonArray;
 import com.google.gson.JsonObject;
 import java.util.ArrayList;
 import net.minecraft.util.JsonUtils;
 
 
 public class JsonModelCube
 {
   public String name;
   public int[] texture_offset;
   public float[] rotation_point;
   public float[] offset;
   public int[] size;
   public float[] rotation;
   public float scale;
   public boolean mirror;
   public double[] custom_scale;
   public ArrayList<JsonModelCube> children;
   
   public void setName(String name) {
     this.name = name;
   }
   
   public void setTextureOffset(int[] textureOffset) {
/* 27 */     this.texture_offset = textureOffset;
   }
   
   public void setRotationPoint(float[] rotation_point) {
/* 31 */     this.rotation_point = rotation_point;
   }
   
   public void setOffset(float[] offset) {
/* 35 */     this.offset = offset;
   }
   
   public void setSize(int[] size) {
/* 39 */     this.size = size;
   }
   
   public void setRotation(float[] rotationAngles) {
/* 43 */     this.rotation = rotationAngles;
   }
   
   public void setScale(float scale) {
/* 47 */     this.scale = scale;
   }
   
   public void setMirror(boolean mirror) {
/* 51 */     this.mirror = mirror;
   }
   
   public void setCustomScale(double[] custom_scale) {
/* 55 */     this.custom_scale = custom_scale;
   }
   
   public void setChildren(ArrayList<JsonModelCube> children) {
/* 59 */     this.children = children;
   }
   
   public void deserialize(JsonObject object) {
     setName(JsonUtils.getString(object, "name"));
     
/* 65 */     int[] texture_offset = { JsonUtils.getJsonArray(object, "texture_offset").get(0).getAsInt(), JsonUtils.getJsonArray(object, "texture_offset").get(1).getAsInt() };
/* 66 */     setTextureOffset(texture_offset);
     
/* 68 */     float[] position = { JsonUtils.getJsonArray(object, "rotation_point").get(0).getAsFloat(), JsonUtils.getJsonArray(object, "rotation_point").get(1).getAsFloat(), JsonUtils.getJsonArray(object, "rotation_point").get(2).getAsFloat() };
/* 69 */     setRotationPoint(position);
     
/* 71 */     float[] offset = { JsonUtils.getJsonArray(object, "offset").get(0).getAsFloat(), JsonUtils.getJsonArray(object, "offset").get(1).getAsFloat(), JsonUtils.getJsonArray(object, "offset").get(2).getAsFloat() };
/* 72 */     setOffset(offset);
     
/* 74 */     int[] size = { JsonUtils.getJsonArray(object, "size").get(0).getAsInt(), JsonUtils.getJsonArray(object, "size").get(1).getAsInt(), JsonUtils.getJsonArray(object, "size").get(2).getAsInt() };
/* 75 */     setSize(size);
     
/* 77 */     if (JsonUtils.hasField(object, "rotation")) {
/* 78 */       float[] rotation = { JsonUtils.getJsonArray(object, "rotation").get(0).getAsFloat(), JsonUtils.getJsonArray(object, "rotation").get(1).getAsFloat(), JsonUtils.getJsonArray(object, "rotation").get(2).getAsFloat() };
/* 79 */       setRotation(rotation);
     } else {
/* 81 */       setRotation(null);
     } 
     
/* 84 */     setScale(JsonUtils.getFloat(object, "scale", 0.0F));
/* 85 */     setMirror(JsonUtils.getBoolean(object, "mirror", false));
     
/* 87 */     if (JsonUtils.hasField(object, "custom_scale")) {
/* 88 */       JsonArray customScaleJsonArray = JsonUtils.getJsonArray(object, "custom_scale");
/* 89 */       double[] custom_scale = { customScaleJsonArray.get(0).getAsDouble(), customScaleJsonArray.get(1).getAsDouble(), customScaleJsonArray.get(2).getAsDouble() };
/* 90 */       setCustomScale(custom_scale);
     } else {
/* 92 */       setCustomScale(new double[] { 1.0D, 1.0D, 1.0D });
     } 
   }
 }

