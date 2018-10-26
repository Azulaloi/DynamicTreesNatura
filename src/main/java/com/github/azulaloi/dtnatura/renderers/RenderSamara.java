package com.github.azulaloi.dtnatura.renderers;

import com.github.azulaloi.dtnatura.items.ItemMapleSeed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderSamara extends Render<ItemMapleSeed.EntityItemMapleSeed> {

    private final RenderItem itemRenderer;
    private Render<Entity> vanillaEntityItemRenderer;

    public RenderSamara(RenderManager renderManager, RenderItem renderItem) {
        super(renderManager);
        this.itemRenderer = renderItem;
        this.vanillaEntityItemRenderer = renderManager.getEntityClassRenderObject(EntityItem.class);
    }

    @Override
    public void doRender(ItemMapleSeed.EntityItemMapleSeed entity, double x, double y, double z, float yawIn, float ticks) {

        if(entity.onGround) {
            vanillaEntityItemRenderer.doRender(entity, x, y, z, yawIn, ticks);
            return;
        }

        ItemStack itemStack = entity.getItem();
        boolean textureMip = false;

        if (this.bindEntityTexture(entity)) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            textureMip = true;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.pushMatrix();
        IBakedModel iBakedModel = this.itemRenderer.getItemModelWithOverrides(itemStack, entity.world, (EntityLivingBase) null);

        GlStateManager.translate(x, y + 0.03125f, z);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        //A real samara rotates at 78 rad/s
        //With a cone angle of 64 degrees
        //Tilting angle is almost 0

        if (entity.motionY < 0) {
            float rotateValue = (entity.getAge() + ticks) / 0.4f; //not sure how to calculate 78 rad/s but nobody can tell
            float cycle = (rotateValue + entity.hoverStart) * (180F / (float)Math.PI);

            GlStateManager.rotate(cycle, 0.0F, 1.0F, 0.0F); // spin me right round
            GlStateManager.translate(-0.1, 0, 0.0); //move center of rotation after spinning or it will rotate around translated center
            GlStateManager.rotate((float) 64f, 1f, 0, 0); //tilt 64 degrees
        } else if (!entity.onGround) { //flail until stabilize?
            float rotateValue = (entity.getAge() + ticks) * 0.1f;
            float cycle = (rotateValue + entity.hoverStart) * (180F / (float)Math.PI);
            GlStateManager.rotate(cycle, 0.5F, .5F, 0.5F);
        }

        IBakedModel model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(iBakedModel, ItemCameraTransforms.TransformType.GROUND, false);
        this.itemRenderer.renderItem(itemStack, model);

        if (!iBakedModel.isGui3d()) {
            GlStateManager.translate(0.0F, 0.0F, 0.09375F);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);

        if (textureMip) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
        }

        super.doRender(entity, x, y, z, yawIn, ticks);
    }

    public static class Factory implements IRenderFactory<ItemMapleSeed.EntityItemMapleSeed> {
        @Override
        public Render<ItemMapleSeed.EntityItemMapleSeed> createRenderFor(RenderManager man) {
            return new RenderSamara(man, Minecraft.getMinecraft().getRenderItem());
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(ItemMapleSeed.EntityItemMapleSeed entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

}
