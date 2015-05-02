package graphics.render.utils;

public class RenderingLayerManager {
	private Layer[] layers;
	private int activeLayer, totalLayers;
	
	public RenderingLayerManager(int layers){
		this.layers = new Layer[layers];
		this.totalLayers = layers;			
	}

	public Layer getActiveLayer() {
		return layers[activeLayer];
	}
	
	public int currentIndex(){
		return activeLayer;
	}

	public void setActiveLayer(int layerIndex) throws IndexOutOfBoundsException {
		if(!this.validIndex(layerIndex))
			throw new IndexOutOfBoundsException();
		this.activeLayer = layerIndex;
	}

	public void addLayer(int layerIndex, Layer layer) throws IndexOutOfBoundsException {		
		if(!this.validIndex(layerIndex))
			throw new IndexOutOfBoundsException();
		this.layers[layerIndex] = layer;
	}
	
	public Layer getLayer(int layerIndex) throws IndexOutOfBoundsException {
		if(!this.validIndex(layerIndex))
			throw new IndexOutOfBoundsException();
		return layers[layerIndex];
	}
	
	private boolean validIndex(int index){
		return index>=0 && index<totalLayers;
	}

	public void addRenderingData(int primitiveType, int verticesAdded) {
		this.getActiveLayer().addRenderingData(primitiveType, verticesAdded);
	}
	
	public Layer[] getLayers() {
		return this.layers;
	}
	
	public String toString(){
		String s = "";
		s += "RendereingLayerManager { totalLayers: " + totalLayers + ", activeLayer: " + activeLayer + ", layers: [\n";
		for(Layer layer : layers){
			s+=layer.toString() + ",\n";
		}
		s += "] }";
		return s;
	}

}
