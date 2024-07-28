 package spyeedy.mods.lcu.addonpacks.entity;
 
 import com.google.gson.JsonArray;
 import com.google.gson.JsonObject;
 import net.minecraft.util.JsonUtils;
 
 public class JsonEntityInfo
 {
   private String entityName;
   private float[] collisionBox;
   private JsonRenderInfo renderInfo;
   
   public void setEntityName(String entityName) {
    this.entityName = entityName;
   }
   
   public void setCollisionBox(float[] collisionBox) {
    this.collisionBox = collisionBox;
   }
   
   public void setRenderInfo(JsonRenderInfo renderInfo) {
    this.renderInfo = renderInfo;
   }
   
   public String getEntityName() {
    return this.entityName;
   }
   
   public JsonRenderInfo getRenderInfo() {
    return this.renderInfo;
   }
   
   public float[] getCollisionBox() {
    return this.collisionBox;
   }
   
   public JsonEntityInfo deserialize(JsonObject object) {
    String name = JsonUtils.getString(object, "name");
     
    JsonArray jsonCollisionBox = JsonUtils.getJsonArray(object, "collision_box");
    float[] collisionBox = { jsonCollisionBox.get(0).getAsFloat(), jsonCollisionBox.get(1).getAsFloat() };
     
    JsonRenderInfo renderInfo = new JsonRenderInfo();
    JsonObject renderObject = JsonUtils.getJsonObject(object, "render_info");
     
    JsonArray translateArr = JsonUtils.getJsonArray(renderObject, "translate");
    JsonArray rotateArr = JsonUtils.getJsonArray(renderObject, "rotate");
     
    String modelName = JsonUtils.getString(renderObject, "model_name");
    float[] translate = { translateArr.get(0).getAsFloat(), translateArr.get(1).getAsFloat(), translateArr.get(2).getAsFloat() };
    int[] rotate = { rotateArr.get(0).getAsInt(), rotateArr.get(1).getAsInt(), rotateArr.get(2).getAsInt() };
    float opacity = JsonUtils.getFloat(renderObject, "opacity", 100.0F);
    float scale = JsonUtils.getFloat(renderObject, "scale", 0.0625F);
     
    renderInfo.setModelName(modelName);
    renderInfo.setTranslate(translate);
    renderInfo.setRotate(rotate);
    renderInfo.setOpacity(opacity);
    renderInfo.setScale(scale);
     
    setEntityName(name);
    setCollisionBox(collisionBox);
    setRenderInfo(renderInfo);
    return this;
   }
   
   public class JsonRenderInfo {
     private String modelName;
     private float[] translate;
     private int[] rotate;
     private float opacity;
     private float scale;
     
     public void setModelName(String modelName) {
      this.modelName = modelName;
     }
     
     public void setTranslate(float[] translate) {
      this.translate = translate;
     }
     
     public void setRotate(int[] rotate) {
      this.rotate = rotate;
     }
     
     public void setOpacity(float opacity) {
      this.opacity = opacity;
     }
     
     public void setScale(float scale) {
      this.scale = scale;
     }
     
     public String getModelName() {
      return this.modelName;
     }
     
     public float[] getTranslate() {
      return this.translate;
     }
     
     public int[] getRotate() {
/* 103 */       return this.rotate;
     }
     
     public float getOpacity() {
/* 107 */       return this.opacity;
     }
     
     public float getScale() {
/* 111 */       return this.scale;
     }
   }
 }

