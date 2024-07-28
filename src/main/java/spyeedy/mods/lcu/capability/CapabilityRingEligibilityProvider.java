 package spyeedy.mods.lcu.capability;
 
 import javax.annotation.Nonnull;
 import javax.annotation.Nullable;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.nbt.NBTBase;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.util.EnumFacing;
 import net.minecraftforge.common.capabilities.Capability;
 import net.minecraftforge.common.capabilities.ICapabilitySerializable;
 
 
 public class CapabilityRingEligibilityProvider
   implements ICapabilitySerializable<NBTTagCompound>
 {
   private final IRingEligibility instance;
   
   public CapabilityRingEligibilityProvider(EntityPlayer player) {
/* 19 */     this.instance = new CapabilityRingEligibility(player);
   }
 
   
   public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
/* 24 */     return (CapabilityRingEligibility.RING_ELIGIBILITY_CAP != null && capability == CapabilityRingEligibility.RING_ELIGIBILITY_CAP);
   }
 
   
   @Nullable
   public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
/* 30 */     return (capability == CapabilityRingEligibility.RING_ELIGIBILITY_CAP) ? (T)CapabilityRingEligibility.RING_ELIGIBILITY_CAP.cast(this.instance) : null;
   }
 
   
   public NBTTagCompound serializeNBT() {
/* 35 */     return this.instance.serializeNBT();
   }
 
   
   public void deserializeNBT(NBTTagCompound nbt) {
/* 40 */     this.instance.deserializeNBT(nbt);
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\capability\CapabilityRingEligibilityProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */