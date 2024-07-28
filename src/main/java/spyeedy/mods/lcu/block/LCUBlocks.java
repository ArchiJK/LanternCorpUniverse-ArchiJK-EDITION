 package spyeedy.mods.lcu.block;
 
 import net.minecraft.block.Block;
 import net.minecraft.client.renderer.block.model.ModelResourceLocation;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemBlock;
 import net.minecraftforge.client.event.ModelRegistryEvent;
 import net.minecraftforge.client.model.ModelLoader;
 import net.minecraftforge.event.RegistryEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.registries.IForgeRegistryEntry;
 
 @EventBusSubscriber
 public class LCUBlocks {
   public static BlockPowerBattery GREEN_LANTERN_POWER_BATTERY = new BlockPowerBattery("green_lantern");
   
   @SubscribeEvent
   public static void registerBlock(RegistryEvent.Register<Block> e) {
     e.getRegistry().register(GREEN_LANTERN_POWER_BATTERY);
   }
   
   @SubscribeEvent
   public static void registerItem(RegistryEvent.Register<Item> e) {
     e.getRegistry().register((new ItemBlock(GREEN_LANTERN_POWER_BATTERY)).setRegistryName(GREEN_LANTERN_POWER_BATTERY.getRegistryName()).setMaxStackSize(1));
   }
   
   @SubscribeEvent
   public static void registerModel(ModelRegistryEvent e) {
/* 30 */     ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(GREEN_LANTERN_POWER_BATTERY), 0, new ModelResourceLocation(GREEN_LANTERN_POWER_BATTERY.getRegistryName(), "inventory"));
   }
 }

