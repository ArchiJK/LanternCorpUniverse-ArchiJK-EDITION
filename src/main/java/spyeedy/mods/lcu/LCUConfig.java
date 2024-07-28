 package spyeedy.mods.lcu;
 import net.minecraftforge.common.config.Config;
 import net.minecraftforge.common.config.Config.RangeInt;
 import net.minecraftforge.common.config.Config.SlidingOption;
 import net.minecraftforge.common.config.ConfigManager;
 import net.minecraftforge.fml.client.event.ConfigChangedEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 
 @Config(modid = "spylcu")
 public class LCUConfig {
   @SlidingOption
   @RangeInt(min = -600, max = 600)
   public static int GREEN_LANTERN_MIN_KARMA = 201;
   
   @SlidingOption
   @RangeInt(min = -600, max = 600)
   public static int GREEN_LANTERN_MAX_KARMA = 500;
   
   @EventBusSubscriber(modid = "spylcu")
   private static class EventHandler
   {
     @SubscribeEvent
     public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
       if (event.getModID().equals("spylcu"))
/* 26 */         ConfigManager.sync("spylcu", Config.Type.INSTANCE); 
     }
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\LCUConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */