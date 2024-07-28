 package spyeedy.mods.lcu.network.client;
 
 import io.netty.buffer.ByteBuf;
 import java.util.UUID;
 import net.minecraft.client.Minecraft;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.nbt.NBTBase;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraftforge.fml.common.network.ByteBufUtils;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
 import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
 import spyeedy.mods.lcu.capability.CapabilityRingEligibility;
 import spyeedy.mods.lcu.capability.IRingEligibility;
 
 public class MessageSyncRingEligibility implements IMessage {
   public UUID playerUUID;
   public NBTTagCompound nbt;
   
   public MessageSyncRingEligibility() {}
   
   public MessageSyncRingEligibility(EntityPlayer player) {
     this.playerUUID = player.getUniqueID();
     this.nbt = player.getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null).serializeNBT();
   }
 
   
   public void fromBytes(ByteBuf buf) {
/* 29 */     this.playerUUID = UUID.fromString(ByteBufUtils.readUTF8String(buf));
/* 30 */     this.nbt = ByteBufUtils.readTag(buf);
   }
 
   
   public void toBytes(ByteBuf buf) {
/* 35 */     ByteBufUtils.writeUTF8String(buf, this.playerUUID.toString());
/* 36 */     ByteBufUtils.writeTag(buf, this.nbt);
   }
   
   public static class Handler
     implements IMessageHandler<MessageSyncRingEligibility, IMessage> {
     public IMessage onMessage(MessageSyncRingEligibility message, MessageContext ctx) {
/* 42 */       Minecraft.getMinecraft().addScheduledTask(() -> {
             if (message != null && ctx != null) {
               EntityPlayer player = (Minecraft.getMinecraft()).world.getPlayerEntityByUUID(message.playerUUID);
               if (player == null)
                 return; 
               CapabilityRingEligibility.RING_ELIGIBILITY_CAP.getStorage().readNBT(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, player.getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null), null, message.nbt);
             } 
           });
/* 50 */       return null;
     }
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\network\client\MessageSyncRingEligibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */