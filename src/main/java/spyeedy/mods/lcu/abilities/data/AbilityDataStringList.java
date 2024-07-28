 package spyeedy.mods.lcu.abilities.data;
 
 import com.google.gson.JsonArray;
 import com.google.gson.JsonObject;
 import java.util.ArrayList;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
 import net.minecraft.nbt.NBTBase;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.nbt.NBTTagList;
 import net.minecraft.nbt.NBTTagString;
 import net.minecraft.util.JsonUtils;
 
 public class AbilityDataStringList
   extends AbilityData<ArrayList<String>>
 {
   public AbilityDataStringList(String key) {
     super(key);
   }
 
   
   public ArrayList<String> parseValue(JsonObject jsonObject, ArrayList<String> defaultValue) {
     if (jsonObject.has(this.jsonKey)) {
       JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, this.jsonKey);
       ArrayList<String> stringList = new ArrayList<>();
       
/* 26 */       for (int idx = 0; idx < jsonArray.size(); idx++) {
/* 27 */         String stringName = jsonArray.get(idx).getAsString();
/* 28 */         stringList.add(stringName);
       } 
/* 30 */       return stringList;
     } 
/* 32 */     return defaultValue;
   }
 
   
   public void writeToNBT(NBTTagCompound nbt, ArrayList<String> value) {
/* 37 */     NBTTagList stringsTagList = new NBTTagList();
     
/* 39 */     value.forEach(entityId -> stringsTagList.appendTag(new NBTTagString(entityId)));
     
/* 41 */     nbt.setTag(this.key, stringsTagList);
   }
 
   
   public ArrayList<String> readFromNBT(NBTTagCompound nbt, ArrayList<String> defaultValue) {
/* 46 */     if (nbt.hasKey(this.key)) {
/* 47 */       NBTTagList stringsTagList = nbt.getTagList(this.key, 8);
/* 48 */       ArrayList<String> stringList = new ArrayList<>();
       
/* 50 */       for (int idx = 0; idx < stringsTagList.tagCount(); idx++) {
/* 51 */         String stringName = stringsTagList.getStringTagAt(idx);
/* 52 */         stringList.add(stringName);
       } 
/* 54 */       return stringList;
     } 
/* 56 */     return defaultValue;
   }
 
   
   public String getDisplay(ArrayList<String> value) {
     String jsonString = "";
     for (int idx = 0; idx < value.size(); idx++) {
       if (idx == 0) {
         jsonString = "\"" + value.get(idx) + "\"";
       } else {
/* 66 */         jsonString = jsonString + ", \"" + value.get(idx) + "\"";
       } 
     } 
/* 69 */     return "[ " + jsonString + " ]";
   }
 }

