 package spyeedy.mods.lcu.gui;
 
 import javax.annotation.Nullable;
 import lucraft.mods.lucraftcore.util.container.ContainerDummy;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.world.World;
 import net.minecraftforge.fml.common.network.IGuiHandler;
 
 
 public class LCUGuiHandler
   implements IGuiHandler
 {
   public static final int OATH_DISPLAY = 0;
   
   @Nullable
   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
     return new ContainerDummy();
   }
 
   
   @Nullable
   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
     if (ID == 0) {
       return new GuiDisplayOath(player);
     }
     return null;
   }
 }

