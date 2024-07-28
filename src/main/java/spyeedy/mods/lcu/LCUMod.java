 package spyeedy.mods.lcu;
 
 import lucraft.mods.lucraftcore.util.creativetabs.CreativeTabRegistry;
 import net.minecraft.creativetab.CreativeTabs;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemStack;
 import net.minecraftforge.common.capabilities.Capability;
 import net.minecraftforge.common.capabilities.CapabilityManager;
 import net.minecraftforge.fml.common.Mod;
 import net.minecraftforge.fml.common.Mod.EventHandler;
 import net.minecraftforge.fml.common.Mod.Instance;
 import net.minecraftforge.fml.common.SidedProxy;
 import net.minecraftforge.fml.common.event.FMLInitializationEvent;
 import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
 import net.minecraftforge.fml.common.network.IGuiHandler;
 import net.minecraftforge.fml.common.network.NetworkRegistry;
 import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
 import net.minecraftforge.fml.relauncher.Side;
 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import spyeedy.mods.lcu.capability.CapabilityRingEligibility;
 import spyeedy.mods.lcu.capability.IRingEligibility;
 import spyeedy.mods.lcu.gui.LCUGuiHandler;
 import spyeedy.mods.lcu.item.LCUItems;
 import spyeedy.mods.lcu.network.client.MessageSyncRingEligibility;
 import spyeedy.mods.lcu.network.server.MessageAcceptRing;
 import spyeedy.mods.lcu.network.server.MessageSendAbilityProjectileInfo;
 import spyeedy.mods.lcu.proxy.LCUCommonProxy;

 @Mod(
         modid = "spylcu",
         name = "Lantern Corps Universe",
         version = "1.12.2-2.1.2",
         dependencies = "required-after:lucraftcore@[1.12.2-2.4.4,)"
 )
 public class LCUMod {
     public static final String MODID = "spylcu";
     @SidedProxy(
             clientSide = "spyeedy.mods.lcu.proxy.LCUClientProxy",
             serverSide = "spyeedy.mods.lcu.proxy.LCUCommonProxy"
     )
     public static LCUCommonProxy proxy;
     @Instance("spylcu")
     public static LCUMod instance;
     public static SimpleNetworkWrapper networkWrapper;
     public static CreativeTabs tabLCU;
     public static final Logger LOGGER;

     public LCUMod() {
     }

     @EventHandler
     public void preInit(FMLPreInitializationEvent e) {
         CapabilityManager.INSTANCE.register(IRingEligibility.class, new CapabilityRingEligibility.Storage(), CapabilityRingEligibility.class);
         CreativeTabRegistry.addCreativeTab("lcu_tab", tabLCU);
         NetworkRegistry.INSTANCE.registerGuiHandler(instance, new LCUGuiHandler());
         proxy.preInit(e);
     }

     @EventHandler
     public void init(FMLInitializationEvent e) {
         proxy.init(e);
         int id = 0;
         networkWrapper.registerMessage(MessageSendAbilityProjectileInfo.Handler.class, MessageSendAbilityProjectileInfo.class, id++, Side.SERVER);
         networkWrapper.registerMessage(MessageSyncRingEligibility.Handler.class, MessageSyncRingEligibility.class, id++, Side.CLIENT);
         networkWrapper.registerMessage(MessageAcceptRing.Handler.class, MessageAcceptRing.class, id++, Side.SERVER);
     }

     static {
         networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("spylcu");
         tabLCU = new CreativeTabs("tabLCU") {
             public ItemStack createIcon() {
                 return new ItemStack(LCUItems.GREEN_LANTERN_RING);
             }
         };
         LOGGER = LogManager.getLogger();
     }
 }