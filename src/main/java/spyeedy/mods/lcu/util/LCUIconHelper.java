 package spyeedy.mods.lcu.util;
 
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import net.minecraft.util.ResourceLocation;
 
 
 public class LCUIconHelper
 {
   public static final ResourceLocation SUPERPOWER_ICON_TEX = new ResourceLocation("spylcu", "textures/gui/superpower_icons.png");
   public static final ResourceLocation ABILITY_ICON_TEX = new ResourceLocation("spylcu", "textures/gui/ability_icons.png");
   
   public static void drawSuperpowerIcon(Minecraft mc, Gui gui, int x, int y, int row, int column) {
     mc.renderEngine.bindTexture(SUPERPOWER_ICON_TEX);
     gui.drawTexturedModalRect(x, y, column * 32, row * 32, 32, 32);
   }
 
   
   public static void drawAbilityIcon(Minecraft mc, Gui gui, int x, int y, int row, int column) {
     mc.renderEngine.bindTexture(ABILITY_ICON_TEX);
     gui.drawTexturedModalRect(x, y, column * 16, row * 16, 16, 16);
   }
 }
