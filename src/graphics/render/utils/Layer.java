package graphics.render.utils;

import graphics.render.utils.RenderingPrimitivesManager.RenderingChunk;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Layer {
	private RenderingPrimitivesManager primitiveManager;
	public FloatBuffer vertices;
	public int numVertices;
	
	public Layer(){
		primitiveManager = new RenderingPrimitivesManager();
		numVertices = 0;
	}
	
	public void init(int verticesCapacity){
		vertices = BufferUtils.createFloatBuffer(verticesCapacity);
	}
	
	public void addRenderingData(int primitiveType, int verticesAdded){
		primitiveManager.addRenderingData(primitiveType, verticesAdded);
	}
	
	public RenderingChunk getNextRenderingDataChunk(){
		return primitiveManager.getNextChunk();
	}
	
	public String toString(){
		String s = "";
		s += "Layer { numVertices: " + numVertices + ", primativeManager: " + primitiveManager.toString();
		return s;
	}
}
