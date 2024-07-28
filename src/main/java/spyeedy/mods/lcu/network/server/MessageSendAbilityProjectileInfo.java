 package spyeedy.mods.lcu.network.server;
 
 import io.netty.buffer.ByteBuf;
 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.superpowers.abilities.supplier.AbilityContainer;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.world.WorldServer;
 import net.minecraftforge.fml.common.network.ByteBufUtils;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
 import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
 import spyeedy.mods.lcu.abilities.AbilityProjectiles;
 
 public class MessageSendAbilityProjectileInfo implements IMessage {
   private String ability;
   private Ability.EnumAbilityContext context;
   private InfoType messageType;
   private int selectedProjectile;
   
   public MessageSendAbilityProjectileInfo() {}
   
   public MessageSendAbilityProjectileInfo(Ability ability, InfoType messageType, int selectedProjectile) {
     this.ability = ability.getKey();
     this.context = ability.context;
     this.messageType = messageType;
/* 26 */     this.selectedProjectile = selectedProjectile;
   }
 
   
   public void fromBytes(ByteBuf buf) {
/* 31 */     this.ability = ByteBufUtils.readUTF8String(buf);
/* 32 */     this.context = Ability.EnumAbilityContext.fromString(ByteBufUtils.readUTF8String(buf));
/* 33 */     this.messageType = InfoType.values()[buf.readInt()];
/* 34 */     this.selectedProjectile = buf.readInt();
   }
 
   
   public void toBytes(ByteBuf buf) {
/* 39 */     ByteBufUtils.writeUTF8String(buf, this.ability);
/* 40 */     ByteBufUtils.writeUTF8String(buf, this.context.toString());
/* 41 */     buf.writeInt(this.messageType.ordinal());
/* 42 */     buf.writeInt(this.selectedProjectile);
   }
   
   public static class Handler
     implements IMessageHandler<MessageSendAbilityProjectileInfo, IMessage>
   {
     public MessageSendAbilityProjectileInfo onMessage(MessageSendAbilityProjectileInfo message, MessageContext ctx) {
/* 49 */       WorldServer thread = (WorldServer)(ctx.getServerHandler()).player.world;
/* 50 */       thread.addScheduledTask(() -> {
             AbilityContainer container = Ability.getAbilityContainer(message.context, (ctx.getServerHandler()).player);
             if (container == null) {
               return;
             }
             AbilityProjectiles ability = (AbilityProjectiles)container.getAbility(message.ability);
             if (ability == null) {
               return;
             }
             if (message.messageType == MessageSendAbilityProjectileInfo.InfoType.SELECTED_PROJECTILE)
               ability.setSelectedProjectile(message.selectedProjectile); 
           });
       return null;
     }
   }
   
   public enum InfoType {
/* 67 */     SELECTED_PROJECTILE
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\network\server\MessageSendAbilityProjectileInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */