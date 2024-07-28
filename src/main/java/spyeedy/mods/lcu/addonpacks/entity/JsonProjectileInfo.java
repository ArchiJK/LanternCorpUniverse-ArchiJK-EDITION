 package spyeedy.mods.lcu.addonpacks.entity;
 
 import com.google.gson.JsonObject;
 import net.minecraft.util.IStringSerializable;
 import net.minecraft.util.JsonUtils;
 
 public class JsonProjectileInfo
   extends JsonEntityInfo {
   private float speed;
   private float damage;
   private EffectTypes effectType;
   private Effect effect;
   private boolean debugMode;
   
   public void setSpeed(float speed) {
    this.speed = speed;
   }
   
   public void setDamage(float damage) {
    this.damage = damage;
   }
   
   public void setEffectType(EffectTypes type) {
    this.effectType = type;
   }
   
   public void setEffect(Effect effect) {
    this.effect = effect;
   }
   
   public void setDebugMode(boolean debugMode) {
    this.debugMode = debugMode;
   }
   
   public float getSpeed() {
    return this.speed;
   }
   
   public float getDamage() {
    return this.damage;
   }
   
   public Effect getEffect() {
    return this.effect;
   }
   
   public EffectTypes getEffectType() {
    return this.effectType;
   }
   
   public boolean isDebugMode() {
    return this.debugMode;
   }
 
   
   public JsonProjectileInfo deserialize(JsonObject object) {
    super.deserialize(object);
     
    setSpeed(JsonUtils.getFloat(object, "speed", 1.5F));
     
    float damage = JsonUtils.getFloat(object, "damage");
    setDamage(damage);
     
    if (JsonUtils.hasField(object, "effect")) {
      float strength; JsonObject effectObject = JsonUtils.getJsonObject(object, "effect");
      EffectTypes type = EffectTypes.byName(JsonUtils.getString(effectObject, "type"));
      switch (type) {
         case EXPLODE:
          strength = JsonUtils.getFloat(effectObject, "strength");
           
          setEffectType(EffectTypes.EXPLODE);
          setEffect(new EffectExplode(strength));
           break;
         case PUSHBACK:
          setEffectType(EffectTypes.PUSHBACK);
          setEffect(null);
           break;
         case NONE:
          setEffectType(EffectTypes.NONE);
          setEffect(null);
           break;
       } 
     } else {
      setEffectType(EffectTypes.NONE);
      setEffect(null);
     } 
     
    setDebugMode(JsonUtils.getBoolean(object, "is_debug_mode_on", false));
     
    return this;
   }
   
   public enum EffectTypes implements IStringSerializable {
    EXPLODE, PUSHBACK, NONE;
 
     
     public String getName() {
      return toString().toLowerCase();
     }
     
     public static EffectTypes byName(String name) {
/* 102 */       for (EffectTypes type : values()) {
/* 103 */         if (type.getName().equalsIgnoreCase(name)) {
/* 104 */           return type;
         }
       } 
/* 107 */       return NONE;
     }
   }
   
   class Effect {}
   
   public class EffectExplode extends Effect {
     private float strength;
     
     EffectExplode(float strength) {
/* 117 */       this.strength = strength;
     }
     
     public float getStrength() {
/* 121 */       return this.strength;
     }
   }
 }

