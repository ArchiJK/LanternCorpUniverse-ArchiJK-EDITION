 package spyeedy.mods.lcu.superpowers;
 import java.awt.Color;
import java.util.UUID;
 import lucraft.mods.lucraftcore.superpowers.Superpower;
 import lucraft.mods.lucraftcore.superpowers.abilities.*;




 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityCondition;
 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityConditionAbility;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import spyeedy.mods.lcu.abilities.AbilityAura;
 import spyeedy.mods.lcu.abilities.AbilityLantern;
 import spyeedy.mods.lcu.abilities.AbilityProjectiles;
 import spyeedy.mods.lcu.abilities.AbilityRechargeLantern;
 import spyeedy.mods.lcu.abilities.condition.AbilityConditionAbilityLCU;
import spyeedy.mods.lcu.abilities.condition.AbilityConditionLanternEnergy;
 import spyeedy.mods.lcu.util.LCUIconHelper;
 
 public class SuperpowerGreenLantern extends Superpower {
   public SuperpowerGreenLantern() {
     super("green_lantern");
     setRegistryName("green_lantern");
   }
 
   
   public void renderIcon(Minecraft mc, Gui gui, int x, int y) {
/* 30 */     LCUIconHelper.drawSuperpowerIcon(mc, gui, x, y, 0, 0);
   }
 
   
   public boolean canLevelUp() {
/* 35 */     return true;
   }
 
   
   public int getMaxLevel() {
/* 40 */     return 10;
   }
 
   
   public Ability.AbilityMap addDefaultAbilities(EntityLivingBase entity, Ability.AbilityMap abilities, Ability.EnumAbilityContext context) {
/* 45 */     abilities.put("healing", (new AbilityHealing(entity)).setDataValue(AbilityHealing.FREQUENCY, Integer.valueOf(12000)).setDataValue(AbilityHealing.AMOUNT, Float.valueOf(1.0F)));
/* 46 */     abilities.put("fall_resistance", (new AbilityFallResistance(entity)).setDataValue(AbilityAttributeModifier.UUID, UUID.fromString("7b106f47-06be-463e-b012-62cf94de513d")).setDataValue(AbilityAttributeModifier.AMOUNT, Float.valueOf(0.05F)).setDataValue(AbilityAttributeModifier.OPERATION, Integer.valueOf(1)));
     
/* 48 */     AbilityLantern abilityGreenLantern = new AbilityLantern(entity);
/* 49 */     abilities.put("green_lantern", abilityGreenLantern);
     
/* 51 */     AbilityAura abilityAura = new AbilityAura(entity);
/* 52 */     abilities.put("aura", abilityAura.addCondition(new AbilityConditionAbility(abilityGreenLantern)).addCondition(new AbilityConditionLanternEnergy(abilityGreenLantern, 5, 5)));
/* 53 */     abilities.put("flight", (new AbilityFlight(entity)).setDataValue(AbilityFlight.SPEED, Float.valueOf(1.0F)).setDataValue(AbilityFlight.SPRINT_SPEED, Float.valueOf(2.0F)).addCondition(new AbilityConditionAbility(abilityAura)));
/* 54 */     abilities.put("water_breathing", (new AbilityWaterBreathing(entity)).addCondition(new AbilityConditionAbility(abilityAura)));
/* 55 */     abilities.put("tough_lungs", (new AbilityToughLungs(entity)).addCondition(new AbilityConditionAbility(abilityAura)));
     
/* 57 */     abilities.put("beam", (new AbilityEnergyBlast(entity)).setDataValue(AbilityEnergyBlast.COLOR, Color.GREEN).addCondition(new AbilityConditionAbility(abilityGreenLantern)).addCondition(new AbilityConditionLanternEnergy(abilityGreenLantern, 2, 1)));
     abilities.put("projectiles", (new AbilityProjectiles(entity)).addCondition(new AbilityConditionAbility(abilityGreenLantern)).addCondition(new AbilityConditionLanternEnergy(abilityGreenLantern, 5, 2)));
/* 59 */     abilities.put("recharge_lantern", (new AbilityRechargeLantern(entity)).addCondition(new AbilityConditionAbilityLCU(abilityGreenLantern)));
     
     return abilities;
   }
 
   
   public boolean shouldAddToTab(EntityPlayer player) {
/* 66 */     return super.shouldAddToTab(player);
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\superpowers\SuperpowerGreenLantern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */