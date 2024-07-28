 package spyeedy.mods.lcu.proxy;
 
 import lucraft.mods.lucraftcore.extendedinventory.IItemExtendedInventory;
 import lucraft.mods.lucraftcore.extendedinventory.render.ExtendedInventoryItemRendererRegistry;
 import net.minecraftforge.fml.common.event.FMLInitializationEvent;
 import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
 import spyeedy.mods.lcu.entity.LCUEntities;
 import spyeedy.mods.lcu.item.ItemLanternRing;
 import spyeedy.mods.lcu.item.LCUItems;
 
 public class LCUClientProxy
   extends LCUCommonProxy {
   public void preInit(FMLPreInitializationEvent e) {
     LCUEntities.registerEntityModels();
   }
 
   
   public void init(FMLInitializationEvent e) {
     ExtendedInventoryItemRendererRegistry.registerRenderer(LCUItems.GREEN_LANTERN_RING, new ItemLanternRing.ItemRenderer());
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\proxy\LCUClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */