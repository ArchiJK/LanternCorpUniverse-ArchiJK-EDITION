 package spyeedy.mods.lcu.abilities.condition;
 
 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityCondition;
 import net.minecraft.util.text.ITextComponent;
 import net.minecraft.util.text.TextComponentTranslation;
 import spyeedy.mods.lcu.abilities.AbilityLantern;

 public class AbilityConditionLanternEnergy extends AbilityCondition {
     private int requiredEnergy;
     private int frequency;
     private String abilityKey;

     public AbilityConditionLanternEnergy(AbilityLantern lantern, int requiredEnergy, int frequency) {
         super((ability) -> lantern.getLanternEnergy() >= requiredEnergy, new TextComponentTranslation("spylcu.ability.condition.lantern_energy", requiredEnergy));
         this.requiredEnergy = requiredEnergy;
         this.frequency = frequency;
         this.abilityKey = lantern.getKey();
     }

     public int getRequiredEnergy() {
         return this.requiredEnergy;
     }

     public int getFrequency() {
         return this.frequency;
     }

     public String getAbilityKey() {
         return this.abilityKey;
     }
 }
