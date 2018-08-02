package fi.dy.masa.vanishmentpoint.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import fi.dy.masa.vanishmentpoint.config.Configs;

public class BlockVanishingBlockTime extends Block
{
    public BlockVanishingBlockTime(Material materialIn)
    {
        super(materialIn);

        this.fullBlock = true;
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

                if (Configs.scheduleNeighbors)
                {
                    for (EnumFacing side : EnumFacing.values())
                    {
                        BlockPos posSide = pos.offset(side);

                        if (world.getBlockState(posSide).getBlock() == this)
                        {
                            int delay = rand.nextInt(20) + rand.nextInt(20);
                            world.scheduleBlockUpdate(posSide, this, delay, 0);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Configs.solidOnTopOnly == false;
    }

    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return Configs.solidOnTopOnly == false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state)
    {
        return Configs.solidOnTopOnly == false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return Configs.solidOnTopOnly == false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return Configs.solidOnTopOnly;
    }

    @Override
    public boolean causesSuffocation(IBlockState state)
    {
        return Configs.solidOnTopOnly == false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
            List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        if (Configs.solidOnTopOnly == false || (entityIn != null && entityIn.posY >= (pos.getY() + 1)))
        {
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
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
