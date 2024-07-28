 package spyeedy.mods.lcu.gui;
 
 import java.awt.Color;
 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.util.helper.StringHelper;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.Tessellator;
 import net.minecraft.init.SoundEvents;
 import net.minecraftforge.fml.client.GuiScrollingList;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
 import spyeedy.mods.lcu.LCUMod;
 import spyeedy.mods.lcu.abilities.AbilityProjectiles;
 import spyeedy.mods.lcu.addonpacks.entity.AddonPackEntityReader;
 import spyeedy.mods.lcu.addonpacks.entity.JsonProjectileInfo;
 import spyeedy.mods.lcu.network.server.MessageSendAbilityProjectileInfo;
 
 public class GuiProjectilesList extends GuiScrollingList {
   private final GuiProjectiles parent;
   
   public GuiProjectilesList(Minecraft client, GuiProjectiles parent) {
     super(client, 150, 80, (parent.height - parent.getYSize()) / 2 + 28, (parent.height - parent.getYSize()) / 2 + 28 + 80, (parent.width - parent.getXSize()) / 2 + 21, 16, parent.width, parent.height);
     
     this.parent = parent;
   }
 
   
   protected int getSize() {
/* 28 */     return this.parent.entityIdList.size();
   }
 
   
   protected void elementClicked(int index, boolean doubleClick) {
/* 33 */     this.parent.selectedEntityId = index;
/* 34 */     this.parent.mc.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0F, 1.0F);
     
/* 36 */     AbilityProjectiles ability = this.parent.ability;
 
     
/* 39 */     if (doubleClick) {
/* 40 */       ability.setSelectedProjectile(index);
/* 41 */       LCUMod.networkWrapper.sendToServer(new MessageSendAbilityProjectileInfo(this.parent.ability, MessageSendAbilityProjectileInfo.InfoType.SELECTED_PROJECTILE, index));
     } 
   }
 
   
   protected boolean isSelected(int index) {
/* 47 */     return (this.parent.selectedEntityId == index);
   }
 
   
   protected void drawBackground() {}
 
   
   protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
/* 55 */     String entityId = this.parent.ability.getEntityIds().get(slotIdx);
/* 56 */     String modid = entityId.split(":")[0];
/* 57 */     JsonProjectileInfo info = AddonPackEntityReader.PROJECTILES.get(entityId);
     
/* 59 */     int slotLeft = this.left + 1;
     
     String name = StringHelper.translateToLocal(modid + ".entity." + info.getEntityName() + ".name");
     this.parent.mc.fontRenderer.drawString(name, slotLeft + 4, slotTop + 2, Color.WHITE.getRGB());
     
     if (slotIdx == this.parent.ability.getSelectedProjectile()) {
/* 65 */       this.parent.mc.getTextureManager().bindTexture(GuiProjectiles.TEX);
/* 66 */       this.parent.drawTexturedModalRect(entryRight - 14 - 2, slotTop, 10, 136, 14, 14);
     } 
   }
 }

