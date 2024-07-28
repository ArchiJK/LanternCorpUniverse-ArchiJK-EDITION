 package spyeedy.mods.lcu.entity;
 
 import io.netty.buffer.ByteBuf;
 import java.awt.Color;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.projectile.EntityThrowable;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.util.DamageSource;
 import net.minecraft.util.math.RayTraceResult;
 import net.minecraft.util.math.Vec3d;
 import net.minecraft.world.World;
 import net.minecraftforge.fml.common.network.ByteBufUtils;
 import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
 import spyeedy.mods.lcu.addonpacks.entity.AddonPackEntityReader;
 import spyeedy.mods.lcu.addonpacks.entity.JsonProjectileInfo;
 
 public class EntityProjectile
   extends EntityThrowable implements IEntityAdditionalSpawnData {
   private String jsonEntityId;
   private Color color;
   
   public EntityProjectile(World worldIn) {
    super(worldIn);
   }
   
   public EntityProjectile(World worldIn, EntityLivingBase throwerIn, String entityId, Color color) {
    super(worldIn, throwerIn);
    this.jsonEntityId = entityId;
    this.color = color;
   }
 
   
   public void onEntityUpdate() {
    JsonProjectileInfo projectileInfo = getJsonInfo();
    if (projectileInfo != null) {
      setSize(projectileInfo.getCollisionBox()[0], projectileInfo.getCollisionBox()[1]);
     }
     
    double speed = (new Vec3d(this.posX, this.posY, this.posZ)).distanceTo(new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ));
    if (!this.world.isRemote && (this.ticksExisted > 600 || speed < 0.01D) &&
      projectileInfo != null && !projectileInfo.isDebugMode()) {
      setDead();
     }
     
    super.onEntityUpdate();
   }
 
   
   protected void onImpact(RayTraceResult result) {
    if (result == null || this.isDead || this.world.isRemote) {
       return;
     }
    if (getJsonEntityId() != null && !getJsonEntityId().equals("")) {
      JsonProjectileInfo projectileInfo = getJsonInfo();
      if (projectileInfo != null) {
        if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
          if (result.entityHit == this.thrower)
             return; 
          result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), projectileInfo.getDamage());
           
          if (projectileInfo.getEffectType() == JsonProjectileInfo.EffectTypes.PUSHBACK) {
            result.entityHit.addVelocity(this.motionX, 0.0D, this.motionZ);
           }
         } 
        if (result.typeOfHit != RayTraceResult.Type.MISS && projectileInfo.getEffectType() == JsonProjectileInfo.EffectTypes.EXPLODE) {
          JsonProjectileInfo.EffectExplode effect = (JsonProjectileInfo.EffectExplode)projectileInfo.getEffect();
          this.world.createExplosion(this, this.posX, this.posY, this.posZ, effect.getStrength(), true);
         } 
       } 
     } 
     
    if (!this.world.isRemote) {
      setDead();
     }
   }
   
   protected float getGravityVelocity() {
    return 0.0F;
   }
 
   
   public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setString("entityId", this.jsonEntityId);
    compound.setInteger("colorR", this.color.getRed());
    compound.setInteger("colorG", this.color.getGreen());
    compound.setInteger("colorB", this.color.getBlue());
   }
 
   
   public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    this.jsonEntityId = compound.getString("entityId");
    this.color = new Color(compound.getInteger("colorR"), compound.getInteger("colorG"), compound.getInteger("colorB"));
   }
 
   
   public void writeSpawnData(ByteBuf buffer) {
/* 100 */     NBTTagCompound nbt = new NBTTagCompound();
/* 101 */     writeEntityToNBT(nbt);
/* 102 */     ByteBufUtils.writeTag(buffer, nbt);
   }
 
   
   public void readSpawnData(ByteBuf additionalData) {
/* 107 */     readEntityFromNBT(ByteBufUtils.readTag(additionalData));
   }
   
   public String getJsonEntityId() {
/* 111 */     return this.jsonEntityId;
   }
   
   public JsonProjectileInfo getJsonInfo() {
/* 115 */     return AddonPackEntityReader.PROJECTILES.get(this.jsonEntityId);
   }
   
   public Color getColor() {
/* 119 */     return this.color;
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\entity\EntityProjectile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */