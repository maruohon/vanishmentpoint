package fi.dy.masa.vanishmentpoint.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import fi.dy.masa.vanishmentpoint.config.Configs;

public class BlockVanishingBlockTime extends Block
{
    public BlockVanishingBlockTime(Material materialIn)
    {
        super(materialIn);

        this.setHardness(1.5F);
        this.setResistance(10F);
        this.setHarvestLevel("pickaxe", 1);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.isRemote == false)
        {
            float adjustedAngle = getAdjustedAngle(world.getCelestialAngle(0));

            if (adjustedAngle >= Configs.vanishingBlockVanishingTimeStart &&
                adjustedAngle < Configs.vanishingBlockVanishingTimeEnd &&
                rand.nextFloat() < Configs.vanishingBlockVanishingChance)
            {
                world.setBlockToAir(pos);
            }
        }
    }

    public static float getAdjustedAngle(float celestialAngle)
    {
        float adjustedAngle = celestialAngle + (float) Configs.celestialAngleOffset;

        if (adjustedAngle < 0)
        {
            adjustedAngle += 1f;
        }
        else if (adjustedAngle >= 1.0f)
        {
            adjustedAngle -= 1f;
        }

        return adjustedAngle;
    }
}
