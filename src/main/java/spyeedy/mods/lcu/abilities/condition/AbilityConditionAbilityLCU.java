 package spyeedy.mods.lcu.abilities.condition;
 
 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityConditionAbility;
 
 public class AbilityConditionAbilityLCU
   extends AbilityConditionAbility {
   private Ability ability;
   
   public AbilityConditionAbilityLCU(Ability ability) {
     super(ability);
     
     this.ability = ability;
   }
   
   public Ability getAbility() {
     return this.ability;
   }
 }

