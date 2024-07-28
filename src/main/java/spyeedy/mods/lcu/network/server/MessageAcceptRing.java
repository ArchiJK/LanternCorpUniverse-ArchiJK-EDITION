 package spyeedy.mods.lcu.network.server;
 
 import io.netty.buffer.ByteBuf;
 import net.minecraft.world.WorldServer;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
 import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
 import spyeedy.mods.lcu.LCUCorps;
 import spyeedy.mods.lcu.LCUMod;
 import spyeedy.mods.lcu.capability.CapabilityRingEligibility;
 import spyeedy.mods.lcu.capability.IRingEligibility;
 
 public class MessageAcceptRing implements IMessage {
   private LCUCorps corps;
   
   public MessageAcceptRing() {}
   
   public MessageAcceptRing(LCUCorps corps) {
     this.corps = corps;
   }
 
   
   public void fromBytes(ByteBuf buf) {
     this.corps = LCUCorps.values()[buf.readInt()];
   }
 
   
   public void toBytes(ByteBuf buf) {
/* 29 */     buf.writeInt(this.corps.ordinal());
   }
   
   public static class Handler
     implements IMessageHandler<MessageAcceptRing, IMessage>
   {
     public IMessage onMessage(MessageAcceptRing message, MessageContext ctx) {
/* 36 */       WorldServer thread = (WorldServer)(ctx.getServerHandler()).player.world;
/* 37 */       thread.addScheduledTask(() -> {
             LCUMod.LOGGER.info("Accepting ring!");
             (ctx.getServerHandler()).player.getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null).acceptRing(message.corps);
           });
/* 41 */       return null;
     }
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\network\server\MessageAcceptRing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */