 package spyeedy.mods.lcu;
 
 import java.util.Locale;
 import net.minecraft.util.IStringSerializable;
 
 public enum LCUCorps
   implements IStringSerializable {
  GREEN;
 
   
   public String getName() {
     return toString().toLowerCase(Locale.ROOT);
   }
 }

