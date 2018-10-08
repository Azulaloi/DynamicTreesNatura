package dtnatura.items;

import jline.internal.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
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

            //TODO: make this floaty boy go up when over heat sources
            //Nether ecology 101: Adaptation to the infernal environment
            //The bloodwood tree, Inferae sanguinatum, has evolved to utilize the
            //nether's chaotic thermal drafts as a method of spreading over great distances.
            //Much like a dandelion, bloodleaf seeds are lightweight with fluffy "parachutes"
            //that produce drag. The large magma basins, lava floes, and rackfires present in
            //the nether all produce heat, and therefore rising air - which the bloodleaf seed
            //will ride until it sticks to the cave ceiling, where it sprout and burrow into the
            //soft infernal stone.

            motionY += 0.039;
            //motionY += motionY < 0 ? 0.0385 : 0.036;

            super.onUpdate();
        }

        @Override
        public void setDefaultPickupDelay() {
            this.setPickupDelay(50); //Somehow this goes missing when turned into an entity
        }
    }
}
