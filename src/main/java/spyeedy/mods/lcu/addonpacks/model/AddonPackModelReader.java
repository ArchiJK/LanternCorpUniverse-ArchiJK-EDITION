 package spyeedy.mods.lcu.addonpacks.model;
 
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
 import spyeedy.mods.lcu.client.model.ModelJSON;
 
 
 @EventBusSubscriber
 public class AddonPackModelReader
 {
   public static final LinkedHashMap<String, ModelJSON> MODELS = new LinkedHashMap<>();
   
   @SubscribeEvent
   public static void onRead(AddonPackReadEvent e) {
     if (e.getDirectory().equals("constructs") && e.getResourceLocation().getPath().contains("models\\") && FilenameUtils.getExtension(e.getFileName()).equalsIgnoreCase("json"))
       try {
/* 26 */         BufferedReader reader = new BufferedReader(new InputStreamReader(e.getInputStream(), StandardCharsets.UTF_8));
/* 27 */         JsonObject jsonobject = (new JsonParser()).parse(reader).getAsJsonObject();
/* 28 */         JsonModelInfo info = new JsonModelInfo();
/* 29 */         info.deserialize(jsonobject);
         
/* 31 */         String[] filenamePath = e.getResourceLocation().getPath().split("\\\\");
/* 32 */         String id = e.getResourceLocation().getNamespace() + ":" + filenamePath[filenamePath.length - 1];
/* 33 */         if (!MODELS.containsKey(id)) {
/* 34 */           MODELS.put(id, new ModelJSON(info));
/* 35 */           LCUMod.LOGGER.info("Successfully loaded LCU model '" + id + "'");
         } else {
/* 37 */           LCUMod.LOGGER.error("There's already another model with the name '" + id + "' registered! Change your model's name!");
         } 
/* 39 */       } catch (Exception e2) {
/* 40 */         LCUMod.LOGGER.error("Wasn't able to read model '" + e.getFileName() + "' in addon pack '" + e.getPackFile().getName() + "': " + e2.getMessage());
       }  
   }
 }

