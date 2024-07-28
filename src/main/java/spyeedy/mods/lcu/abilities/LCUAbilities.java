 package spyeedy.mods.lcu.abilities;
 
 import com.google.gson.JsonObject;
 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.superpowers.abilities.AbilityEntry;
 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityCondition;
 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityConditionAbility;
 import net.minecraft.util.JsonUtils;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.event.RegistryEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.registries.IForgeRegistryEntry;
 import spyeedy.mods.lcu.abilities.condition.AbilityConditionAbilityLCU;
 import spyeedy.mods.lcu.abilities.condition.AbilityConditionLanternEnergy;
 
 @EventBusSubscriber(modid = "spylcu")
 public class LCUAbilities {
   @SubscribeEvent
   public static void registerAbilities(RegistryEvent.Register<AbilityEntry> e) {
     e.getRegistry().register(new AbilityEntry(AbilityAura.class, new ResourceLocation("spylcu", "aura")));
     e.getRegistry().register(new AbilityEntry(AbilityProjectiles.class, new ResourceLocation("spylcu", "projectiles")));
     
     e.getRegistry().register(new AbilityEntry(AbilityLantern.class, new ResourceLocation("spylcu", "lantern")));
     e.getRegistry().register(new AbilityEntry(AbilityRechargeLantern.class, new ResourceLocation("spylcu", "recharge_lantern")));
   }
   
   @SubscribeEvent
   public static void registerConditions(RegistryEvent.Register<AbilityCondition.ConditionEntry> e) {
/* 30 */     AbilityCondition.ConditionEntry.register(e.getRegistry(), AbilityConditionLanternEnergy.class, new ResourceLocation("spylcu", "lantern_energy"), (json, ability, abilities) -> {
           Ability ab = abilities.get(JsonUtils.getString(json, "ability"));
           int requiredEnergy = JsonUtils.getInt(json, "required_energy");
           int frequency = JsonUtils.getInt(json, "frequency");
/* 34 */           return (ab == null || !(ab instanceof AbilityLantern) || requiredEnergy < 0) ? null : new AbilityConditionLanternEnergy((AbilityLantern)ab, requiredEnergy, frequency);
         });
     
/* 37 */     AbilityCondition.ConditionEntry.register(e.getRegistry(), AbilityConditionAbility.class, new ResourceLocation("spylcu", "ability"), (j, a, l) -> {
           Ability ab = l.get(JsonUtils.getString(j, "ability"));
           return (ab != null) ? new AbilityConditionAbilityLCU(ab) : null;
         });
   }
 }

