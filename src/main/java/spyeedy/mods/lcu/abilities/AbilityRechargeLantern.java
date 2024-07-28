 package spyeedy.mods.lcu.abilities;
 
 import java.util.ArrayList;
 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataString;
 import lucraft.mods.lucraftcore.superpowers.abilities.predicates.AbilityCondition;
 import net.minecraft.block.Block;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraftforge.event.entity.player.PlayerInteractEvent;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import spyeedy.mods.lcu.LCUMod;
 import spyeedy.mods.lcu.abilities.condition.AbilityConditionAbilityLCU;
 import spyeedy.mods.lcu.abilities.data.AbilityDataStringList;
 import spyeedy.mods.lcu.block.LCUBlocks;
 import spyeedy.mods.lcu.util.LCUIconHelper;
 
 
 
 public class AbilityRechargeLantern
   extends AbilityToggle
 {
  public static final AbilityData<String> BLOCK_TO_RECHARGE = (new AbilityDataString("block_to_recharge")).disableSaving().enableSetting("block_registry_name", "The block to interact with to recharge lantern energy.");
  public static final AbilityData<ArrayList<String>> OATH = (new AbilityDataStringList("oath")).disableSaving().enableSetting("oath", "Sets the oath to say when you recharge.");
   
   public AbilityRechargeLantern(EntityLivingBase entity) {
    super(entity);
   }
 
   
   public void registerData() {
    super.registerData();
     
    this.dataManager.register(BLOCK_TO_RECHARGE, LCUBlocks.GREEN_LANTERN_POWER_BATTERY.getRegistryName().toString());
    ArrayList<String> oaths = new ArrayList<>();
    oaths.add("In Brightest Day, In Blackest Night");
    oaths.add("No evil shall escape my sight");
    oaths.add("Let those who worship evil's might");
    oaths.add("Beware my power");
    oaths.add("Green Lantern's Light!");
    this.dataManager.register(OATH, oaths);
   }
 
 
   
   public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
    LCUIconHelper.drawAbilityIcon(mc, gui, x, y, 1, 4);
   }
 
   
   public boolean action() {
    if (!isEnabled()) {
      for (AbilityCondition condition : getConditions()) {
        if (condition instanceof AbilityConditionAbilityLCU && (
          (AbilityConditionAbilityLCU)condition).getAbility() instanceof AbilityLantern) {
          return super.action();
         }
       } 
     } else {
       
      return super.action();
     } 
    return false;
   }
 
   
   public void updateTick() {}
   
   public ArrayList<String> getOaths() {
    return getDataManager().get(OATH);
   }
   
   @EventBusSubscriber(modid = "spylcu")
   public static class EventHandler
   {
     @SubscribeEvent
     public static void blockInteract(PlayerInteractEvent.RightClickBlock event) {
      EntityPlayer player = event.getEntityPlayer();
      if (!(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof net.minecraft.block.BlockAir)) {
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
         
        for (Ability ability : Ability.getAbilities(player)) {
          if (ability instanceof AbilityRechargeLantern &&
            ability.isEnabled() && ability.isUnlocked()) {
            String blockRegName = ability.getDataManager().get(AbilityRechargeLantern.BLOCK_TO_RECHARGE);
             
            for (AbilityCondition condition : ability.getConditions()) {
              if (condition instanceof AbilityConditionAbilityLCU && (
                (AbilityConditionAbilityLCU)condition).getAbility() instanceof AbilityLantern) {
                AbilityLantern abilityLantern = (AbilityLantern)((AbilityConditionAbilityLCU)condition).getAbility();
                 
                if (block.getRegistryName().toString().equals(blockRegName)) {
                  event.setCanceled(true);
                   
/* 100 */                   if (!player.world.isRemote) {
/* 101 */                     player.openGui(LCUMod.instance, 0, player.world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
/* 102 */                     abilityLantern.setLanternEnergy(abilityLantern.getMaxLanternEnergy());
/* 103 */                     ability.action();
                   } 
                 } 
               } 
             } 
           } 
         } 
       } 
     }
   }
 }

