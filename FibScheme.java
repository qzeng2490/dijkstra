package dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import dijkstra.FibonacciHeap.Entry1;
public class FibScheme {
	  private ArrayList<Vertex> nodes;
	  private ArrayList<Edge> edges;
	  private int x;
	  private int N;
	  private HashSet<Entry1<Vertex>> settledNodes;
	  private Map<Vertex, Vertex> predecessors;
	  //each node in FibHeap stores information of a vertex and the distance
	  private FibonacciHeap<Vertex> fibHeap;
	  private ArrayList<Entry1<Vertex>> entryList;  	  
	  private int[][] a;
	  private boolean[][] b ; 
	  
	  
	  public FibScheme(Graph graph) {
		    // create a copy of the array so that we can operate on this array
		    this.nodes = new ArrayList<Vertex>(graph.getVertexes());
		    this.edges = new ArrayList<Edge>(graph.getEdges());
		    this.entryList = new ArrayList<Entry1<Vertex>>();
		    this.x = graph.x;
		    this.N = nodes.size();
		    this.a = new int[N][N];
		    this.b = new boolean[N][N];
		    for(int i =0 ; i<N;i++)
		    	for(int j =0; j<N;j++)
		    		b[i][j] = false;
		    for(Edge e:edges){
		    	int s = nodes.get(e.getSource()).getid();
		    	int d = nodes.get(e.getDestination()).getid();
		    	Integer w = (Integer) e.getWeight();
		    	a[s][d] = w;
		    	b[s][d] = true;
		    }
	  }
	  
	  public void execute(Vertex source) {
		  /* set of finished vertices and unfinished vertices */
		  settledNodes = new HashSet<Entry1<Vertex>>();
		  /* FibHeap keep track of distances from the source to each vertex */
		  fibHeap = new FibonacciHeap<Vertex>();
		  /* predecessors keep track of preceding vertices */
		  predecessors = new HashMap<Vertex, Vertex>();
		  
		  Entry1<Vertex> tempEntry;
		  
		  /* Initialization: set every distance to INFINITY until we discover a path */
		  
		  for(int i=0;i < nodes.size();i++){
			  tempEntry = fibHeap.enqueue(nodes.get(i), Integer.MAX_VALUE);
			  entryList.add(tempEntry);
		  }
		  /* get source in the fibHeap and decrease key of source */
		  tempEntry = entryList.get(source.getid());
		  fibHeap.decreaseKey(tempEntry, 0);
		  
		  /* This loop corresponds to sending out the explorers walking the paths, where
		   * the step of picking "the vertex, node, with the shortest path to s" corresponds
		   * to an explorer arriving at an unexplored vertex */
		  
		  while (fibHeap.size() > 0) {
			  Entry1<Vertex> node = fibHeap.dequeueMin();
		      settledNodes.add(node);
		      updateMinimalDistances(node);
		  }
	  }
	  /*update the distance of neighbors of the node*/
	  private void updateMinimalDistances(Entry1<Vertex> node) {
		  ArrayList<Entry1<Vertex>> adjacentNodes = getNeighbors(node);
		  for (Entry1<Vertex> target : adjacentNodes) {
			  int newdist = node.keyValue + a[node.getValue().getid()][target.getValue().getid()];
			  if (target.keyValue > newdist) {
				  fibHeap.decreaseKey(target, newdist);
				  predecessors.put(target.getValue(), node.getValue());
		      }
		  }
	  }
	  
	  /*Get adjacent list of the node*/
	  private ArrayList<Entry1<Vertex>> getNeighbors(Entry1<Vertex> node) {
		  ArrayList<Entry1<Vertex>> neighbors = new ArrayList<Entry1<Vertex>>();
		  int source = node.getValue().getid();
		  for(int i=0;i<N;i++){
			  if(b[source][i]){
				  Entry1<Vertex> v = this.entryList.get(i);
				  if(!isSettled(v.getValue())){
					  neighbors.add(v);
				  }
			  }
		  }
//		  for (Edge edge : edges) {
//			  Entry1<Vertex> v = this.entryList.get(edge.getDestination());
//			  if (edge.getSource() == node.getValue().getid() && !isSettled(v.getValue())) {
//				  neighbors.add(v);
//		      }
//		  }
		  return neighbors;
	  }
	  
	  private boolean isSettled(Vertex vertex) {
		  return settledNodes.contains(vertex);
	  }
	  public LinkedList<Vertex> getPath(Vertex target) {
		  LinkedList<Vertex> path = new LinkedList<Vertex>();
		  Vertex step = target;
		  // check if a path exists
		  if (predecessors.get(step) == null) {
			  return null;
		  }
		  path.add(step);
		  while (predecessors.get(step) != null) {
			  step = predecessors.get(step);
			  path.add(step);
		  }
		  // Put it into the correct order
		  Collections.reverse(path);
		  return path;
	  }

	  
	  public void sortEntryList(){
			Collections.sort(this.entryList, new Comparator<Entry1<Vertex>>(){
				public int compare(Entry1<Vertex> arg0, Entry1<Vertex> arg1){
					return arg0.getValue().getid() > arg1.getValue().getid() ?1:-1;
				}
			});
			
	  }
  
	  public ArrayList<Entry1<Vertex>> getDist(){
		  return this.entryList;
	  }
	  public ArrayList<Edge> getEdge(){
		  return this.edges;
	  }
	  public ArrayList<Vertex> getVertex(){
		  return this.nodes;
	  }
	  public int getX(){
		  return this.x;
	  }
	  
	  public void printDist(ArrayList<Entry1<Vertex>> distances){
		  for(Entry1<Vertex> entry : distances){
				System.out.println("Vertex: "+ entry.getValue().getid() + "\tDIST: "+entry.keyValue);
			}
	  }
}
