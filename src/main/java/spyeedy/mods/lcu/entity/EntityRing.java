 package spyeedy.mods.lcu.entity;
 import io.netty.buffer.ByteBuf;
 import java.util.Collection;
 import java.util.Collections;
 import java.util.UUID;
 import lucraft.mods.lucraftcore.extendedinventory.capabilities.CapabilityExtendedInventory;
 import lucraft.mods.lucraftcore.extendedinventory.capabilities.IExtendedInventoryCapability;
 import lucraft.mods.lucraftcore.karma.KarmaHandler;
 import net.minecraft.block.Block;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityFlying;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemStack;
 import net.minecraft.nbt.NBTBase;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.nbt.NBTUtil;
 import net.minecraft.util.DamageSource;
 import net.minecraft.util.EnumHand;
 import net.minecraft.util.NonNullList;
 import net.minecraft.util.text.ITextComponent;
 import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
 import net.minecraftforge.fml.common.network.ByteBufUtils;
 import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
 import spyeedy.mods.lcu.LCUConfig;
 import spyeedy.mods.lcu.block.BlockPowerBattery;
 import spyeedy.mods.lcu.capability.CapabilityRingEligibility;
 import spyeedy.mods.lcu.capability.IRingEligibility;
 import spyeedy.mods.lcu.item.ItemLanternRing;
 
 public class EntityRing extends EntityFlying implements IEntityAdditionalSpawnData {
  private final NonNullList<String> messages = NonNullList.create();
   private UUID targetUUID;
   protected ItemLanternRing ring;
   protected BlockPowerBattery powerBattery;
   
   public EntityRing(World worldIn) {
    super(worldIn);
   }
   
   public EntityRing(World worldIn, UUID playerUUID, ItemLanternRing ring, BlockPowerBattery powerBattery, String[] messages) {
    this(worldIn);
    this.targetUUID = playerUUID;
    this.ring = ring;
    this.powerBattery = powerBattery;
    Collections.addAll(this.messages, messages);
    setSize(0.25F, 0.25F);
   }
 
   
   public void onEntityUpdate() {
    super.onEntityUpdate();
     
    if (this.world.isRemote)
       return; 
    EntityPlayer player = this.world.getPlayerEntityByUUID(this.targetUUID);
    boolean shouldRingbeAlive = false;
     
    if (player != null) {
      ItemStack wristStack = player.getCapability(CapabilityExtendedInventory.EXTENDED_INVENTORY_CAP, null).getInventory().getStackInSlot(2);
      boolean ifHasRingForExtInv = (!wristStack.isEmpty() && wristStack.getItem() == this.ring);
       
      if (player.getUniqueID().equals(getTargetUUID()) && !player.inventory.hasItemStack(new ItemStack(this.ring)) && !ifHasRingForExtInv &&
        KarmaHandler.getKarma(player) <= LCUConfig.GREEN_LANTERN_MAX_KARMA && KarmaHandler.getKarma(player) >= LCUConfig.GREEN_LANTERN_MIN_KARMA) {
        shouldRingbeAlive = true;
       }
       
      if (shouldRingbeAlive) {
        if (this.ticksExisted == 1) {
          for (String str : this.messages) {
            player.sendMessage(new TextComponentString(str.replace("%pName", player.getDisplayNameString())));
           }
         }
         
        double speed = 0.5D;
         
        double distX = player.posX - this.posX;
        double distZ = player.posZ - this.posZ;
        double distY = this.posY - 0.9D - player.posY;
         
        if (distY < -0.1D) {
          this.motionY += speed / 12.0D;
        } else if (distY > 0.1D) {
          this.motionY -= speed / 12.0D;
         } else {
          this.motionY = 0.0D;
         } 
         
        if (getDistance(player) > 3.0F) {
          this.motionX += distX / 40.0D;
          this.motionZ += distZ / 40.0D;
         } 
       } 
     } 
     
    if (!shouldRingbeAlive) {
      setDead();
     }
   }
 
   
   protected boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 103 */     if (!player.getUniqueID().equals(this.targetUUID)) return false;
     
/* 105 */     player.inventory.addItemStackToInventory(new ItemStack(this.ring));
     
/* 107 */     ItemStack powerBatteryStack = new ItemStack(Item.getItemFromBlock(this.powerBattery));
/* 108 */     if (!player.inventory.hasItemStack(powerBatteryStack)) {
/* 109 */       player.inventory.addItemStackToInventory(powerBatteryStack);
     }
/* 111 */     return true;
   }
 
 
 
   
   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 118 */     if (this.world.isRemote)
       return; 
/* 120 */     if (damageSrc.getTrueSource() instanceof EntityPlayer) {
/* 121 */       EntityPlayer player = (EntityPlayer)damageSrc.getTrueSource();
       
/* 123 */       if (!player.getUniqueID().equals(this.targetUUID))
         return; 
/* 125 */       IRingEligibility cap = player.getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null);
/* 126 */       cap.declineRing(this.ring.getCorp());
       
/* 128 */       setDead();
     } 
   }
 
   
   public void writeEntityToNBT(NBTTagCompound compound) {
/* 134 */     super.writeEntityToNBT(compound);
     
/* 136 */     compound.setTag("target_uuid", NBTUtil.createUUIDTag(this.targetUUID));
   }
 
   
   public void readEntityFromNBT(NBTTagCompound compound) {
/* 141 */     super.readEntityFromNBT(compound);
     
/* 143 */     this.targetUUID = NBTUtil.getUUIDFromTag(compound.getCompoundTag("target_uuid"));
   }
 
   
   public void writeSpawnData(ByteBuf buffer) {
/* 148 */     NBTTagCompound nbt = new NBTTagCompound();
/* 149 */     writeEntityToNBT(nbt);
/* 150 */     ByteBufUtils.writeTag(buffer, nbt);
   }
 
   
   public void readSpawnData(ByteBuf additionalData) {
/* 155 */     readEntityFromNBT(ByteBufUtils.readTag(additionalData));
   }
   
   public UUID getTargetUUID() {
/* 159 */     return this.targetUUID;
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\entity\EntityRing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */