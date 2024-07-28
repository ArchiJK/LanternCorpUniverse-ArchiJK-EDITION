 package spyeedy.mods.lcu.abilities;
 
 import java.awt.Color;
 import java.util.Iterator;

 import lucraft.mods.lucraftcore.superpowers.abilities.Ability;
 import lucraft.mods.lucraftcore.superpowers.abilities.AbilityToggle;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataBoolean;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataColor;
 import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityDataFloat;
 import lucraft.mods.lucraftcore.superpowers.render.RenderSuperpowerLayerEvent;
 import lucraft.mods.lucraftcore.util.helper.LCRenderHelper;
 import lucraft.mods.lucraftcore.util.helper.PlayerHelper;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import net.minecraft.client.model.ModelBase;
 import net.minecraft.client.model.ModelPlayer;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.relauncher.Side;
 import spyeedy.mods.lcu.util.LCUIconHelper;
 
 public class AbilityAura
   extends AbilityToggle {
  public static final AbilityData<Color> COLOR = (new AbilityDataColor("color")).disableSaving().enableSetting("color", "Color of the aura.");
  public static final AbilityData<Float> ALPHA = (new AbilityDataFloat("alpha")).disableSaving().enableSetting("alpha", "Transparency of the aura. Values between 0 - 1");
  public static final AbilityData<Boolean> GLOW = (new AbilityDataBoolean("glow")).disableSaving().enableSetting("glow", "Make the aura glow.");
   
   public AbilityAura(EntityLivingBase entity) {
    super(entity);
   }
 
   
   public void registerData() {
    super.registerData();
     
    this.dataManager.register(COLOR, Color.GREEN);
    this.dataManager.register(ALPHA, Float.valueOf(0.25F));
    this.dataManager.register(GLOW, Boolean.valueOf(false));
   }
 
   
   public void drawIcon(Minecraft mc, Gui gui, int x, int y) {
    Color color = getDataManager().get(COLOR);
    GlStateManager.pushMatrix();
 
     
    GlStateManager.color((color.getRed() / 255), (color.getGreen() / 255), (color.getBlue() / 255));
    LCUIconHelper.drawAbilityIcon(mc, gui, x, y, 1, 1);
 
     
    GlStateManager.color(1.0F, 1.0F, 1.0F);
    LCUIconHelper.drawAbilityIcon(mc, gui, x, y, 1, 0);
    GlStateManager.popMatrix();
   }
   
   public void updateTick() {}
   
   @EventBusSubscriber(modid = "spylcu", value = {Side.CLIENT})
   public static class Renderer
   {
    private static final ResourceLocation[] auraTexture = new ResourceLocation[] { new ResourceLocation("spylcu:textures/entity/aura_steve.png"), new ResourceLocation("spylcu:textures/entity/aura_alex.png") };

       @SubscribeEvent
       public static void onRenderLayer(RenderSuperpowerLayerEvent e) {
           if (Minecraft.getMinecraft().player != null) {
               EntityPlayer player = e.getPlayer();
               Iterator var2 = Ability.getAbilitiesFromClass(Ability.getAbilities(player), AbilityAura.class).iterator();

               while(var2.hasNext()) {
                   AbilityAura abilityAura = (AbilityAura)var2.next();
                   if (abilityAura != null && abilityAura.isEnabled()) {
                       Color color = abilityAura.getDataManager().get(AbilityAura.COLOR);
                       float alpha = abilityAura.getDataManager().get(AbilityAura.ALPHA);
                       float playerScale = 1.0F;
                       ModelPlayer playerModel = new ModelPlayer(playerScale, PlayerHelper.hasSmallArms(player));
                       if (PlayerHelper.hasSmallArms(player)) {
                           e.getRenderPlayer().bindTexture(auraTexture[PlayerHelper.hasSmallArms(player) ? 1 : 0]);
                       }

                       GlStateManager.pushMatrix();
                       GlStateManager.enableBlend();
                       if (abilityAura.getDataManager().get(AbilityAura.GLOW)) {
                           LCRenderHelper.setLightmapTextureCoords(240.0F, 240.0F);
                           GlStateManager.disableLighting();
                       }

                       GlStateManager.color((float)(color.getRed() / 255), (float)(color.getGreen() / 255), (float)(color.getBlue() / 255), alpha);
                       playerModel.setModelAttributes(e.getRenderPlayer().getMainModel());
                       playerModel.render(player, e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
                       if (abilityAura.getDataManager().get(AbilityAura.GLOW)) {
                           LCRenderHelper.restoreLightmapTextureCoords();
                           GlStateManager.enableLighting();
                       }

                       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                       GlStateManager.disableBlend();
                       GlStateManager.popMatrix();
                   }
               }

           }
       }
   }
 }