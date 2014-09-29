package dijkstra;

import java.util.ArrayList;
public class Vertex {
	private int id;
	private boolean visited;
	private ArrayList<Edge> edges;
	
	public Vertex() {

	}
	public Vertex(int id, ArrayList<Edge> edges){
		super();
		this.id = id;
		this.visited = false;
		this.edges = edges;
	}
	public int getid(){
		return this.id;
	}
	public void setvisted(boolean visited){
		this.visited = visited;
	}
	public boolean isvisited(){
		return this.visited;
	}
	public void addEdges(ArrayList<Edge> edges){
		this.edges = edges;
	}
	public ArrayList<Edge> getEdges(){
		return this.edges;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (this == obj)
        	return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	    	return false;
	    Vertex other = (Vertex) obj;
	    if (id != other.id)
	    	return false;
	    return true;
	}
	
	
    @Override
	public String toString(){
		return "Vertexes:"+id+" [adjecentList=" +edges+ "]";
	}
	
}
