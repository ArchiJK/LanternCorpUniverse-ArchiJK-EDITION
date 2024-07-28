 package spyeedy.mods.lcu.superpowers;
 import java.util.List;
 import lucraft.mods.lucraftcore.extendedinventory.capabilities.CapabilityExtendedInventory;
 import lucraft.mods.lucraftcore.extendedinventory.capabilities.IExtendedInventoryCapability;
 import lucraft.mods.lucraftcore.karma.KarmaHandler;
 import lucraft.mods.lucraftcore.superpowers.Superpower;
 import lucraft.mods.lucraftcore.superpowers.SuperpowerHandler;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemStack;
 import net.minecraft.util.math.Vec3d;
 import net.minecraftforge.event.RegistryEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 import net.minecraftforge.registries.IForgeRegistryEntry;
 import spyeedy.mods.lcu.LCUConfig;
 import spyeedy.mods.lcu.LCUCorps;
 import spyeedy.mods.lcu.capability.CapabilityRingEligibility;
 import spyeedy.mods.lcu.capability.IRingEligibility;
 import spyeedy.mods.lcu.entity.EntityGreenLanternRing;
 import spyeedy.mods.lcu.item.LCUItems;
 
 @EventBusSubscriber(modid = "spylcu")
 public class LCUSuperpowers {
/* 28 */   public static Superpower GREEN_LANTERN = new SuperpowerGreenLantern();
   
   @SubscribeEvent
   public static void registerSuperpowers(RegistryEvent.Register<Superpower> e) {
/* 32 */     e.getRegistry().register(GREEN_LANTERN);
   }
   
   public static Superpower getGreenLanternSuperpower() {
/* 36 */     return GREEN_LANTERN;
   }
   
   @SubscribeEvent
   public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
/* 41 */     EntityPlayer player = e.player;
     
/* 43 */     if (player.world.isRemote)
       return; 
/* 45 */     ItemStack wristStack = player.getCapability(CapabilityExtendedInventory.EXTENDED_INVENTORY_CAP, null).getInventory().getStackInSlot(2);
/* 46 */     Superpower GL_Superpower = getGreenLanternSuperpower();
     
/* 48 */     if (SuperpowerHandler.hasSuperpower(player, GL_Superpower)) {
/* 49 */       if (wristStack.isEmpty()) {
/* 50 */         SuperpowerHandler.removeSuperpower(player);
/* 51 */       } else if (wristStack.getItem() != LCUItems.GREEN_LANTERN_RING) {
/* 52 */         SuperpowerHandler.removeSuperpower(player);
       }
/* 54 */       else if (KarmaHandler.getKarma(player) > LCUConfig.GREEN_LANTERN_MAX_KARMA || KarmaHandler.getKarma(player) < LCUConfig.GREEN_LANTERN_MIN_KARMA) {
/* 55 */         SuperpowerHandler.removeSuperpower(player);
       } 
     }
 
     
/* 60 */     if (!player.inventory.hasItemStack(new ItemStack(LCUItems.GREEN_LANTERN_RING)) && wristStack.isEmpty() && wristStack.getItem() != LCUItems.GREEN_LANTERN_RING &&
       KarmaHandler.getKarma(player) <= LCUConfig.GREEN_LANTERN_MAX_KARMA && KarmaHandler.getKarma(player) >= LCUConfig.GREEN_LANTERN_MIN_KARMA) {
       int count = 0;
       List<Entity> entities = (player.getEntityWorld()).loadedEntityList;
       for (Entity entity : entities) {
/* 65 */         if (entity instanceof EntityGreenLanternRing && (
/* 66 */           (EntityGreenLanternRing)entity).getTargetUUID().equals(player.getUniqueID())) {
/* 67 */           count++;
         }
       } 
       
/* 71 */       if (count == 0 && !player.world.isRemote && !player.getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null).didDecline(LCUCorps.GREEN)) {
/* 72 */         EntityGreenLanternRing entityRing = new EntityGreenLanternRing(player.world, player.getUniqueID());
/* 73 */         Vec3d normLook = player.getLookVec().normalize().scale(1.0D);
/* 74 */         entityRing.setPosition(player.posX + normLook.x, player.posY + 1.5D, player.posZ + normLook.z);
/* 75 */         player.world.spawnEntity(entityRing);
       } 
     } 
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\superpowers\LCUSuperpowers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */