 package spyeedy.mods.lcu.item;
 
 import net.minecraft.client.renderer.block.model.ModelResourceLocation;
 import net.minecraft.item.Item;
 import net.minecraftforge.client.event.ModelRegistryEvent;
 import net.minecraftforge.client.model.ModelLoader;
 import net.minecraftforge.event.RegistryEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.registries.IForgeRegistryEntry;
 import spyeedy.mods.lcu.LCUCorps;
 
 @EventBusSubscriber(modid = "spylcu")
 public class LCUItems
 {
   public static ItemLanternRing GREEN_LANTERN_RING = new ItemLanternRing(LCUCorps.GREEN);
   
   @SubscribeEvent
   public static void registerItems(RegistryEvent.Register<Item> e) {
     e.getRegistry().register(GREEN_LANTERN_RING);
   }
   
   @SubscribeEvent
   public static void registerModel(ModelRegistryEvent e) {
     ModelLoader.setCustomModelResourceLocation(GREEN_LANTERN_RING, 0, new ModelResourceLocation(GREEN_LANTERN_RING.getRegistryName(), "inventory"));
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\item\LCUItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */