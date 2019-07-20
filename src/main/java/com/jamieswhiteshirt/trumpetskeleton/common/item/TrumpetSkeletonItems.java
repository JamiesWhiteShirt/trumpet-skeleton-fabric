package com.jamieswhiteshirt.trumpetskeleton.common.item;

import com.jamieswhiteshirt.trumpetskeleton.common.entity.TrumpetSkeletonEntityTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TrumpetSkeletonItems {
    public static final Item TRUMPET = register("trumpet", new TrumpetItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1).maxDamage(200)));
    public static final Item TRUMPET_SKELETON_SPAWN_EGG = register("trumpet_skeleton_spawn_egg", new SpawnEggItem(TrumpetSkeletonEntityTypes.TRUMPET_SKELETON, 0xC1C1C1, 0xFCFC00, (new Item.Settings().group(ItemGroup.MISC))));

    private static Item register(String id, Item item) {
        return register(new Identifier("trumpet-skeleton", id), item);
    }

    private static Item register(Identifier id, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registry.ITEM, id, item);
    }

    public static void init() { }
}
