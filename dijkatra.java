package dijkstra;

import java.util.ArrayList;
import java.util.Map;

import dijkstra.FibonacciHeap.Entry1;

public class Dijkstra {
	public static void main(String[] args) throws Exception{
		ArrayList<Vertex> vertexes;
		int source = 0;
		float density = 0;
		int numVer = 0;
		String filename = "";
		String mode = "";
		Graph graph;
		
		long start =0;
		long runtime =0;
//		LinkedList<Vertex> path;
		//get parameters from command line
		if(args.length == 4){
			mode = args[0];
			numVer = Integer.parseInt(args[1]);
			density = Float.parseFloat(args[2]);
			source = Integer.parseInt(args[3]);
		}else if(args.length == 2){
			mode = args[0];
			filename = args[1];
		}else{
			throw new Exception("input error!");
		}
		
		if(mode.contentEquals("-r")){
			graph = new Graph(numVer,density,source);
			vertexes = graph.getVertexes();
	
			SimpleScheme simpleSheme = new SimpleScheme(graph);
			FibScheme fibSheme = new FibScheme(graph);

			start = System.currentTimeMillis();
			simpleSheme.execute(vertexes.get(source));
			runtime = System.currentTimeMillis()-start;
			System.out.println("random mode simpleSheme run time:"+runtime);
			for(Vertex v: vertexes){
				v.setvisted(false);
			}
			start = System.currentTimeMillis();
			fibSheme.execute(vertexes.get(source));
			runtime = System.currentTimeMillis()-start;
			System.out.println("random mode fibSheme run time:"+runtime);
		}else if(mode.contentEquals("-s") ){
			graph = new Graph(filename);
			vertexes = graph.getVertexes();
			source = graph.x;
			SimpleScheme simpleSheme = new SimpleScheme(graph);	
			simpleSheme.execute(vertexes.get(source));
			Map<Vertex, Integer> distance = simpleSheme.getDist();
			for(int i=0;i<vertexes.size();i++){
				System.out.println(distance.get(vertexes.get(i))+ " //"+
							"cost from node "+source+" to "+i);
			}
			
		}else if(mode.contentEquals("-f")){
			graph = new Graph(filename);
			vertexes = graph.getVertexes();
			source = graph.x;
			FibScheme fibSheme = new FibScheme(graph);		
			fibSheme.execute(vertexes.get(source));
			ArrayList<Entry1<Vertex>> entryList = fibSheme.getDist();
			for(int i=0;i<vertexes.size();i++){
				System.out.println(entryList.get(i).keyValue+ " //"+
							"cost from node "+source+" to "+i);
			}
		}else{
			throw new Exception("mode input error!");
		}
		
	}
}
