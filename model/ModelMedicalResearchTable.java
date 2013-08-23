// Date: 26/04/2013 16:46:36
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package piefarmer.immunology.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMedicalResearchTable extends ModelBase
{
  //fields
    ModelRenderer TableLeft;
    ModelRenderer TableRight;
    ModelRenderer TableTop;
    ModelRenderer TableMid;
    ModelRenderer BookMid;
    ModelRenderer RackBack;
    ModelRenderer RackStand1;
    ModelRenderer RackStand2;
    ModelRenderer RackStand4;
    ModelRenderer RackStand7;
    ModelRenderer RackStand9;
    ModelRenderer RackStand10;
    ModelRenderer RackStandFront;
    ModelRenderer RackBottom;
    ModelRenderer TestTube1;
    ModelRenderer TestTube2;
    ModelRenderer TestTube3;
    ModelRenderer TestTube4;
    ModelRenderer TestTube5;
  
  public ModelMedicalResearchTable()
  {
    textureWidth = 96;
    textureHeight = 32;
    
      TableLeft = new ModelRenderer(this, 64, 0);
      TableLeft.addBox(0F, 0F, 0F, 1, 14, 14);
      TableLeft.setRotationPoint(6F, 10F, -7F);
      TableLeft.setTextureSize(96, 32);
      TableLeft.mirror = true;
      setRotation(TableLeft, 0F, 0F, 0F);
      TableRight = new ModelRenderer(this, 65, 0);
      TableRight.addBox(0F, 0F, 0F, 1, 14, 14);
      TableRight.setRotationPoint(-7F, 10F, -7F);
      TableRight.setTextureSize(96, 32);
      TableRight.mirror = true;
      setRotation(TableRight, 0F, 0F, 0F);
      TableTop = new ModelRenderer(this, 0, 0);
      TableTop.addBox(0F, 0F, 0F, 16, 1, 16);
      TableTop.setRotationPoint(-8F, 9F, -8F);
      TableTop.setTextureSize(96, 32);
      TableTop.mirror = true;
      setRotation(TableTop, 0F, 0F, 0F);
      TableMid = new ModelRenderer(this, 0, 17);
      TableMid.addBox(0F, 0F, 0F, 12, 1, 4);
      TableMid.setRotationPoint(-6F, 16F, -2F);
      TableMid.setTextureSize(96, 32);
      TableMid.mirror = true;
      setRotation(TableMid, 0F, 0F, 0F);
      BookMid = new ModelRenderer(this, 32, 17);
      BookMid.addBox(0F, 0F, 0F, 4, 1, 6);
      BookMid.setRotationPoint(0F, 8F, -3F);
      BookMid.setTextureSize(96, 32);
      BookMid.mirror = true;
      setRotation(BookMid, 0F, 0.669215F, 0F);
      RackBack = new ModelRenderer(this, 0, 22);
      RackBack.addBox(0F, 0F, 0F, 11, 7, 1);
      RackBack.setRotationPoint(-7F, 2F, 6F);
      RackBack.setTextureSize(96, 32);
      RackBack.mirror = true;
      setRotation(RackBack, 0F, 0F, 0F);
      RackStand1 = new ModelRenderer(this, 52, 17);
      RackStand1.addBox(0F, 0F, 0F, 1, 1, 2);
      RackStand1.setRotationPoint(-7F, 4F, 4F);
      RackStand1.setTextureSize(96, 32);
      RackStand1.mirror = true;
      setRotation(RackStand1, 0F, 0F, 0F);
      RackStand2 = new ModelRenderer(this, 52, 17);
      RackStand2.addBox(0F, 0F, 0F, 1, 1, 2);
      RackStand2.setRotationPoint(-5F, 4F, 4F);
      RackStand2.setTextureSize(96, 32);
      RackStand2.mirror = true;
      setRotation(RackStand2, 0F, 0F, 0F);
      RackStand4 = new ModelRenderer(this, 52, 17);
      RackStand4.addBox(0F, 0F, 0F, 1, 1, 2);
      RackStand4.setRotationPoint(-3F, 4F, 4F);
      RackStand4.setTextureSize(96, 32);
      RackStand4.mirror = true;
      setRotation(RackStand4, 0F, 0F, 0F);
      RackStand7 = new ModelRenderer(this, 52, 17);
      RackStand7.addBox(0F, 0F, 0F, 1, 1, 2);
      RackStand7.setRotationPoint(-1F, 4F, 4F);
      RackStand7.setTextureSize(96, 32);
      RackStand7.mirror = true;
      setRotation(RackStand7, 0F, 0F, 0F);
      RackStand9 = new ModelRenderer(this, 52, 17);
      RackStand9.addBox(0F, 0F, 0F, 1, 1, 2);
      RackStand9.setRotationPoint(1F, 4F, 4F);
      RackStand9.setTextureSize(96, 32);
      RackStand9.mirror = true;
      setRotation(RackStand9, 0F, 0F, 0F);
      RackStand10 = new ModelRenderer(this, 52, 17);
      RackStand10.addBox(0F, 0F, 0F, 1, 1, 2);
      RackStand10.setRotationPoint(3F, 4F, 4F);
      RackStand10.setTextureSize(96, 32);
      RackStand10.mirror = true;
      setRotation(RackStand10, 0F, 0F, 0F);
      RackStandFront = new ModelRenderer(this, 24, 28);
      RackStandFront.addBox(0F, 0F, 0F, 11, 1, 1);
      RackStandFront.setRotationPoint(-7F, 4F, 3F);
      RackStandFront.setTextureSize(96, 32);
      RackStandFront.mirror = true;
      setRotation(RackStandFront, 0F, 0F, 0F);
      RackBottom = new ModelRenderer(this, 24, 24);
      RackBottom.addBox(0F, 0F, 0F, 11, 1, 3);
      RackBottom.setRotationPoint(-7F, 8.5F, 3F);
      RackBottom.setTextureSize(96, 32);
      RackBottom.mirror = true;
      setRotation(RackBottom, 0F, 0F, 0F);
      TestTube1 = new ModelRenderer(this, 0, 0);
      TestTube1.addBox(0F, 0F, 0F, 1, 5, 1);
      TestTube1.setRotationPoint(-6F, 3F, 4F);
      TestTube1.setTextureSize(96, 32);
      TestTube1.mirror = true;
      setRotation(TestTube1, 0F, 0F, 0F);
      TestTube2 = new ModelRenderer(this, 4, 0);
      TestTube2.addBox(0F, 0F, 0F, 1, 5, 1);
      TestTube2.setRotationPoint(-4F, 3F, 4F);
      TestTube2.setTextureSize(96, 32);
      TestTube2.mirror = true;
      setRotation(TestTube2, 0F, 0F, 0F);
      TestTube3 = new ModelRenderer(this, 8, 0);
      TestTube3.addBox(0F, 0F, 0F, 1, 5, 1);
      TestTube3.setRotationPoint(-2F, 3F, 4F);
      TestTube3.setTextureSize(96, 32);
      TestTube3.mirror = true;
      setRotation(TestTube3, 0F, 0F, 0F);
      TestTube4 = new ModelRenderer(this, 12, 6);
      TestTube4.addBox(0F, 0F, 0F, 1, 5, 1);
      TestTube4.setRotationPoint(0F, 3F, 4F);
      TestTube4.setTextureSize(96, 32);
      TestTube4.mirror = true;
      setRotation(TestTube4, 0F, 0F, 0F);
      TestTube5 = new ModelRenderer(this, 12, 0);
      TestTube5.addBox(0F, 0F, 0F, 1, 5, 1);
      TestTube5.setRotationPoint(2F, 3F, 4F);
      TestTube5.setTextureSize(96, 32);
      TestTube5.mirror = true;
      setRotation(TestTube5, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    TableLeft.render(f5);
    TableRight.render(f5);
    TableTop.render(f5);
    TableMid.render(f5);
    BookMid.render(f5);
    RackBack.render(f5);
    RackStand1.render(f5);
    RackStand2.render(f5);
    RackStand4.render(f5);
    RackStand7.render(f5);
    RackStand9.render(f5);
    RackStand10.render(f5);
    RackStandFront.render(f5);
    RackBottom.render(f5);
    TestTube1.render(f5);
    TestTube2.render(f5);
    TestTube3.render(f5);
    TestTube4.render(f5);
    TestTube5.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
  public void renderAll()
  {
	  TableLeft.render(0.0625F);
	  TableRight.render(0.0625F);
	  TableTop.render(0.0625F);
	  TableMid.render(0.0625F);
	  BookMid.render(0.0625F);
	  RackBack.render(0.0625F);
	  RackStand1.render(0.0625F);
	  RackStand2.render(0.0625F);
	  RackStand4.render(0.0625F);
	  RackStand7.render(0.0625F);
	  RackStand9.render(0.0625F);
	  RackStand10.render(0.0625F);
	  RackStandFront.render(0.0625F);
	  RackBottom.render(0.0625F);
	  TestTube1.render(0.0625F);
	  TestTube2.render(0.0625F);
	  TestTube3.render(0.0625F);
	  TestTube4.render(0.0625F);
	  TestTube5.render(0.0625F);
  }

}