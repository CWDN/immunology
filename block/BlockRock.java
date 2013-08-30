package piefarmer.immunology.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.tileentity.TileEntityRock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockRock extends BlockContainer{

	public BlockRock(int par1, Material par2Material) {
		super(par1, par2Material);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.6F, 1.0F);
	}
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
    {
		int item = par2Random.nextInt(3);
		switch(item)
		{
		case 0:
			return Block.cobblestone.blockID;
		case 1:
			return Item.coal.itemID;
		case 2:
			return Item.flint.itemID;
		default:
			return Block.cobblestone.blockID;
		}
			
    }
	@Override
	public int quantityDropped(Random par1Random)
    {
		return par1Random.nextInt(5);
    }
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for(int i = 0; i < count; i++)
        {
            int id = idDropped(metadata, world.rand, fortune);
            if (id > 0)
            {
                ret.add(new ItemStack(id, 1, damageDropped(metadata)));
            }
        }
        return ret;
    }

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	@Override
	public int getRenderType()
	{
		return -1;
	}
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityRock();
	}
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("stone");
    }
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		Random rand = new Random();
		int rotation = rand.nextInt(4);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, rotation, 2);
	}
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        Block soil = blocksList[par1World.getBlockId(par2, par3 - 1, par4)];
        return (soil != null && soil.grass.blockID == soil.blockID || soil != null &&  soil.blockID == soil.stone.blockID);
    }
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && canBlockStay(par1World, par2, par3, par4);
    }


}
