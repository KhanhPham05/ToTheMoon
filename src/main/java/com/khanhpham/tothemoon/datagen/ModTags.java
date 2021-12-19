package com.khanhpham.tothemoon.datagen;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.util.Identity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public final class ModTags {
    private static final String modid = ToTheMoon.ID;

    // -------------------------------    ITEMS
        // GENERAL - done
    public static final IOptionalNamedTag<Item> ingots = Tags.Items.INGOTS;
    public static final IOptionalNamedTag<Item> plate = modItem("forge", "plates");
    public static final IOptionalNamedTag<Item> dust = Tags.Items.DUSTS;
    public static final IOptionalNamedTag<Item> nuggets = Tags.Items.NUGGETS;
    public static final IOptionalNamedTag<Item> oreBlockItems = Tags.Items.ORES;
    public static final IOptionalNamedTag<Item> storageBlockItems = Tags.Items.STORAGE_BLOCKS;
    public static final IOptionalNamedTag<Item> moonOreItem = modItem(modid, "moon_ores");


        //copper - done
    public static final IOptionalNamedTag<Item> moonOreCopperItem = modItem(modid, "moon_ores/copper");
    public static final IOptionalNamedTag<Item> oreCopperItem = item("ores/copper");
    public static final IOptionalNamedTag<Item> storageCopperItem =item( "storage_blocks/copper");
    public static final IOptionalNamedTag<Item> ingotCopper = item("ingots/copper");
    public static final IOptionalNamedTag<Item> dustCopper = item("dusts/copper");
    public static final IOptionalNamedTag<Item> plateCopper = item("plates/copper");
    public static final IOptionalNamedTag<Item> nuggetCopper = item("nuggets/copper");

        //steel - done
    public static final IOptionalNamedTag<Item> ingotSteel = item("ingots/steel");
    public static final IOptionalNamedTag<Item> dustSteel = item("dusts/steel");
    public static final IOptionalNamedTag<Item> storageSteelItem = item("storage_blocks/steel");
    public static final IOptionalNamedTag<Item> plateSteel = item("plates/steel");
    public static final IOptionalNamedTag<Item> nuggetSteel = item("nugget/steel");

        //redstone steel alloy - done
    public static final IOptionalNamedTag<Item> dustRedstoneSteel = item("dusts/redstone_steel");
    public static final IOptionalNamedTag<Item> ingotRedstoneSteel = item("ingots/redstone_steel");
    public static final IOptionalNamedTag<Item> storageRedstoneSteelItem = item("storage_blocks/redstone_steel");
    public static final IOptionalNamedTag<Item> plateRedstoneSteel = item("plates/redstone_steel");
    public static final IOptionalNamedTag<Item> nuggetRedstoneSteel = item("nuggets/redstone_steel");


    // -------------------------------  BLOCKS
        //general - done
    public static final IOptionalNamedTag<Block> oreBlocks = Tags.Blocks.ORES;
    public static final IOptionalNamedTag<Block> storageBlocks = Tags.Blocks.STORAGE_BLOCKS;
    public static final IOptionalNamedTag<Block> moonOreBlocks = mod(modid, "moon_ores");

    public static final IOptionalNamedTag<Block> oreCopper = block("ores/copper");
    public static final IOptionalNamedTag<Block> moonOreCopper = mod(modid, "moon_ores/copper");

    public static final IOptionalNamedTag<Block> storageCopper = block("storage_blocks/copper");
    public static final IOptionalNamedTag<Block> storageSteel = block("storage_blocks/steel");
    public static final IOptionalNamedTag<Block> storageRedstoneSteel = block("storage_blocks/redstone_steel");

    //public static final IOptionalNamedTag<Block> energyCapable = mod(ToTheMoon.ID, "energy_capable");
    public static final IOptionalNamedTag<Block> energyReceive = mod(ToTheMoon.ID, "energy_receive");
    public static final IOptionalNamedTag<Block> energyTransferable = mod(ToTheMoon.ID, "energy_transferable");

    private static IOptionalNamedTag<Block> block(String name) {
        ensureValidTag(name);
        return mod("forge", name);
    }

    private static IOptionalNamedTag<Block> mod(String modid, String name) {

        return BlockTags.createOptional(Identity.of(modid, name));
    }

    private static IOptionalNamedTag<Item> item(String name) {
        ensureValidTag(name);
        return modItem("forge", name);
    }

    private static IOptionalNamedTag<Item> modItem(String modid, String name) {
        return ItemTags.createOptional(Identity.of(modid, name));
    }

    private static void ensureValidTag(String tag) {
        int endPoint = tag.length() - 1;
        if (tag.charAt(endPoint - 1) == '/') throw new IllegalArgumentException();

        if (tag.contains(":") || tag.contains(" ") || tag.contains(".") || !tag.contains("/")) throw new IllegalArgumentException();
    }
}
