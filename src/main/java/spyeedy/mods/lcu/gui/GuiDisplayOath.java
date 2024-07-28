 package spyeedy.mods.lcu.gui;
 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.util.container.ContainerDummy;
 import net.minecraft.client.gui.inventory.GuiContainer;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.inventory.Container;
 import net.minecraft.util.NonNullList;
 import spyeedy.mods.lcu.abilities.AbilityRechargeLantern;
 
 public class GuiDisplayOath extends GuiContainer {
   private final NonNullList<String> oath = NonNullList.create();
   private int counter = 0;
   private int index = 0;
   private final EntityPlayer player;
   
   public GuiDisplayOath(EntityPlayer player) {
     super(new ContainerDummy());
     
     this.player = player;
     
     for (Ability ability : Ability.getAbilities(player)) {
       if (ability instanceof AbilityRechargeLantern) {
         this.oath.addAll(((AbilityRechargeLantern)ability).getOaths());
       }
     } 
   }
 
   
   public void updateScreen() {
/* 31 */     this.counter++;
/* 32 */     if (this.counter == (this.index + 1) * 10) {
/* 33 */       sendChatMessage(this.oath.get(this.index), true);
/* 34 */       this.index++;
     } 
     
/* 37 */     if (this.index >= this.oath.size()) {
/* 38 */       this.player.closeScreen();
     }
   }
 
   
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {}
 
   
   protected void keyTyped(char typedChar, int keyCode) {}
 
   
   public boolean doesGuiPauseGame() {
/* 50 */     return false;
   }
 }

