package dijkstra;

import java.util.Random;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Graph {
	int numVer;
	float density;
	int numEdge; // number of edges in this graph
	int x;       //source node
	private  ArrayList<Vertex> vertexes;
	private  ArrayList<Edge> edges;
	public Graph(){
		
	}
	public Graph(int numVer, float density, int x){
		super();
		this.numVer = numVer;
		this.density = density;
		this.x = x;
		this.numEdge =Math.round(density*(numVer) * (numVer -1)/2);
		
		boolean [][] adjMatrix = new boolean[numVer][numVer]; 
		Random r = new Random();
		
		do{
			this.vertexes = new ArrayList<Vertex>();
			this.edges = new ArrayList<Edge>();
			for(int i=0; i<this.numVer;i++){
				Vertex v = new Vertex(i,new ArrayList<Edge>());
				this.vertexes.add(v);
			}
			for(int i=0; i< numVer; i++){
				for(int j=0; j<numVer; j++){
					adjMatrix[i][j] = false;
				}
			}
			//gernerate edges
			for(int k=0; k<numEdge;k++){
				int s,d;
				do{
					 s = r.nextInt(numVer);
					 d = r.nextInt(numVer);
				}while(s == d);
				int w = r.nextInt(1000)+1;
				if(adjMatrix[s][d] || adjMatrix[d][s]){
					k--;
				}else{
					this.edges.add(new Edge(s,d,w));
					this.edges.add(new Edge(d,s,w));
					adjMatrix[s][d] = true;
					adjMatrix[d][s] = true;
				}
			}
			
			//add adjecent list to each vertex	
			for(Edge temp :edges){
				int source = temp.getSource();
				this.vertexes.get(source).getEdges().add(temp);
			}
			
		}while(!this.connect());
		
		for(Vertex v : this.vertexes){
			v.setvisted(false);
		}		
	}
	public Graph(String fileName){
		try{
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader reader = new InputStreamReader(fis, "gbk");
			BufferedReader br = new BufferedReader(reader);
			
			this.x = Integer.parseInt(br.readLine());
			String temp = br.readLine();
			this.numVer = Integer.parseInt(temp.split(" ")[0]);
			this.numEdge = Integer.parseInt(temp.split(" ")[1]);
			// initiate vertexes by numVer
			this.vertexes = new ArrayList<Vertex>();
			this.edges = new ArrayList<Edge>();
			for(int i=0; i<this.numVer;i++){
				Vertex v = new Vertex(i,new ArrayList<Edge>());
				this.vertexes.add(v);
			}
			String info = "";
			//add edges to graph
			while ((info = br.readLine()) != null) {
				int s = Integer.parseInt(info.split(" ")[0]);
				int d = Integer.parseInt(info.split(" ")[1]);
				int w = Integer.parseInt(info.split(" ")[2]);
				Edge e1 = new Edge(s,d,w);
				Edge e2 = new Edge(d,s,w);
				this.edges.add(e1);
				this.edges.add(e2);
				this.vertexes.get(s).getEdges().add(e1);
				this.vertexes.get(d).getEdges().add(e2);
			}
			//close stream
			br.close();
			reader.close();
			fis.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	//using DFS to determine whether the graph is connected
	public boolean connect(){
		Vertex v0 = this.vertexes.get(0);	
		DFS(v0);	
		int numVisted =0 ;
		for(int i=0 ; i<this.numVer;i++){
			if(this.vertexes.get(i).isvisited() == true){
				numVisted++;
			}
		}
		if(numVisted == this.numVer){
			System.out.println("Yes!!!Connected!\n");
			return true ; 
		}else{
			return false ;
		}
	}
	//
	public void DFS(Vertex root){
		if(root == null) return;
		root.setvisted(true);
		for(Edge e: root.getEdges()){
			int d = e.getDestination();
			Vertex vd = this.getVertexes().get(d);
			if(vd.isvisited() == false){
				DFS(vd);
			}
		}
	}
	
	public ArrayList<Vertex> getVertexes(){
		return this.vertexes;
	}
	public ArrayList<Edge> getEdges(){
		return this.edges;
	}
		

}
