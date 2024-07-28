 package spyeedy.mods.lcu.block;
 import lucraft.mods.lucraftcore.util.blocks.BlockBase;
 import net.minecraft.block.Block;
 import net.minecraft.block.BlockHorizontal;
 import net.minecraft.block.material.Material;
 import net.minecraft.block.properties.IProperty;
 import net.minecraft.block.properties.PropertyDirection;
 import net.minecraft.block.state.BlockFaceShape;
 import net.minecraft.block.state.BlockStateContainer;
 import net.minecraft.block.state.IBlockState;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.util.EnumFacing;
 import net.minecraft.util.EnumHand;
 import net.minecraft.util.math.AxisAlignedBB;
 import net.minecraft.util.math.BlockPos;
 import net.minecraft.world.IBlockAccess;
 import net.minecraft.world.World;
 import spyeedy.mods.lcu.LCUMod;
 
 public class BlockPowerBattery extends BlockBase {
   public static PropertyDirection FACING = BlockHorizontal.FACING;
   public static AxisAlignedBB BOUNDS = new AxisAlignedBB(0.28125D, 0.0D, 0.28125D, 0.71875D, 0.8125D, 0.71875D);
   
   public BlockPowerBattery(String name) {
     super(name + "_power_battery", Material.GROUND);
     
     setCreativeTab(LCUMod.tabLCU);
     setDefaultState(getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }
 
   
   public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 33 */     return 10;
   }
 
   
   public boolean isOpaqueCube(IBlockState state) {
/* 38 */     return false;
   }
 
   
   public boolean renderAsNormalBlock(IBlockState state) {
/* 43 */     return false;
   }
 
   
   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
/* 48 */     return BlockFaceShape.UNDEFINED;
   }
 
   
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 53 */     return BOUNDS;
   }
 
   
   public int getMetaFromState(IBlockState state) {
     return ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
   }
 
   
   public IBlockState getStateFromMeta(int meta) {
     return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
   }
 
   
   public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
/* 68 */     IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
/* 69 */     return state.withProperty(FACING, placer.getHorizontalFacing());
   }
 
   
   protected BlockStateContainer createBlockState() {
/* 74 */     return new BlockStateContainer(this, FACING);
   }
 }

