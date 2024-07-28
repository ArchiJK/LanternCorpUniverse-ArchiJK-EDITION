 package spyeedy.mods.lcu.entity;
 
 import java.util.UUID;
 import net.minecraft.world.World;
 import spyeedy.mods.lcu.block.LCUBlocks;
 import spyeedy.mods.lcu.item.LCUItems;
 
 public class EntityGreenLanternRing
   extends EntityRing
 {
   public EntityGreenLanternRing(World worldIn) {
     super(worldIn);
   }
   
   public EntityGreenLanternRing(World worldIn, UUID playerUUID) {
     super(worldIn, playerUUID, LCUItems.GREEN_LANTERN_RING, LCUBlocks.GREEN_LANTERN_POWER_BATTERY, new String[] { "%pName, you have the will to overcome your fear.", "Join the Green Lantern Corps" });
   }
 }


