 package spyeedy.mods.lcu.capability;
 
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.nbt.NBTBase;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.util.EnumFacing;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.common.capabilities.ICapabilityProvider;
 import net.minecraftforge.event.AttachCapabilitiesEvent;
 import net.minecraftforge.event.entity.EntityJoinWorldEvent;
 import net.minecraftforge.event.entity.player.PlayerEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 
 @EventBusSubscriber(modid = "spylcu")
 public class LCUCapabilities {
   @SubscribeEvent
   public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> evt) {
     if (evt.getObject() instanceof EntityPlayer && !evt.getObject().hasCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null)) {
       evt.addCapability(new ResourceLocation("spylcu", "ring_eligibility"), new CapabilityRingEligibilityProvider((EntityPlayer)evt.getObject()));
     }
   }
   
   @SubscribeEvent
   public static void onPlayerClone(PlayerEvent.Clone e) {
/* 27 */     NBTTagCompound compound = (NBTTagCompound)CapabilityRingEligibility.RING_ELIGIBILITY_CAP.getStorage().writeNBT(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, e.getOriginal().getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null), null);
/* 28 */     CapabilityRingEligibility.RING_ELIGIBILITY_CAP.getStorage().readNBT(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, e.getEntityPlayer().getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null), null, compound);
   }
   
   @SubscribeEvent
   public static void onPlayerLoggedIn(EntityJoinWorldEvent e) {
/* 33 */     if (e.getEntity() instanceof EntityPlayer) {
/* 34 */       EntityPlayer player = (EntityPlayer)e.getEntity();
/* 35 */       player.getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null).syncToPlayer();
     } 
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\capability\LCUCapabilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */