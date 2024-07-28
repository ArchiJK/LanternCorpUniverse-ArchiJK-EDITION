 package spyeedy.mods.lcu.addonpacks.entity;
 
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;
 import java.io.BufferedReader;
 import java.io.InputStreamReader;
 import java.nio.charset.StandardCharsets;
 import java.util.LinkedHashMap;
 import lucraft.mods.lucraftcore.addonpacks.AddonPackReadEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import org.apache.commons.io.FilenameUtils;
 import spyeedy.mods.lcu.LCUMod;
 
 
 @EventBusSubscriber
 public class AddonPackEntityReader
 {
   public static final LinkedHashMap<String, JsonProjectileInfo> PROJECTILES = new LinkedHashMap<>();
 
   
   @SubscribeEvent
   public static void onRead(AddonPackReadEvent e) {
     if (e.getDirectory().equals("constructs") &&
       e.getResourceLocation().getPath().contains("projectiles\\") && FilenameUtils.getExtension(e.getFileName()).equalsIgnoreCase("json"))
       try {
/* 27 */         BufferedReader reader = new BufferedReader(new InputStreamReader(e.getInputStream(), StandardCharsets.UTF_8));
/* 28 */         JsonObject jsonobject = (new JsonParser()).parse(reader).getAsJsonObject();
/* 29 */         JsonProjectileInfo info = new JsonProjectileInfo();
/* 30 */         info.deserialize(jsonobject);
         
/* 32 */         String id = e.getResourceLocation().getNamespace() + ":" + info.getEntityName();
/* 33 */         if (!PROJECTILES.containsKey(id)) {
/* 34 */           PROJECTILES.put(id, info);
/* 35 */           LCUMod.LOGGER.info("Successfully loaded LCU projectile '" + id + "'");
         } else {
/* 37 */           LCUMod.LOGGER.error("There's already another projectile registered with the name '" + id + "'! Change your projectile's name!");
         } 
/* 39 */       } catch (Exception e2) {
/* 40 */         LCUMod.LOGGER.error("Wasn't able to read projectile '" + e.getFileName() + "' in addon pack '" + e.getPackFile().getName() + "': " + e2.getMessage());
       }  
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\addonpacks\entity\AddonPackEntityReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */