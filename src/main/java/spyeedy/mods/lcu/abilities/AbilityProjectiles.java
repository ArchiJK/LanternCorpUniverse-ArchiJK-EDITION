 package spyeedy.mods.lcu.abilities;
 
 import java.awt.Color;
 import java.util.ArrayList;
 import lucraft.mods.lucraftcore.superpowers.abilities.AbilityAction;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataColor;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataInteger;
 import lucraft.mods.lucraftcore.superpowers.abilities.supplier.EnumSync;
 import lucraft.mods.lucraftcore.superpowers.events.AbilityKeyEvent;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import net.minecraft.client.gui.GuiScreen;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.relauncher.Side;
 import net.minecraftforge.fml.relauncher.SideOnly;
 import spyeedy.mods.lcu.abilities.data.AbilityDataEntityIdList;
 import spyeedy.mods.lcu.entity.EntityProjectile;
 import spyeedy.mods.lcu.gui.GuiProjectiles;
 import spyeedy.mods.lcu.util.LCUIconHelper;
 
 public class AbilityProjectiles extends AbilityAction {
/* 27 */   public static final AbilityData<Color> COLOR = (new AbilityDataColor("color")).disableSaving().enableSetting("color", "Color of the projectiles.");
/* 28 */   public static final AbilityData<ArrayList<String>> ENTITY_IDS = (new AbilityDataEntityIdList("entity_ids")).disableSaving().enableSetting("entity_ids", "Sets the projectiles available to access.");
/* 29 */   private static final AbilityData<Integer> SELECTED_PROJECTILE = (new AbilityDataInteger("selected_projectile")).setSyncType(EnumSync.SELF);
   
   public AbilityProjectiles(EntityLivingBase entity) {
/* 32 */     super(entity);
   }
 
   
   public void registerData() {
/* 37 */     super.registerData();
     
/* 39 */     this.dataManager.register(COLOR, Color.GREEN);
/* 40 */     ArrayList<String> entityIds = new ArrayList<>();
/* 41 */     entityIds.add("spylcu:dinosaur");
/* 42 */     entityIds.add("spylcu:plane");
/* 43 */     this.dataManager.register(ENTITY_IDS, entityIds);
/* 44 */     this.dataManager.register(SELECTED_PROJECTILE, Integer.valueOf(0));
   }
 
   
   public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
/* 49 */     Color color = getDataManager().get(COLOR);
/* 50 */     GlStateManager.pushMatrix();
 
     
/* 53 */     GlStateManager.color((color.getRed() / 255), (color.getGreen() / 255), (color.getBlue() / 255));
/* 54 */     LCUIconHelper.drawAbilityIcon(mc, gui, x, y, 1, 3);
 
     
/* 57 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
     LCUIconHelper.drawAbilityIcon(mc, gui, x, y, 1, 2);
/* 59 */     GlStateManager.popMatrix();
   }
   
   public void setSelectedProjectile(int selectedProjectile) {
     this.dataManager.set(SELECTED_PROJECTILE, Integer.valueOf(selectedProjectile));
   }
   
   public int getSelectedProjectile() {
/* 67 */     return this.dataManager.get(SELECTED_PROJECTILE).intValue();
   }
   
   public ArrayList<String> getEntityIds() {
/* 71 */     return this.dataManager.get(ENTITY_IDS);
   }
 
   
   public boolean action() {
/* 76 */     if (!getEntity().isSneaking() && 
/* 77 */       !this.entity.world.isRemote) {
/* 78 */       EntityProjectile entity = new EntityProjectile(this.entity.world, this.entity, getEntityIds().get(getSelectedProjectile()), getDataManager().get(COLOR));
/* 79 */       if (!entity.getJsonInfo().isDebugMode()) {
/* 80 */         entity.shoot(this.entity, this.entity.rotationPitch, this.entity.rotationYaw, 0.0F, entity.getJsonInfo().getSpeed(), 0.0F);
       }
/* 82 */       this.entity.world.spawnEntity(entity);
       
/* 84 */       return true;
     } 
     
/* 87 */     return false;
   }
   
   @EventBusSubscriber
   public static class EventHandler
   {
     @SubscribeEvent
     @SideOnly(Side.CLIENT)
     public static void lucraftKeyEvent(AbilityKeyEvent.Client e) {
/* 96 */       if (e.ability instanceof AbilityProjectiles && 
/* 97 */         (Minecraft.getMinecraft()).player.isSneaking())
/* 98 */         Minecraft.getMinecraft().displayGuiScreen(new GuiProjectiles((AbilityProjectiles)e.ability));
     }
   }
 }

