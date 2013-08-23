package piefarmer.immunology.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.tileentity.TileEntityDiagnosticTable;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTable;

public class BlockDiagnosticTable extends BlockContainer{

	public BlockDiagnosticTable(int par1) {
		super(par1, Material.iron);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.935F, 1.0F);
	
	}
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}
	public int getRenderType()
	{
		return -1;
	}
	public int getRenderBlockPass()
    {
        return 1;
    }
	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(Immunology.modid + ":" + this.getUnlocalizedName2());
    }
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack par6ItemStack)
	{
		int rotation = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 2.5D) & 3;
		world.setBlock(i, j, k, this.blockID, rotation, 2);
	}
	public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityDiagnosticTable();
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float a, float b, float c)
	{
		TileEntityDiagnosticTable tileEntity = (TileEntityDiagnosticTable)world.getBlockTileEntity(x, y, z);
		if(tileEntity == null || player.isSneaking())
		{
			return false;
		}
		player.openGui(Immunology.instance, 1, world, x, y, z);
		return true;
	
	}
	@Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
            super.breakBlock(world, x, y, z, par5, par6);
    }
}
