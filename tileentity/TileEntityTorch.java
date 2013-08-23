package piefarmer.immunology.tileentity;

import java.util.Random;

import piefarmer.immunology.common.Immunology;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTorch extends TileEntity{
	
	private int timeLit = 0;
	public TileEntityTorch()
	{
	}
	public TileEntityTorch(boolean isLit){
		if(isLit)
		{
			Random rand = new Random();
			this.timeLit = 6000 + rand.nextInt(3000);
		}
		else
		{
			this.timeLit = -1;
		}
	}
	@Override
	public void updateEntity()
	{
		if(this.timeLit > 0)
		{
			this.timeLit--;
		}
		else if (this.timeLit == 0)
		{
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Immunology.torchWoodNotLit.blockID, this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord), 3);
		}
	}

}
