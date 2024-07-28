 package spyeedy.mods.lcu.suitsets;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Map;
 import lucraft.mods.lucraftcore.superpowers.suitsets.JsonSuitSet;
 import lucraft.mods.lucraftcore.superpowers.suitsets.RegisterSuitSetEvent;
 import lucraft.mods.lucraftcore.superpowers.suitsets.SuitSet;
 import net.minecraft.inventory.EntityEquipmentSlot;
 import net.minecraft.item.Item;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.client.event.ModelRegistryEvent;
 import net.minecraftforge.event.RegistryEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.relauncher.Side;
 import net.minecraftforge.fml.relauncher.SideOnly;
 import net.minecraftforge.registries.IForgeRegistryEntry;
 import spyeedy.mods.lcu.item.ItemArmorNonSuitSet;
 
 @EventBusSubscriber(modid = "spylcu")
 public class LCUSuitSet
   extends SuitSet
 {
  public static final LCUSuitSet GREEN_LANTERN = new LCUSuitSet("green_lantern");
   
  private static final ArrayList<JsonSuitSet> SUIT_SETS = new ArrayList<>();
  private static final ArrayList<ItemArmorNonSuitSet> ARMOR_PARTS = new ArrayList<>();
  private static final EntityEquipmentSlot[] SLOTS = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };
   
   @SubscribeEvent
   public static void onRegisterSuitSets(RegisterSuitSetEvent e) {
    e.register(GREEN_LANTERN);
    for (ResourceLocation sets : SuitSet.REGISTRY.getKeys()) {
      if (SuitSet.REGISTRY.getObject(sets) instanceof JsonSuitSet && ((JsonSuitSet)SuitSet.REGISTRY.getObject(sets)).getParent() == GREEN_LANTERN) {
        JsonSuitSet suitSet = (JsonSuitSet)SuitSet.REGISTRY.getObject(sets);
        SUIT_SETS.add(suitSet);
       } 
     } 
   }
   
   @SubscribeEvent
   public static void onRegisterItems(RegistryEvent.Register<Item> e) throws Exception {
    for (JsonSuitSet set : SUIT_SETS) {
      for (EntityEquipmentSlot slot : SLOTS) {
        if (shouldMakePart(set, slot) && !set.slotInfos.containsKey(slot)) {
          LCUDataInfo dataInfo = new LCUDataInfo();
          NBTTagCompound tag = getPartTag(set, slot);
           
          if (tag != null) {
            dataInfo.glow = (tag.hasKey("glow") && tag.getBoolean("glow"));
            dataInfo.openable = (tag.hasKey("openable") && tag.getBoolean("openable"));
             
            if (tag.hasKey("armor_model_scale")) {
              dataInfo.armor_model_scale = tag.getFloat("armor_model_scale");
             } else {
              dataInfo.armor_model_scale = (slot == EntityEquipmentSlot.HEAD) ? 0.5F : ((slot == EntityEquipmentSlot.FEET) ? 0.252F : 0.25F);
             } 
             
            if (tag.hasKey("translate_name")) {
              dataInfo.translateName = tag.getString("translate_name");
             } else {
              dataInfo.translateName = null;
              throw new Exception("Missing 'translate_name' for suit set %SSName - part %PartName".replace("%SSName", set.getRegistryName().toString()).replace("%PartName", slot.getName()));
             } 
             
            dataInfo.textures = tag.hasKey("textures") ? new HashMap<>() : null;
            NBTTagCompound texturesTag = tag.getCompoundTag("textures");
            if (texturesTag != null) {
              if (texturesTag.hasKey("normal")) {
                dataInfo.textures.put("normal", texturesTag.getString("normal"));
               } else {
                throw new Exception("Missing 'normal' texture for suit set %SSName - part %PartName".replace("%SSName", set.getRegistryName().toString()).replace("%PartName", slot.getName()));
               } 
              if (dataInfo.openable) {
                if (texturesTag.hasKey("normal_open")) {
                  dataInfo.textures.put("normal_open", texturesTag.getString("normal_open"));
                 } else {
                  throw new Exception("Missing 'normal_open' texture for suit set %SSName - part %PartName".replace("%SSName", set.getRegistryName().toString()).replace("%PartName", slot.getName()));
                 } 
               }
              if (dataInfo.glow)
                if (texturesTag.hasKey("glow")) {
                  dataInfo.textures.put("glow", texturesTag.getString("glow"));
                 } else {
                  throw new Exception("Missing 'glow' texture for suit set %SSName - part %PartName".replace("%SSName", set.getRegistryName().toString()).replace("%PartName", slot.getName()));
                 }  
              if (dataInfo.glow && dataInfo.openable) {
                if (texturesTag.hasKey("glow_open")) {
                  dataInfo.textures.put("glow_open", texturesTag.getString("glow_open"));
                 } else {
                  throw new Exception("Missing 'glow_open' texture for suit set %SSName - part %PartName".replace("%SSName", set.getRegistryName().toString()).replace("%PartName", slot.getName()));
                 } 
               }
             } 
            dataInfo.itemModelType = null;
            NBTTagCompound itemModelTag = tag.getCompoundTag("item_texture");
            if (itemModelTag != null) {
/* 100 */               if (itemModelTag.hasKey("model")) {
/* 101 */                 dataInfo.itemModelType = JsonSuitSet.JsonSuitSetItemModelType.MODEL;
/* 102 */                 dataInfo.itemModelLoc = itemModelTag.getString("model");
/* 103 */               } else if (itemModelTag.hasKey("blockstate")) {
/* 104 */                 dataInfo.itemModelType = JsonSuitSet.JsonSuitSetItemModelType.BLOCKSTATE;
/* 105 */                 dataInfo.itemModelLoc = itemModelTag.getString("blockstate");
/* 106 */               } else if (itemModelTag.hasKey("model") && itemModelTag.hasKey("blockstate")) {
/* 107 */                 throw new Exception("Cannot have both 'model' and 'blockstate' for 'item_texture', only one type is allowed for suit set %SSName - part %PartName".replace("%SSName", set.getRegistryName().toString()).replace("%PartName", slot.getName()));
               } 
             }
             
/* 111 */             ItemArmorNonSuitSet armor = new ItemArmorNonSuitSet(set, slot, dataInfo);
/* 112 */             e.getRegistry().register(armor);
/* 113 */             ARMOR_PARTS.add(armor);
           } 
         } 
       } 
     } 
/* 118 */     SUIT_SETS.clear();
   }
   
   @SubscribeEvent
   @SideOnly(Side.CLIENT)
   public static void onRegisterModels(ModelRegistryEvent e) {
/* 124 */     for (ItemArmorNonSuitSet item : ARMOR_PARTS) {
/* 125 */       item.registerModel();
     }
/* 127 */     ARMOR_PARTS.clear();
   }
   
   private static boolean shouldMakePart(SuitSet set, EntityEquipmentSlot slot) {
/* 131 */     if (slot == EntityEquipmentSlot.HEAD && set.getHelmet() == null)
/* 132 */       return true; 
/* 133 */     if (slot == EntityEquipmentSlot.LEGS && set.getLegs() == null) {
/* 134 */       return true;
     }
/* 136 */     return (slot == EntityEquipmentSlot.FEET && set.getBoots() == null);
   }
 
   
   private static NBTTagCompound getPartTag(SuitSet set, EntityEquipmentSlot slot) {
/* 141 */     if (slot == EntityEquipmentSlot.HEAD)
/* 142 */       return set.getData().getCompoundTag("maskTag"); 
/* 143 */     if (slot == EntityEquipmentSlot.LEGS)
/* 144 */       return set.getData().getCompoundTag("legsTag"); 
/* 145 */     if (slot == EntityEquipmentSlot.FEET) {
/* 146 */       return set.getData().getCompoundTag("bootsTag");
     }
/* 148 */     return null;
   }
 
 
   
   public LCUSuitSet(String name) {
/* 154 */     super(name);
/* 155 */     setRegistryName("spylcu", name);
   }
   
   public static class LCUDataInfo {
     public boolean openable;
     public boolean glow;
     public float armor_model_scale;
     public String translateName;
     public Map<String, String> textures;
     public JsonSuitSet.JsonSuitSetItemModelType itemModelType;
     public String itemModelLoc;
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\suitsets\LCUSuitSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */