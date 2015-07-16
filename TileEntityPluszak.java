package pl.pokemine.bax.forge.mods.pluszaki.blocks;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import pl.pokemine.bax.forge.mods.pluszaki.PokeMinePluszaki;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class TileEntityPluszak extends TileEntityWrapper {

	private String id;
	private float offsetX, offsetY, offsetZ;
	private float scaleX, scaleY, scaleZ;
	private float obrot;
	private float osX, osY, osZ;
	private float rotateX, rotateY, rotateZ, rotateV;
	
	public TileEntityPluszak() {
		super();
	}
	
	protected TileEntityPluszak(String ajdi,
			float offsetX, float offsetY, float offsetZ,
			float scaleX, float scaleY, float scaleZ,
			float obrot,
			float osX, float osY, float osZ,
			float rotateX, float rotateY, float rotateZ, float rotateV) {
		this();
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		this.obrot = obrot;
		this.osX = osX;
		this.osY = osY;
		this.osZ = osZ;
		this.rotateX = rotateX;
		this.rotateY = rotateY;
		this.rotateZ = rotateZ;
		this.rotateV = rotateV;
		id = ajdi;
		setupModel();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("offsetX", offsetX);
		nbt.setFloat("offsetY", offsetY);
		nbt.setFloat("offsetZ", offsetZ);
		nbt.setFloat("scaleX", scaleX);
		nbt.setFloat("scaleY", scaleY);
		nbt.setFloat("scaleZ", scaleZ);
		nbt.setFloat("obrot", obrot);
		nbt.setFloat("osX", osX);
		nbt.setFloat("osY", osY);
		nbt.setFloat("osZ", osZ);
		nbt.setFloat("rotateX", rotateX);
		nbt.setFloat("rotateY", rotateY);
		nbt.setFloat("rotateZ", rotateZ);
		nbt.setFloat("rotateV", rotateV);
		nbt.setString("ajdi", id);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		offsetX = nbt.getFloat("offsetX");
		offsetY = nbt.getFloat("offsetY");
		offsetZ = nbt.getFloat("offsetZ");
		scaleX = nbt.getFloat("scaleX");
		scaleY = nbt.getFloat("scaleY");
		scaleZ = nbt.getFloat("scaleZ");
		obrot = nbt.getFloat("obrot");
		osX = nbt.getFloat("osX");
		osY = nbt.getFloat("osY");
		osZ = nbt.getFloat("osZ");
		rotateX = nbt.getFloat("rotateX");
		rotateY = nbt.getFloat("rotateY");
		rotateZ = nbt.getFloat("rotateZ");
		rotateV = nbt.getFloat("rotateV");
		id = nbt.getString("ajdi");
		setupModel();
	}

	public static void init() {
		GameRegistry.registerTileEntity(TileEntityPluszak.class, "tileEntityPokeMinePluszak");
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPluszak.class, new Renderer());
	}

	@SideOnly(Side.CLIENT)
	private static class Renderer extends TileEntitySpecialRenderer {

		@Override
		public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
			TileEntityPluszak pluszak = (TileEntityPluszak) te;

			bindTexture(new ResourceLocation(PokeMinePluszaki.MODID + ":tiles/" + pluszak.id + ".png"));
			
			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float) x + pluszak.offsetX, (float) y + pluszak.offsetY, (float) z + pluszak.offsetZ);
			GL11.glScalef(pluszak.scaleX, pluszak.scaleY, pluszak.scaleZ);
			
			GL11.glRotatef(te.getBlockMetadata() * 90 + pluszak.obrot, 0.0F + pluszak.osX, 0.0F + pluszak.osY,0.0F + pluszak.osZ);
			GL11.glRotatef(-90F + pluszak.rotateX, 0.0F + pluszak.rotateY, 90.0F + pluszak.rotateZ, 0.0F + pluszak.rotateV);
			
			pluszak.model.renderAll();
			GL11.glPopMatrix();
		}
	
	}

	@SideOnly(Side.CLIENT)
	private IModelCustom model;

	@SideOnly(Side.CLIENT)
	public void setupModel() {
		model = getOrCreateModel(id);
	}

//	@SideOnly(Side.CLIENT)
	private static HashMap<String, IModelCustom> pluszaki = new HashMap<String, IModelCustom>();

	@SideOnly(Side.CLIENT)
	private static IModelCustom getOrCreateModel(String ajdi) {
		IModelCustom model = pluszaki.get(ajdi);
		if (model == null) {
			model = AdvancedModelLoader.loadModel(new ResourceLocation(PokeMinePluszaki.MODID + ":obj/" + ajdi + ".obj"));
			pluszaki.put(ajdi, model);
		}
		return model;
	}

}

abstract class TileEntityWrapper extends TileEntity {
	public void setupModel() {}
}