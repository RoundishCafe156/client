package cc.hyperium.cosmetics.companions.hamster;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class HamsterModel extends ModelBase {
    private ModelRenderer hamsterHeadMain;

    private ModelRenderer hamsterBody;

    private ModelRenderer hamsterLeg1;

    private ModelRenderer hamsterLeg2;

    private ModelRenderer hamsterLeg3;

    private ModelRenderer hamsterLeg4;

    private ModelRenderer hamsterMane;

    HamsterModel() {
        float f = 0.0F;

        hamsterHeadMain = new ModelRenderer(this, 0, 0);
        hamsterHeadMain.addBox(-3F, -3F, -2F, 6, 6, 4, 0);
        hamsterHeadMain.setRotationPoint(-1F, 13.5F, -7F);

        hamsterBody = new ModelRenderer(this, 18, 14);
        hamsterBody.addBox(-4F, -2F, -3F, 6, 9, 6, f);
        hamsterBody.setRotationPoint(0.0F, 14F, 2.0F);

        hamsterMane = new ModelRenderer(this, 21, 0);
        hamsterMane.addBox(-4F, -3F, -3F, 8, 6, 7, f);
        hamsterMane.setRotationPoint(-1F, 14F, 2.0F);

        hamsterLeg1 = new ModelRenderer(this, 0, 18);
        hamsterLeg1.addBox(-1F, 0.0F, -1F, 2, 4, 2, f + 0.5f);
        hamsterLeg1.setRotationPoint(-2.5F, 16F, 7F);

        hamsterLeg2 = new ModelRenderer(this, 0, 18);
        hamsterLeg2.addBox(-1F, 0.0F, -1F, 2, 4, 2, f + 0.5f);
        hamsterLeg2.setRotationPoint(0.5F, 16F, 7F);

        hamsterLeg3 = new ModelRenderer(this, 0, 18);
        hamsterLeg3.addBox(-1F, 0.0F, -1F, 2, 4, 2, f + 0.5f);
        hamsterLeg3.setRotationPoint(-2.5F, 16F, -4F);

        hamsterLeg4 = new ModelRenderer(this, 0, 18);
        hamsterLeg4.addBox(-1F, 0.0F, -1F, 2, 4, 2, f + 0.5f);
        hamsterLeg4.setRotationPoint(0.5F, 16F, -4F);

        hamsterHeadMain.setTextureOffset(16, 14).addBox(-3F, -5F, 0.0F, 2, 2, 1, 0);
        hamsterHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5F, 0.0F, 2, 2, 1, 0);
        hamsterHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -3F, 3, 3, 4, 0);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.2F, 0.0F);

        if (isChild) {
            float f = 2.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 5F * par7, 2.0F * par7);
            hamsterHeadMain.renderWithRotation(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f, 1.0F / f, 1.0F / f);
            GL11.glTranslatef(0.0F, 24F * par7, 0.0F);
            hamsterBody.render(par7);
            hamsterLeg1.render(par7);
            hamsterLeg2.render(par7);
            hamsterLeg3.render(par7);
            hamsterLeg4.render(par7);
            hamsterMane.render(par7);
            GL11.glPopMatrix();
        } else {
            hamsterHeadMain.renderWithRotation(par7);
            hamsterBody.render(par7);
            hamsterLeg1.render(par7);
            hamsterLeg2.render(par7);
            hamsterLeg3.render(par7);
            hamsterLeg4.render(par7);
            hamsterMane.render(par7);
        }

        GL11.glPopMatrix();
    }

    @Override
    public void setLivingAnimations(EntityLivingBase par1EntityLiving, float par2, float par3, float par4) {
        EntityHamster entityhamster = (EntityHamster) par1EntityLiving;

        if (entityhamster.isSitting()) {
            hamsterHeadMain.setRotationPoint(-1F, 11.5F, -7F);
            hamsterMane.setRotationPoint(-1F, 14F, -3F);
            hamsterMane.rotateAngleX = ((float) Math.PI * 2F / 5F);
            hamsterMane.rotateAngleY = 0.0F;
            hamsterBody.setRotationPoint(0.0F, 14F, 0.0F);
            hamsterBody.rotateAngleX = ((float) Math.PI * 0.3F);
            hamsterLeg1.setRotationPoint(-2.4F, 19F, 5.0F);
            hamsterLeg1.rotateAngleX = ((float) Math.PI * 3F / 2F);
            hamsterLeg2.setRotationPoint(0.4F, 19F, 5.0F);
            hamsterLeg2.rotateAngleX = ((float) Math.PI * 3F / 2F);
            hamsterLeg3.rotateAngleX = 0;
            hamsterLeg3.setRotationPoint(-2.49F, 16F, -4F);
            hamsterLeg4.rotateAngleX = 0;
            hamsterLeg4.setRotationPoint(0.51F, 16F, -4F);
        } else {
            hamsterHeadMain.setRotationPoint(-1F, 13.5F, -7F);
            hamsterBody.setRotationPoint(0.0F, 14F, 2.0F);
            hamsterBody.rotateAngleX = ((float) Math.PI / 2F);
            hamsterMane.setRotationPoint(-1F, 14F, -3F);
            hamsterMane.rotateAngleX = hamsterBody.rotateAngleX;
            hamsterLeg1.setRotationPoint(-2.5F, 16F, 7F);
            hamsterLeg2.setRotationPoint(0.5F, 16F, 7F);
            hamsterLeg3.setRotationPoint(-2.5F, 16F, -4F);
            hamsterLeg4.setRotationPoint(0.5F, 16F, -4F);
            hamsterLeg1.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
            hamsterLeg2.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float) Math.PI) * 1.4F * par3;
            hamsterLeg3.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float) Math.PI) * 1.4F * par3;
            hamsterLeg4.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);

        hamsterHeadMain.rotateAngleX = par5 / (180F / (float) Math.PI);
        hamsterHeadMain.rotateAngleY = par4 / (180F / (float) Math.PI);
    }
}
