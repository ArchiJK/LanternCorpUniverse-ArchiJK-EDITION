package spyeedy.mods.lcu.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import spyeedy.mods.lcu.LCUCorps;

public interface IRingEligibility extends INBTSerializable<NBTTagCompound> {
  void acceptRing(LCUCorps paramLCUCorps);
  
  void declineRing(LCUCorps paramLCUCorps);
  
  boolean didDecline(LCUCorps paramLCUCorps);
  
  void syncToPlayer();
}


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\capability\IRingEligibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */