package fi.dy.masa.vanishmentpoint.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import fi.dy.masa.vanishmentpoint.block.VanishmentPointBlocks;

@Mod.EventBusSubscriber(Side.CLIENT)
public class Models
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        registerItemBlockModels();
    }

    private static void registerItemBlockModels()
    {
        registerItemBlockModel(VanishmentPointBlocks.VANISHING_BLOCK_TIME, 0, "inventory");
    }

    private static void registerItemBlockModel(Block block, int meta, String fullVariant)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
            new ModelResourceLocation(block.getRegistryName(), fullVariant));
    }
}
