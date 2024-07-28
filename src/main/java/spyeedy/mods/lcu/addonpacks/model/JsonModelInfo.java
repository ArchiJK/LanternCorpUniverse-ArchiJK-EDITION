 package spyeedy.mods.lcu.addonpacks.model;
 
 import com.google.gson.JsonArray;
 import com.google.gson.JsonObject;
 import java.util.ArrayList;
 import net.minecraft.util.JsonUtils;
 
 public class JsonModelInfo
 {
   public int textureWidth;
   public int textureHeight;
   public String color_texture;
   public String texture;
   public ArrayList<JsonModelCube> cubes;
   
   public JsonModelInfo setColorTextureName(String texturePath) {
     this.color_texture = texturePath;
     return this;
   }
   
   public JsonModelInfo setTextureName(String texturePath) {
     this.texture = texturePath;
     return this;
   }
   
   public JsonModelInfo setCubes(ArrayList<JsonModelCube> cubes) {
/* 27 */     this.cubes = cubes;
/* 28 */     return this;
   }
   
   public JsonModelInfo deserialize(JsonObject object) throws Exception {
/* 32 */     this.textureWidth = JsonUtils.getInt(object, "texture_width");
/* 33 */     this.textureHeight = JsonUtils.getInt(object, "texture_height");
     
/* 35 */     JsonObject textureObject = JsonUtils.getJsonObject(object, "textures");
/* 36 */     String color_texture_name = JsonUtils.getString(textureObject, "color_texture", "");
/* 37 */     String texture_name = JsonUtils.getString(textureObject, "texture", "");
     
/* 39 */     ArrayList<JsonModelCube> cubes = new ArrayList<>();
/* 40 */     for (int i = 0; i < JsonUtils.getJsonArray(object, "cubes").size(); i++) {
/* 41 */       JsonObject cubeObject = JsonUtils.getJsonArray(object, "cubes").get(i).getAsJsonObject();
       
/* 43 */       JsonModelCube cube = new JsonModelCube();
/* 44 */       cube.deserialize(cubeObject);
/* 45 */       deserializeChildCubes(cubeObject, cube);
       
/* 47 */       cubes.add(cube);
     } 
     
/* 50 */     setColorTextureName(color_texture_name);
/* 51 */     setTextureName(texture_name);
/* 52 */     setCubes(cubes);
     
/* 54 */     return this;
   }
   
   public void deserializeChildCubes(JsonObject parentObject, JsonModelCube parentCube) {
     ArrayList<JsonModelCube> childs = new ArrayList<>();
/* 59 */     if (parentObject.has("children")) {
/* 60 */       JsonArray arrayObject = JsonUtils.getJsonArray(parentObject, "children");
       
       for (int idx = 0; idx < arrayObject.size(); idx++) {
         JsonObject o2 = arrayObject.get(idx).getAsJsonObject();
         
/* 65 */         JsonModelCube childCube = new JsonModelCube();
/* 66 */         childCube.deserialize(o2);
/* 67 */         deserializeChildCubes(o2, childCube);
         
/* 69 */         childs.add(childCube);
       } 
     } 
/* 72 */     parentCube.setChildren(childs);
   }
 }

