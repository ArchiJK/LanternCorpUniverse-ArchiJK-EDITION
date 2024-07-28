 package spyeedy.mods.lcu.gui;
 
 import com.google.common.collect.ImmutableList;
 import java.awt.Color;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;
 import lucraft.mods.lucraftcore.util.container.ContainerDummy;
 import lucraft.mods.lucraftcore.util.gui.buttons.GuiButton10x;
 import lucraft.mods.lucraftcore.util.helper.LCRenderHelper;
 import lucraft.mods.lucraftcore.util.helper.StringHelper;
 import net.minecraft.client.gui.GuiButton;
 import net.minecraft.client.gui.inventory.GuiContainer;
 import net.minecraft.inventory.Container;
 import net.minecraft.util.ResourceLocation;
 import spyeedy.mods.lcu.abilities.AbilityProjectiles;
 import spyeedy.mods.lcu.addonpacks.entity.AddonPackEntityReader;
 import spyeedy.mods.lcu.addonpacks.entity.JsonProjectileInfo;
 
 public class GuiProjectiles
   extends GuiContainer
 {
  public static final ResourceLocation TEX = new ResourceLocation("spylcu", "textures/gui/construct_selector.png");
   
  public int selectedEntityId = -1;
   
   private int mouseX;
   
   private int mouseY;
   private GuiProjectilesList guiList;
   private GuiButton btnHelp;
   public AbilityProjectiles ability;
   public List<String> entityIdList;
   
   public GuiProjectiles(AbilityProjectiles abilityProjectiles) {
    super(new ContainerDummy());
     
    this.ability = abilityProjectiles;
    this.entityIdList = ImmutableList.copyOf(abilityProjectiles.getEntityIds());
   }
 
   
   public void initGui() {
    super.initGui();
    this.xSize = 192;
    this.ySize = 136;
     
    int x = (this.width - this.xSize) / 2;
    int y = (this.height - this.ySize) / 2;
     
    this.guiList = new GuiProjectilesList(this.mc, this);
     
    this.btnHelp = new GuiButton10x(0, x + 21 + 151 + 3, y + this.ySize / 2 - 5, "?");
    this.buttonList.add(this.btnHelp);
   }
 
   
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    drawDefaultBackground();
     
    int x = (this.width - this.xSize) / 2;
    int y = (this.height - this.ySize) / 2;
     
    this.mc.getTextureManager().bindTexture(TEX);
    drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
 
     
    String guiTitle = StringHelper.translateToLocal("spylcu.gui.title.projectiles");
    int guiTitleWidth = this.mc.fontRenderer.getStringWidth(guiTitle);
    this.mc.fontRenderer.drawString(guiTitle, x + this.xSize / 2 - guiTitleWidth / 2 + 1, y + 11, 0);
    this.mc.fontRenderer.drawString(guiTitle, x + this.xSize / 2 - guiTitleWidth / 2, y + 10, Color.WHITE.getRGB());
 
     
    if (this.guiList != null) {
      this.guiList.drawScreen(mouseX, mouseY, partialTicks);
     }
     
    super.drawScreen(mouseX, mouseY, partialTicks);
 
     
    if (this.guiList != null &&
      mouseX >= this.btnHelp.x && mouseX <= this.btnHelp.x + this.btnHelp.width && mouseY >= this.btnHelp.y && mouseY <= this.btnHelp.y + this.btnHelp.height && this.selectedEntityId >= 0) {
      String entityId = this.ability.getEntityIds().get(this.selectedEntityId);
      String modid = entityId.split(":")[0];
      JsonProjectileInfo info = AddonPackEntityReader.PROJECTILES.get(entityId);
       
      String desc = StringHelper.translateToLocal(modid + ".entity." + info.getEntityName() + ".desc");
       
      List<String> list = new ArrayList<>();
      for (String s : desc.split("\n")) {
        for (String s2 : this.mc.fontRenderer.listFormattedStringToWidth(s, 130)) {
          list.add(s2);
         }
       } 
       
      LCRenderHelper.drawStringList(list, mouseX - 130, mouseY, true);
     } 
 
     
/* 100 */     this.mouseX = mouseX;
/* 101 */     this.mouseY = mouseY;
   }
 
   
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {}
 
   
   public void handleMouseInput() throws IOException {
/* 109 */     super.handleMouseInput();
/* 110 */     if (this.guiList != null) {
/* 111 */       this.guiList.handleMouseInput(this.mouseX, this.mouseY);
     }
   }
 
   
   public boolean doesGuiPauseGame() {
/* 117 */     return false;
   }
 }

