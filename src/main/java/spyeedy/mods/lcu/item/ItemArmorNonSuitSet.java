 package spyeedy.mods.lcu.item;
 
 import lucraft.mods.lucraftcore.superpowers.effects.EffectVibrating;
 import lucraft.mods.lucraftcore.superpowers.models.ModelBipedSuitSet;
 import lucraft.mods.lucraftcore.superpowers.suitsets.JsonSuitSet;
 import lucraft.mods.lucraftcore.superpowers.suitsets.SuitSet;
 import lucraft.mods.lucraftcore.util.helper.ItemHelper;
 import lucraft.mods.lucraftcore.util.helper.PlayerHelper;
 import lucraft.mods.lucraftcore.util.helper.StringHelper;
 import lucraft.mods.lucraftcore.util.items.OpenableArmor;
 import lucraft.mods.lucraftcore.util.render.ModelCache;
 import net.minecraft.client.model.ModelBase;
 import net.minecraft.client.model.ModelBiped;
 import net.minecraft.client.renderer.block.model.ModelResourceLocation;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.entity.player.EntityPlayerMP;
 import net.minecraft.init.SoundEvents;
 import net.minecraft.inventory.EntityEquipmentSlot;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemArmor;
 import net.minecraft.item.ItemStack;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.network.Packet;
 import net.minecraft.network.play.server.SPacketCustomSound;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.client.model.ModelLoader;
 import net.minecraftforge.fml.relauncher.Side;
 import net.minecraftforge.fml.relauncher.SideOnly;
 import spyeedy.mods.lcu.suitsets.LCUSuitSet;
 
 public class ItemArmorNonSuitSet extends ItemArmor implements OpenableArmor.IOpenableArmor {
   private final SuitSet suitSet;
   
   public ItemArmorNonSuitSet(SuitSet suitSet, EntityEquipmentSlot equipmentSlotIn, LCUSuitSet.LCUDataInfo dataInfo) {
    super(suitSet.getArmorMaterial(equipmentSlotIn), 0, equipmentSlotIn);
    setRegistryName(suitSet.getRegistryName().getNamespace(), suitSet.getRegistryName().getPath() + "_" + getArmorSlotName(equipmentSlotIn).toLowerCase());
     
    if (suitSet.showInCreativeTab()) {
      setCreativeTab(suitSet.getCreativeTab());
     } else {
      setCreativeTab(null);
     } 
    setTranslationKey(suitSet.getUnlocalizedName() + "_" + getArmorSlotName(equipmentSlotIn).toLowerCase());
     
    this.suitSet = suitSet;
    this.dataInfo = dataInfo;
   }
   private final LCUSuitSet.LCUDataInfo dataInfo;
   
   public String getItemStackDisplayName(ItemStack stack) {
    return StringHelper.translateToLocal(this.dataInfo.translateName);
   }
 
   
   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
    return getArmorTexturePath(false, entity instanceof EntityPlayer && PlayerHelper.hasSmallArms((EntityPlayer) entity), isArmorOpen(entity, stack));
   }
 
   
   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
    ModelBiped armorModel = null;
     
    if (!itemStack.isEmpty()) {
      boolean smallArms = false;
       
      if (entityLiving instanceof EntityPlayer) {
        smallArms = PlayerHelper.hasSmallArms((EntityPlayer)entityLiving);
       }
      armorModel = getArmorModel(this.suitSet, itemStack, entityLiving, armorSlot, smallArms, isArmorOpen(entityLiving, itemStack));
       
      if (armorModel != null) {
        armorModel.setModelAttributes(_default);
        return armorModel;
       } 
     } 
     
    return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
   }
   
   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(SuitSet suitSet, ItemStack stack, Entity entity, EntityEquipmentSlot slot, boolean smallArms, boolean open) {
    String key = suitSet.getRegistryName().toString() + "_" + this.dataInfo.armor_model_scale + "_" + getArmorTexturePath(false, smallArms, open) + "_" + getArmorTexturePath(true, smallArms, open) + "_" + slot.toString() + "_" + smallArms + "_" + EffectVibrating.isVibrating(entity);
    ModelBase model = ModelCache.getModel(key);
    if (model != null && model instanceof ModelBiped) {
      return (ModelBiped)model;
     }
    return (ModelBiped)ModelCache.storeModel(key, new ModelBipedSuitSet(this.dataInfo.armor_model_scale, getArmorTexturePath(false, smallArms, open), getArmorTexturePath(true, smallArms, open), suitSet, slot, smallArms, EffectVibrating.isVibrating(entity)));
   }
   
   public String getArmorTexturePath(boolean light, boolean smallArms, boolean open) {
    String key = light ? "glow" : "normal";
    if (smallArms && this.dataInfo.textures.containsKey(key + "_smallarms"))
      key = key + "_smallarms";
    if (open && this.dataInfo.textures.containsKey(key + "_open")) {
      key = key + "_open";
     }
/* 100 */     return this.dataInfo.textures.containsKey(key) ? this.dataInfo.textures.get(key) : "";
   }
 
 
 
   
   public boolean canBeOpened(Entity entity, ItemStack stack) {
/* 107 */     return this.dataInfo.openable;
   }
 
   
   public void setArmorOpen(Entity entity, ItemStack stack, boolean open) {
/* 112 */     if (this.dataInfo.openable) {
/* 113 */       NBTTagCompound nbt = new NBTTagCompound();
/* 114 */       if (stack.hasTagCompound())
/* 115 */         nbt = stack.getTagCompound(); 
/* 116 */       nbt.setBoolean("IsOpen", open);
/* 117 */       onArmorToggled(entity, stack, nbt.getBoolean("IsOpen"));
/* 118 */       stack.setTagCompound(nbt);
     } 
   }
 
   
   public boolean isArmorOpen(Entity entity, ItemStack stack) {
/* 124 */     if (this.dataInfo.openable && stack.hasTagCompound()) {
/* 125 */       return stack.getTagCompound().getBoolean("IsOpen");
     }
/* 127 */     return false;
   }
 
   
   public void onArmorToggled(Entity entity, ItemStack stack, boolean open) {
/* 132 */     if (entity instanceof EntityPlayerMP) {
/* 133 */       ((EntityPlayerMP)entity).connection.sendPacket(new SPacketCustomSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON.getRegistryName().toString(), entity.getSoundCategory(), entity.posX, entity.posY, entity.posZ, 1.0F, 1.0F));
     }
   }
 
   
   public String getArmorSlotName(EntityEquipmentSlot slot) {
/* 139 */     switch (slot) {
       case HEAD:
/* 141 */         return "Helmet";
       case LEGS:
/* 143 */         return "Legs";
       case FEET:
/* 145 */         return "Boots";
     } 
/* 147 */     return "";
   }
 
   
   @SideOnly(Side.CLIENT)
   public void registerModel() {
/* 153 */     if (this.dataInfo.itemModelType == null || (this.dataInfo.itemModelType != null && this.dataInfo.itemModelLoc.isEmpty())) {
/* 154 */       ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(this.suitSet.getRegistryName().getNamespace(), this.suitSet.getRegistryName().getPath() + "_suit"), getEquipmentSlot().toString().toLowerCase()));
     }
/* 156 */     else if (this.dataInfo.itemModelType == JsonSuitSet.JsonSuitSetItemModelType.MODEL) {
/* 157 */       ResourceLocation loc = new ResourceLocation(this.dataInfo.itemModelLoc);
/* 158 */       ItemHelper.registerItemModel(this, loc.getNamespace(), loc.getPath());
/* 159 */     } else if (this.dataInfo.itemModelType == JsonSuitSet.JsonSuitSetItemModelType.BLOCKSTATE) {
/* 160 */       ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(this.dataInfo.itemModelLoc), getEquipmentSlot().toString().toLowerCase()));
     } 
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\item\ItemArmorNonSuitSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */