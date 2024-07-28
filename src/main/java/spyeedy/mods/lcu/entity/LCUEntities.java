 package spyeedy.mods.lcu.entity;
 import net.minecraft.client.renderer.entity.Render;
 import net.minecraft.client.renderer.entity.RenderManager;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.event.RegistryEvent;
 import net.minecraftforge.fml.client.registry.IRenderFactory;
 import net.minecraftforge.fml.client.registry.RenderingRegistry;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.registry.EntityEntry;
 import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
 import net.minecraftforge.fml.relauncher.Side;
 import net.minecraftforge.fml.relauncher.SideOnly;
 import net.minecraftforge.registries.IForgeRegistryEntry;
 import spyeedy.mods.lcu.client.render.RenderLanternRing;
 import spyeedy.mods.lcu.item.LCUItems;
 
 @EventBusSubscriber
 public class LCUEntities {
   @SubscribeEvent
   public static void onRegisterEntities(RegistryEvent.Register<EntityEntry> e) {
     e.getRegistry().register(EntityEntryBuilder.create().entity(EntityProjectile.class).id(new ResourceLocation("spylcu", "projectile"), 0).name("projectile").tracker(64, 1, true).build());
     e.getRegistry().register(EntityEntryBuilder.create().entity(EntityGreenLanternRing.class).id(new ResourceLocation("spylcu", "green_lantern_ring"), 1).name("green_lantern_ring").tracker(64, 1, true).build());
   }
   
   @SideOnly(Side.CLIENT)
   public static void registerEntityModels() {
/* 28 */     RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, spyeedy.mods.lcu.client.render.RenderProjectile::new);
/* 29 */     RenderingRegistry.registerEntityRenderingHandler(EntityGreenLanternRing.class, manager -> new RenderLanternRing(manager, LCUItems.GREEN_LANTERN_RING));
   }
 }


/* Location:              C:\Users\JK\Desktop\LanternCorpsUniverse-1.12.2-2.1.2-deobf.jar!\spyeedy\mods\lcu\entity\LCUEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */