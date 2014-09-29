package dijkstra;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class SimpleScheme {
	  private ArrayList<Vertex> nodes;
	  private ArrayList<Edge> edges;
	  private int x;
	  private int N;
	  private Set<Vertex> settledNodes;
	  private ArrayList<Vertex> unSettledNodes;
	  private Map<Vertex, Vertex> predecessors;
	  private Map<Vertex, Integer> distance;
	  
	  private int[][] a;
	  private boolean[][] b ; 

	  
	  public SimpleScheme(Graph graph) {
		    // create a copy of the array so that we can operate on this array
		    this.nodes = new ArrayList<Vertex>(graph.getVertexes());
		    this.edges = new ArrayList<Edge>(graph.getEdges());
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
		  //set of finished vertices and unfinished vertices
		  settledNodes = new HashSet<Vertex>();
		  unSettledNodes = new ArrayList<Vertex>();
		  //distance keep track of distances from the source to each vertex
		  distance = new HashMap<Vertex, Integer>();
		  //predecessors keep track of preceding vertices
		  predecessors = new HashMap<Vertex, Vertex>();
		  distance.put(source, 0);
		  unSettledNodes.add(source);
		  
		  /* This loop corresponds to sending out the explorers walking the paths, where
		   * the step of picking "the vertex, node, with the shortest path to s" corresponds
		   * to an explorer arriving at an unexplored vertex */
		  	  
		  while (unSettledNodes.size() > 0) {
			  Vertex node = getMinimum(unSettledNodes);
		      settledNodes.add(node);
		      unSettledNodes.remove(node);
		      updateMinimalDistances(node);
		  }
	  }
	  /*update the distance of neighbors of the node*/
	  private void updateMinimalDistances(Vertex node) {
		  ArrayList<Vertex> adjacentNodes = getNeighbors(node);
		  for (Vertex target : adjacentNodes) {
			  int newdist = getShortestDistance(node) + a[node.getid()][target.getid()];
			  if (getShortestDistance(target) > newdist) {
				  distance.put(target, newdist);
				  predecessors.put(target, node);
				  unSettledNodes.add(target);
		      }
		  }
	  }
	  
	  /*Get adjacent list of the node*/
	  private ArrayList<Vertex> getNeighbors(Vertex node) {
		  ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
		  int source = node.getid();
		  for(int i=0;i<N;i++){
			  if(b[source][i]){
				  Vertex v = this.getVertex().get(i);
				  if(!isSettled(v)){
					  neighbors.add(v);
				  }
			  }
		  }
//		  for (Edge edge : edges) {
//			  Vertex v = this.getVertex().get(edge.getDestination());
//			  if (edge.getSource() == node.getid() && !isSettled(v)) {
//				  neighbors.add(v);
//		      }
//		  }
		  return neighbors;
	  }
	  /* Get the node in unsettled nodes which has the minimum distance to the settled nodes */
	  private Vertex getMinimum(ArrayList<Vertex> vertexes) {
		  Vertex minimum = null;
		  for (Vertex vertex : vertexes) {
			  if (minimum == null) {
				  minimum = vertex;
		      } else {
		    	  if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
		    		  minimum = vertex;
		    	  }
		     }
		  }
		  return minimum;
	 }

	  private boolean isSettled(Vertex vertex) {
		  return settledNodes.contains(vertex);
	  }

      private int getShortestDistance(Vertex destination) {
    	  Integer d = distance.get(destination);
		  if (d == null) {
		       return Integer.MAX_VALUE;
		  } else {
		       return d;
		  }
	  }

      /*
	   * This method returns the path from the source to the selected target and
	   * NULL if no path exists
	   */
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

	  public Map<Vertex, Integer> getDist(){
		  return this.distance;
	  }
	  public ArrayList<Vertex> getVertex(){
		  return this.nodes;
	  }
	  public ArrayList<Edge> getEdge(){
		  return this.edges;
	  }
	  public int getX(){
		  return this.x;
	  }
	   
	  public void printDist(Map<Vertex, Integer> distances){
		  for(Map.Entry<Vertex, Integer> entry : distances.entrySet()){
				System.out.println("Vertex: "+ entry.getKey().getid() + "\tDIST: "+entry.getValue());
			}
	  }
	  public void printsSettledNodes(Set<Vertex> set){
		  for(Vertex v: set){
			  System.out.println("settledNodes:\t" + v.getid());
		  }
	  }
	  public void printUnSettledNodes(Set<Vertex> set){
		  for(Vertex v: set){
			  System.out.println("unsettledNodes:\t" + v.getid());
		  }
	  }
}
