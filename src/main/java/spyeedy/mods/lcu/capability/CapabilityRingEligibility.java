 package spyeedy.mods.lcu.capability;
 
 import com.google.common.collect.Maps;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Nullable;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.entity.player.EntityPlayerMP;
 import net.minecraft.nbt.NBTBase;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.util.EnumFacing;
 import net.minecraftforge.common.capabilities.Capability;
 import net.minecraftforge.common.capabilities.CapabilityInject;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
 import spyeedy.mods.lcu.LCUCorps;
 import spyeedy.mods.lcu.LCUMod;
 import spyeedy.mods.lcu.network.client.MessageSyncRingEligibility;
 
 public class CapabilityRingEligibility
   implements IRingEligibility {
   @CapabilityInject(IRingEligibility.class)
   public static final Capability<IRingEligibility> RING_ELIGIBILITY_CAP = null;
   
   private HashMap<LCUCorps, Boolean> corpsDeclineMap;
   private final EntityPlayer player;
   
   public CapabilityRingEligibility(EntityPlayer player) {
/* 28 */     this.player = player;
/* 29 */     this.corpsDeclineMap = Maps.newHashMap();
   }
 
   
   public void acceptRing(LCUCorps corp) {
/* 34 */     this.corpsDeclineMap.put(corp, Boolean.valueOf(false));
/* 35 */     syncToPlayer();
   }
 
   
   public void declineRing(LCUCorps corp) {
/* 40 */     this.corpsDeclineMap.put(corp, Boolean.valueOf(true));
/* 41 */     syncToPlayer();
   }
 
   
   public boolean didDecline(LCUCorps corp) {
/* 46 */     return this.corpsDeclineMap.getOrDefault(corp, Boolean.valueOf(false)).booleanValue();
   }
 
   
   public void syncToPlayer() {
/* 51 */     if (this.player instanceof EntityPlayerMP) {
/* 52 */       LCUMod.networkWrapper.sendTo(new MessageSyncRingEligibility(this.player), (EntityPlayerMP)this.player);
     }
   }
 
   
   public NBTTagCompound serializeNBT() {
     NBTTagCompound nbt = new NBTTagCompound();
     
/* 60 */     for (Map.Entry<LCUCorps, Boolean> entry : this.corpsDeclineMap.entrySet()) {
       nbt.setBoolean("spylcu:" + entry.getKey().getName(), entry.getValue().booleanValue());
     }
     
     return nbt;
   }
 
   
   public void deserializeNBT(NBTTagCompound nbt) {
/* 69 */     this.corpsDeclineMap = Maps.newHashMap();
     
/* 71 */     for (LCUCorps corp : LCUCorps.values()) {
/* 72 */       if (nbt.hasKey("spylcu:" + corp.getName()))
       {
/* 74 */         this.corpsDeclineMap.put(corp, Boolean.valueOf(nbt.getBoolean("spylcu:" + corp.getName())));
       }
     } 
   }
   
   public static class Storage
     implements Capability.IStorage<IRingEligibility> {
     @Nullable
     public NBTBase writeNBT(Capability<IRingEligibility> capability, IRingEligibility instance, EnumFacing side) {
/* 83 */       return instance.serializeNBT();
     }
 
     
     public void readNBT(Capability<IRingEligibility> capability, IRingEligibility instance, EnumFacing side, NBTBase nbt) {
/* 88 */       instance.deserializeNBT((NBTTagCompound) nbt);
     }
   }
 }
