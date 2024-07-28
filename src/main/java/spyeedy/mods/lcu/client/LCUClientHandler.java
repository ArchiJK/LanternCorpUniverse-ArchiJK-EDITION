 package spyeedy.mods.lcu.client;
 
 import lucraft.mods.lucraftcore.util.helper.LCRenderHelper;
 import lucraft.mods.lucraftcore.util.helper.StringHelper;
 import net.minecraft.client.gui.GuiButton;
 import net.minecraftforge.client.event.GuiScreenEvent;
 import net.minecraftforge.fml.client.config.GuiButtonExt;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
 import net.minecraftforge.fml.relauncher.Side;
 import spyeedy.mods.lcu.LCUCorps;
 import spyeedy.mods.lcu.LCUMod;
 import spyeedy.mods.lcu.capability.CapabilityRingEligibility;
 import spyeedy.mods.lcu.capability.IRingEligibility;
 import spyeedy.mods.lcu.network.server.MessageAcceptRing;
 
 
 
 
 @EventBusSubscriber({Side.CLIENT})
 public class LCUClientHandler
 {
   @SubscribeEvent
   public static void initGuiPost(GuiScreenEvent.InitGuiEvent.Post e) {
/* 26 */     if (e.getGui() instanceof lucraft.mods.lucraftcore.extendedinventory.gui.GuiExtendedInventory) {
/* 27 */       int i = ((e.getGui()).width - 176) / 2;
/* 28 */       int j = ((e.getGui()).height - 174) / 2;
       
/* 30 */       String glOffer = StringHelper.translateToLocal("spylcu.gloffer");
/* 31 */       int glOfferWidth = LCRenderHelper.mc.fontRenderer.getStringWidth(glOffer);
/* 32 */       GuiButtonExt guiButtonExt = new GuiButtonExt(60, i - glOfferWidth - 10 - 5, j + 5, glOfferWidth + 10, 18, glOffer);
/* 33 */       e.getButtonList().add(guiButtonExt);
       
/* 35 */       IRingEligibility data = LCRenderHelper.mc.player.getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null);
/* 36 */       /* 37 */       /* 39 */
           guiButtonExt.visible = data.didDecline(LCUCorps.GREEN);
     } 
   }
   @SubscribeEvent
   public static void buttonPressPost(GuiScreenEvent.ActionPerformedEvent.Post e) {
/* 45 */     if (e.getGui() instanceof lucraft.mods.lucraftcore.extendedinventory.gui.GuiExtendedInventory) {
/* 46 */       IRingEligibility data = LCRenderHelper.mc.player.getCapability(CapabilityRingEligibility.RING_ELIGIBILITY_CAP, null);
/* 47 */       if ((e.getButton()).id == 60 && 
/* 48 */         data.didDecline(LCUCorps.GREEN)) {
/* 49 */         LCUMod.networkWrapper.sendToServer(new MessageAcceptRing(LCUCorps.GREEN));
/* 50 */         (e.getButton()).visible = false;
       } 
     } 
   }
 }

