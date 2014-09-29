package dijkstra;

public class Edge {
	private int source;
	private int destination;
	private int weight;
	
	public Edge(int source,int destination, int weight){
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
	public void setSource(int source){
		this.source = source;
	}
	public int getSource(){
		return this.source;
	}
	public void setDestination(int destination){
		this.destination = destination;
	}
	public int getDestination(){
		return this.destination;
	}
	public void setWeight(int weight){
		this.weight = weight;
	}
	public int getWeight(){
		return this.weight;
	}
	
}
