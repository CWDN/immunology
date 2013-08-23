package piefarmer.immunology.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenRocks extends WorldGenerator{

	private int blockId;

    public WorldGenRocks(int par1)
    {
        this.blockId = par1;
    }
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int var6 = 0; var6 < 16; ++var6)
        {
            int var7 = i + random.nextInt(16);
            int var8 = j + random.nextInt(16);
            int var9 = k + random.nextInt(16);

            if (world.isAirBlock(var7, var8, var9) && (!world.provider.hasNoSky || var8 < 127) && Block.blocksList[this.blockId].canBlockStay(world, var7, var8, var9))
            {
                world.setBlock(var7, var8, var9, this.blockId);
            }
        }

        return true;
	}

}
