package piefarmer.immunology.block;

import java.util.Random;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import piefarmer.immunology.client.ClientProxy;
import piefarmer.immunology.common.*;
import piefarmer.immunology.gui.ContainerMedicalResearchTable;
import piefarmer.immunology.lib.Names;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTable;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTableRenderer;

public class BlockMedicalResearchTable extends BlockContainer{

	Random rand = new Random();
	public BlockMedicalResearchTable(int par1) {
		super(par1, Material.iron);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.935F, 1.0F);
		this.setCreativeTab(Immunology.tabImmunology);
	
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
        this.blockIcon = par1IconRegister.registerIcon(Immunology.modid + ":" + Names.medresBlock_unlocalizedName);
    }
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack par6ItemStack)
	{
		int rotation = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
	}
	public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityMedicalResearchTable();
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float a, float b, float c)
	{
		TileEntityMedicalResearchTable tileEntity = (TileEntityMedicalResearchTable)world.getBlockTileEntity(x, y, z);
		if(tileEntity == null || player.isSneaking())
		{
			return false;
		}
		player.openGui(Immunology.instance, 0, world, x, y, z);
		return true;
	
	}
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6){
		
        

        TileEntityMedicalResearchTable tileentitytbl = (TileEntityMedicalResearchTable)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitytbl != null)
        {
            for (int j1 = 0; j1 < tileentitytbl.getSizeInventory(); ++j1)
            {
                ItemStack itemstack = tileentitytbl.getStackInSlot(j1);

                if (itemstack != null)
                {
                    float f = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
                    {
                        int k1 = this.rand.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                        {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            par1World.func_96440_m(par2, par3, par4, par5);
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        
}






}
