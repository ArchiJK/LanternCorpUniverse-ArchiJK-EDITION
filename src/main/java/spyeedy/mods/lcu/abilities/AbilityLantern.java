 package spyeedy.mods.lcu.abilities;
 
 import java.awt.Color;
 import java.util.Map;
 import lucraft.mods.lucraftcore.LCConfig;
 import lucraft.mods.lucraftcore.superpowers.SuperpowerHandler;
 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataColor;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataInteger;
 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityCondition;
 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityConditionAbility;
import lucraft.mods.lucraftcore.superpowers.abilities.supplier.AbilityContainer;
 import lucraft.mods.lucraftcore.superpowers.abilities.supplier.EnumSync;
 import lucraft.mods.lucraftcore.superpowers.events.AbilityKeyEvent;
 import lucraft.mods.lucraftcore.util.abilitybar.AbilityBarPos;
 import lucraft.mods.lucraftcore.util.helper.LCRenderHelper;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import net.minecraft.client.gui.ScaledResolution;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.util.ResourceLocation;
 import net.minecraft.util.math.MathHelper;
 import net.minecraftforge.client.event.RenderGameOverlayEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.relauncher.Side;
 import spyeedy.mods.lcu.abilities.condition.AbilityConditionAbilityLCU;
import spyeedy.mods.lcu.abilities.condition.AbilityConditionLanternEnergy;
 import spyeedy.mods.lcu.util.LCUIconHelper;
 
 
 
 
 public class AbilityLantern
   extends AbilityToggle
 {
  public static final AbilityData<Integer> LANTERN_ENERGY = (new AbilityDataInteger("lantern_energy")).setSyncType(EnumSync.SELF);
  public static final AbilityData<Integer> MAX_LANTERN_ENERGY = (new AbilityDataInteger("max_lantern_energy")).disableSaving().setSyncType(EnumSync.SELF).enableSetting("max_lantern_energy", "The maximum amount of lantern energy you can have");
  public static final AbilityData<Color> COLOR = (new AbilityDataColor("color")).disableSaving().enableSetting("color", "Sets for HUD colors");
   
   public AbilityLantern(EntityLivingBase entity) {
    super(entity);
   }
 
   
   public void init(Map<String, Ability> abilities) {
    super.init(abilities);
   }
 
   
   public void registerData() {
    super.registerData();
     
    this.dataManager.register(LANTERN_ENERGY, Integer.valueOf(0));
    this.dataManager.register(MAX_LANTERN_ENERGY, Integer.valueOf(100));
    this.dataManager.register(COLOR, Color.GREEN);
   }
 
   
   public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
    LCUIconHelper.drawAbilityIcon(mc, gui, x, y, 0, 0);
   }
 
   
   public void updateTick() {
    AbilityContainer container = Ability.getAbilityContainer(this.context, this.entity);
    if (container == null)
       return; 
    if (isEnabled()) {
      for (Ability ab : container.getAbilities()) {
        if (!(ab instanceof AbilityRechargeLantern)) {
          for (AbilityCondition condition : ab.getConditions()) {
            if (condition instanceof AbilityConditionLanternEnergy) {
              AbilityConditionLanternEnergy conditionLanternEnergy = (AbilityConditionLanternEnergy)condition;
               
              if (conditionLanternEnergy.getAbilityKey().equals(getKey())) {
                if (conditionLanternEnergy.getRequiredEnergy() > getLanternEnergy()) {
                  if (ab.getAbilityType() == Ability.AbilityType.TOGGLE)
                    ab.setEnabled(false);
                   continue;
                 } 
                if (ab.isUnlocked() && ab.isEnabled() &&
                  this.entity.ticksExisted % conditionLanternEnergy.getFrequency() * 20 == 0) {
                  setLanternEnergy(getLanternEnergy() - conditionLanternEnergy.getRequiredEnergy());
                 }
               } 
             } 
           } 
         }
       } 
     }
   }
 
 
 
   
   public void setEnabled(boolean enabled) {
    if (!enabled) {
/* 100 */       AbilityContainer container = Ability.getAbilityContainer(this.context, this.entity);
/* 101 */       if (container != null) {
/* 102 */         for (Ability ab : container.getAbilities()) {
/* 103 */           if (!ab.getKey().equals(getKey())) {
/* 104 */             for (AbilityCondition condition : ab.getConditions()) {
/* 105 */               if ((condition instanceof AbilityConditionAbility || condition instanceof AbilityConditionAbilityLCU) &&
/* 106 */                 condition.test(this) &&
/* 107 */                 ab.isEnabled()) {
/* 108 */                 ab.action();
               }
             } 
           }
         } 
       }
     } 
 
     
/* 117 */     super.setEnabled(enabled);
   }
   
   public void setLanternEnergy(int lanternEnergy) {
/* 121 */     lanternEnergy = MathHelper.clamp(lanternEnergy, 0, getMaxLanternEnergy());
/* 122 */     this.dataManager.set(LANTERN_ENERGY, Integer.valueOf(lanternEnergy));
/* 123 */     SuperpowerHandler.syncToPlayer(this.entity);
   }
   
   public int getLanternEnergy() {
/* 127 */     return this.dataManager.get(LANTERN_ENERGY).intValue();
   }
   
   public int getMaxLanternEnergy() {
/* 131 */     return this.dataManager.get(MAX_LANTERN_ENERGY).intValue();
   }
   
   public Color getColor() {
/* 135 */     return this.dataManager.get(COLOR);
   }
   
   @EventBusSubscriber(modid = "spylcu")
   public static class EventHandler {
     @SubscribeEvent
     public static void abilityKey(AbilityKeyEvent e) {
/* 142 */       Ability ab = e.ability;
       
/* 144 */       if (!(ab instanceof AbilityRechargeLantern) && ab.getAbilityType() == Ability.AbilityType.ACTION) {
/* 145 */         for (AbilityCondition condition : ab.getConditions()) {
/* 146 */           if (condition instanceof AbilityConditionLanternEnergy) {
/* 147 */             AbilityConditionLanternEnergy conditionLanternEnergy = (AbilityConditionLanternEnergy)condition;
             
/* 149 */             AbilityContainer container = Ability.getAbilityContainer(ab.context, ab.getEntity());
/* 150 */             if (container == null)
               return; 
/* 152 */             for (Ability ab2 : container.getAbilities()) {
/* 153 */               if (ab2 instanceof AbilityLantern && conditionLanternEnergy.getAbilityKey().equals(ab2.getKey())) {
/* 154 */                 AbilityLantern abilityLantern = (AbilityLantern)ab2;
/* 155 */                 if (conditionLanternEnergy.getRequiredEnergy() <= abilityLantern.getLanternEnergy() && 
/* 156 */                   e.pressed) {
/* 157 */                   abilityLantern.setLanternEnergy(abilityLantern.getLanternEnergy() - conditionLanternEnergy.getRequiredEnergy());
                 }
               } 
             } 
           } 
         } 
       }
     }
   }
 
   
   @EventBusSubscriber(value = {Side.CLIENT}, modid = "spylcu")
   public static class Renderer
   {
/* 171 */     public static final ResourceLocation HUD_TEX = new ResourceLocation("spylcu", "textures/gui/hud.png");
     
/* 173 */     private static final Minecraft mc = Minecraft.getMinecraft();
     
     @SubscribeEvent
     public static void postRenderGameOverlay(RenderGameOverlayEvent.Post e) {
/* 177 */       if (e.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE || mc.gameSettings.showDebugInfo) {
         return;
       }
       
/* 181 */       ScaledResolution res = e.getResolution();
/* 182 */       AbilityBarPos barPos = LCConfig.abilityBar;
/* 183 */       for (Ability ability : Ability.getAbilities(mc.player)) {
/* 184 */         if (ability instanceof AbilityLantern) {
/* 185 */           GlStateManager.pushMatrix();
           
/* 187 */           int barWidth = 8;
/* 188 */           int barHeight = 107;
/* 189 */           int energyBarWidth = 2;
/* 190 */           int energyBarHeight = 99;
           
/* 192 */           int posX = 0;
/* 193 */           int posY = 0;
           
/* 195 */           AbilityLantern ab = (AbilityLantern)ability;
/* 196 */           Color color = ab.getColor();
/* 197 */           int energy = ab.getLanternEnergy();
/* 198 */           int strWidth = mc.fontRenderer.getStringWidth(energy + "%");
/* 199 */           int energyBarLength = Math.round(energy / ab.getMaxLanternEnergy() * (energyBarHeight + 1));
           
/* 201 */           if (barPos == AbilityBarPos.LEFT) {
/* 202 */             posX = res.getScaledWidth() - barWidth - 1;
           } else {
/* 204 */             posX++;
           } 
 
           
/* 208 */           int strPosX = (barPos == AbilityBarPos.LEFT) ? (res.getScaledWidth() - strWidth - 1) : (posX + 1);
/* 209 */           posY += 2;
/* 210 */           GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 211 */           LCRenderHelper.drawStringWithOutline(energy + "%", strPosX, posY, color.getRGB(), 0);
/* 212 */           GlStateManager.color(1.0F, 1.0F, 1.0F);
           
/* 214 */           posY += 9;
/* 215 */           mc.renderEngine.bindTexture(HUD_TEX);
/* 216 */           if (barPos == AbilityBarPos.LEFT) {
             
/* 218 */             Gui.drawModalRectWithCustomSizedTexture(posX, posY, 0.0F, 0.0F, barWidth, barHeight, 128.0F, 128.0F);
 
             
/* 221 */             if (energyBarLength > energyBarHeight) {
/* 222 */               GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 223 */               Gui.drawRect(posX + 4, posY + 3 + energyBarHeight, posX + 5, posY + 3 + energyBarHeight + 1, color.getRGB());
/* 224 */               GlStateManager.color(1.0F, 1.0F, 1.0F);
             } 
           } else {
             
/* 228 */             Gui.drawModalRectWithCustomSizedTexture(posX, posY, 9.0F, 0.0F, barWidth, barHeight, 128.0F, 128.0F);
 
             
/* 231 */             if (energyBarLength > energyBarHeight) {
/* 232 */               GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 233 */               Gui.drawRect(posX + 3, posY + 3 + energyBarHeight, posX + 4, posY + 3 + energyBarHeight + 1, color.getRGB());
/* 234 */               GlStateManager.color(1.0F, 1.0F, 1.0F);
             } 
           } 
 
           
/* 239 */           GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/* 240 */           int actLength = Math.min(energyBarLength, energyBarHeight);
/* 241 */           Gui.drawModalRectWithCustomSizedTexture(posX + 3, posY + 3, 18.0F, 0.0F, energyBarWidth, actLength, 128.0F, 128.0F);
/* 242 */           GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 244 */           GlStateManager.popMatrix();
         } 
       } 
     }
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\abilities\AbilityLantern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */