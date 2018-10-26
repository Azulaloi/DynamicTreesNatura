package com.github.azulaloi.dtnatura.items;

import com.ferreusveritas.dynamictrees.items.Seed;
import jline.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMapleSeed extends Seed {

    public ItemMapleSeed(String name) {
        super(name);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    @Nullable
    public Entity createEntity(World world, Entity entity, ItemStack itemStack) {
        EntityItemMapleSeed mapleSeedEntity = new EntityItemMapleSeed(world, entity.posX, entity.posY, entity.posZ, itemStack);

        mapleSeedEntity.motionX = entity.motionX;
        mapleSeedEntity.motionY = entity.motionY;
        mapleSeedEntity.motionZ = entity.motionZ;

        return mapleSeedEntity;
    }

    public static class EntityItemMapleSeed extends EntityItem {

        public EntityItemMapleSeed(World world) {
            super(world);
            this.setDefaultPickupDelay();
        }

        public EntityItemMapleSeed(World world, double x, double y, double z, ItemStack stack) {
            super(world, x, y, z, stack);
            this.setDefaultPickupDelay();
        }

        @Override
        public void onUpdate() {

            BlockPos pos = this.getPosition();
            World world = this.world;

            motionX *= 1.01f; //counteract item drag
            motionZ *= 1.01f;

            if (motionY < 0) {
                motionY += 0.039;
                                  // go faster while falling
                motionX *= 1.01f; // it will probably hit the ground before breaking sound barrier
                motionZ *= 1.01f; // ...probably
            }

            super.onUpdate();
        }

        @Override
        public void setDefaultPickupDelay() {
            this.setPickupDelay(50); // Somehow this goes missing when turned into an entity
        }
    }
}
