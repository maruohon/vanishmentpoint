package fi.dy.masa.vanishmentpoint.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import fi.dy.masa.vanishmentpoint.reference.Reference;
import fi.dy.masa.vanishmentpoint.reference.ReferenceNames;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class VanishmentPointBlocks
{
    public static final Block VANISHING_BLOCK_TIME = new BlockVanishingBlockTime(Material.ROCK);

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();

        registerBlock(registry, VANISHING_BLOCK_TIME, ReferenceNames.NAME_BLOCK_VANISHING_BLOCK_TIME);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        registerItemBlock(registry, VANISHING_BLOCK_TIME, ReferenceNames.NAME_BLOCK_VANISHING_BLOCK_TIME);
    }

    private static void registerBlock(IForgeRegistry<Block> registry, Block block, String name)
    {
        block.setRegistryName(Reference.MOD_ID + ":" + name).setTranslationKey(Reference.MOD_ID + "." + name);
        registry.register(block);
    }

    private static void registerItemBlock(IForgeRegistry<Item> registry, Block block, String name)
    {
        Item item = new ItemBlock(block);
        item.setRegistryName(Reference.MOD_ID + ":" + name).setTranslationKey(Reference.MOD_ID + "." + name);
        registry.register(item);
    }
}
