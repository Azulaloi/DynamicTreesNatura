package com.github.azulaloi.dtnatura.items;

import jline.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBloodSeed extends SeedInverse {

    public ItemBloodSeed(String name) {
        super(name);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    @Nullable
    public Entity createEntity(World world, Entity entity, ItemStack itemStack) {
        EntityItemBloodSeed bloodSeedEntity = new EntityItemBloodSeed(world, entity.posX, entity.posY, entity.posZ, itemStack);

        bloodSeedEntity.motionX = entity.motionX;
        bloodSeedEntity.motionY = entity.motionY;
        bloodSeedEntity.motionZ = entity.motionZ;

        return bloodSeedEntity;
    }

    public static class EntityItemBloodSeed extends EntityItem {

        public EntityItemBloodSeed(World world) {
            super(world);
            this.setDefaultPickupDelay();
        }

        public EntityItemBloodSeed(World world, double x, double y, double z, ItemStack stack) {
            super(world, x, y, z, stack);
            this.setDefaultPickupDelay();
        }

        @Override
        public void onUpdate() {

            //Nether Ecology 101: Adaptation to the Infernal Environment
            //  The bloodwood tree, Inferae sanguinatum, has evolved to utilize the nether's
            //  chaotic thermal drafts as a method of spreading over great distances. Much like
            //  a dandelion, bloodleaf seeds are lightweight with fluffy "parachutes" that catch
            //  the wind. The large magma basins, lava floes, and rackfires present in the nether
            //  all produce heat, and therefore rising air - which the bloodleaf seed will ride until
            //  it sticks to the cave ceiling, where it sprout and burrow into the soft infernal stone.

            BlockPos pos = this.getPosition();
            World world = this.world;

            //glide
            motionY += 0.039;

            //100 should be enough to germinate roof of nether oceans
            for (int i = 0; i < 100; i++) { //check performance on this
                Block block = world.getBlockState(pos.down(i)).getBlock();
                if (block.equals(Blocks.FIRE) || block.equals(Blocks.LAVA) || block.equals(Blocks.FLOWING_LAVA)) {
                    addVelocity(0, 0.005, 0);
                    //motionY *= 1.005; //TODO: scale to distance of heat?
                    break;
                } else if (!block.equals(Blocks.AIR)) {
                    break;
                }
            }


            //Superclass (Seed) plants properly upside-down

            super.onUpdate();
        }

        @Override
        public void setDefaultPickupDelay() {
            this.setPickupDelay(50); //Somehow this goes missing when turned into an entity
        }
    }
}
