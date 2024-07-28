 package spyeedy.mods.lcu.abilities.data;
 
 import com.google.gson.JsonArray;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParseException;
 import java.util.ArrayList;
 import net.minecraft.util.JsonUtils;
 import spyeedy.mods.lcu.addonpacks.entity.AddonPackEntityReader;
 
 public class AbilityDataEntityIdList
   extends AbilityDataStringList
 {
   public AbilityDataEntityIdList(String key) {
     super(key);
   }
 
   
   public ArrayList<String> parseValue(JsonObject jsonObject, ArrayList<String> defaultValue) {
     if (jsonObject.has(this.jsonKey)) {
       JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, this.jsonKey);
       
       for (int idx = 0; idx < jsonArray.size(); idx++) {
         String stringName = jsonArray.get(idx).getAsString();
         if (AddonPackEntityReader.PROJECTILES.containsKey(stringName)) {
           return super.parseValue(jsonObject, defaultValue);
         }
         try {
/* 28 */           throw new JsonParseException("projectile '" + stringName + "' is not registered!");
/* 29 */         } catch (Exception e) {
/* 30 */           e.printStackTrace();
         } 
       } 
     } 
     
/* 35 */     return defaultValue;
   }
 }

