import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

		private static List<Vertex> generateCircle(int count)
		{
			List<Vertex> ret = new ArrayList<Vertex>(count);
			
			return null;
		}
		
		private static boolean visualisation = false;
		
        public static void main(String[] args) {

                if (args.length == 0) {
                        System.err.println("[ERROR] 69: Nein. Einfach nur nein.");
                        System.exit(-1);
                }
                
                List<Vertex> vertices = new ArrayList<Vertex>();
                
                int offset = 0;
                boolean passedNum = false;
                for(int i = 0; i < args.length; i++)
                {
                	if(args[i].equals("-v") || args[i].equals("/v") || 
                            args[i].equals("-viz") || args[i].equals("/viz") || 
                            args[i].equals("-visualize") || args[i].equals("/visualize"))
                    {
                    	visualisation = true;
                    	if(!passedNum) offset++;
                    }
                	
                	if(args[i].equals("-verbose") || args[i].equals("/verbose"))
                    {
                    	verbose = true;
                    	if(!passedNum) offset++;
                    }
                	
                	if(args[i].equals("-pointsize") || args[i].equals("/pointsize"))
                    {
                    	Visualizer.pointsize = Float.parseFloat(args[i+1]);
                    	if(!passedNum) offset+=2;
                    }
                	
                	if(args[i].equals("-speed") || args[i].equals("/speed"))
                    {
                    	Visualizer.speed = Float.parseFloat(args[i+1]);
                    	if(!passedNum) offset+=2;
                    }
                	
                	try
                	{
                		if(args[i].contains("1:")||args[i].contains("0:"))
                			passedNum = true; //woops, we passed the numbers in the parameters
                	}
                	catch (Exception e)
                	{
                		
                	}
                }
                
                if(args[0].equals("-gen") || args[0].equals("/gen") || 
                   args[0].equals("-g") || args[0].equals("/g") || 
                   args[0].equals("-generate") || args[0].equals("/generate"))
                {
                	if(args.length == 1) {
                		System.err.println("[ERROR] 42: I'm leaving on a jet plane cause there is no number and nothing to do for me...");
                		System.exit(-1);
                	} else {                		
                		vertices = EulerCircleGen.generate(Integer.parseInt(args[1]), args.length > 2 ? Integer.parseInt(args[2]) : 0);
                		args = new String[]{};
                	}
                	
                	
                }
                
                
                for (int i = offset; i < args.length; i++) {

                    
                    int knotid = Integer.parseInt(args[i].split(":")[0]);
                    String[] connections = args[i].split(":")[1].split(";");
                    List<Path> connected = new ArrayList<Path>(connections.length);
                    
                    for(int x = 0; x < connections.length; x++)
                    {
                    	connected.add(new Path(knotid, Integer.parseInt(connections[x])));
                    }
                    
                    vertices.add(new Vertex(knotid, connected));
                    /*
                    for (int x = 0; x < args[i].length(); x++) {
                            connected.add(new Path(i+1,(args[i].charAt(x) - 48)));
                            if(!((args[i].charAt(x) - 48) >= 0 && (args[i].charAt(x) - 48) <= 9))
                            {
                            	System.err.println("[ERROR] 1337: Your argument is invalid. Srsly.");
                                System.exit(-1);
                            }
                    }

                    vertices.add(new Vertex(i+1, connected));
                    */
                    
            	}
                

                new Main(vertices);
        }

		public static boolean verbose = false;

        public Main(List<Vertex> vertices) {

                int res = checkEuler(vertices);

                switch (res) {

                        case 0:  String result = calculateCircle(vertices);
                        		 System.out.println("[INFO] Got result circle: "+result); 
                        		 if(Main.visualisation) new Visualizer(vertices, result);
                        		 break;
                        case 1:  result = calculatePath(vertices);
                        		 System.out.println("[INFO] Got result path: "+result);  
                        		 if(Main.visualisation) new Visualizer(vertices, result);
                        		 break;
                        case -1: System.exit(-1);                               break;
                }
        }

        private int checkEuler(List<Vertex> vertices) {

                int odd = 0;

                for (int i = 0; i < vertices.size(); i++) {

                        if ((vertices.get(i).getPathsCount() % 2) != 0)
                                odd++;
                }

                switch (odd) {
                        case 0:  System.out.println("[INFO] Eulercircle possible"); return 0;
                        case 2:  System.out.println("[INFO] Eulerpath possible");   return 1;
                        default: System.err.println("[ERROR] 33: Fuck you.");       return -1;
                }
        }
        
        private void setVisited(int start, int end, List<Vertex> vertices)
        {
        	Path ref = new Path(start, end);
        	boolean printed = false;
        	for (int i = 0; i < vertices.size(); i++)
        	{
        		if(vertices.get(i).getPaths().contains(ref))
        		{
        			vertices.get(i).getPaths().get(vertices.get(i).getPaths().indexOf(ref)).setVisited(true);
        			if(!printed) {
        				if(Main.verbose) System.out.println("[INFO] Path "+vertices.get(i).getPaths().get(vertices.get(i).getPaths().indexOf(ref)).getStart()+" ----- "+vertices.get(i).getPaths().get(vertices.get(i).getPaths().indexOf(ref)).getEnd()+" marked as visited!");
        				printed = true;
        			}
        		}
        	}
        }
        
        private Vertex getVertexById(int id, List<Vertex> vertices)
        {
        	for (int i = 0; i < vertices.size(); i++)
        	{
        		if(vertices.get(i).getId() == id && vertices.get(i).getFirstNonVisitedPath() != null)
        			return vertices.get(i);
        	}
        	return null;
        }
        
        private Vertex findVertexBetween(Vertex v1, Vertex v2, List<Vertex> vertices)
        {
        	List<Vertex> pPS = new ArrayList<Vertex>(v1.getPathsCount());
        	
        	for(int i = 0; i < v1.getPaths().size(); i++)
        	{
        		if(!v1.getPaths().get(i).getVisited())
        			pPS.add(getVertexById(v1.getPaths().get(i).getEnd(),vertices));
        	}
        	
        	for(int i = 0; i < pPS.size(); i++)
        	{
        		for(int x = 0; x < pPS.get(i).getPaths().size(); x++)
        		{
        			if(!pPS.get(i).getPaths().get(x).getVisited() && pPS.get(i).getPaths().get(x).getEnd() == v2.getId())
        			{
        				if(Main.verbose)  System.out.println("[INFO] Calculated to use point "+pPS.get(i).getId());
        				return pPS.get(i);
        			}
        		}
        	}
        	
        	return null;
        }
        
        private boolean arePathLeft(List<Vertex> vertices)
        {
        	for (int i = 0; i < vertices.size(); i++)
        	{
        		if(vertices.get(i).hasNonVisitedPaths())
        			return true;
        	}
        	
        	return false;
        }
        
        private Vertex getFirstWithPathsAv(List<Vertex> vertices)
        {
        	for (int i = 0; i < vertices.size(); i++)
        	{
        		if(vertices.get(i).hasNonVisitedPaths())
        			return vertices.get(i);
        	}
        	
        	return null;
        }
        
        private String pathToString(List<Integer> steps)
        {
        	String result = "";
        	
        	for(int i = 0; i < steps.size(); i++)
        	{
        		result += steps.get(i) + "->";
        	}
        	
        	return result.endsWith("->") ? result.substring(0, result.length()-2) : result;
        }

        private List<Integer> calculateCircle(Vertex start, List<Vertex> vertices) {        	
        	Vertex last = getVertexById(start.getFirstNonVisitedPath().getEnd(), vertices);
        	List<Integer> steps = new LinkedList<Integer>();
        	steps.add(start.getId());
        	steps.add(last.getId());
        	setVisited(start.getId(), last.getId(), vertices);
        	while(last.getId() != start.getId())
        	{
        		int lid = last.getId();
        		last = getVertexById(last.getFirstNonVisitedPath().getEnd(), vertices);
        		setVisited(lid, last.getId(), vertices);
        		steps.add(last.getId());
        	}
        	if(Main.verbose) System.out.println("[INFO] Partial result: "+pathToString(steps));
        	return steps;
        }

        private String calculateCircle(List<Vertex> vertices) {
        	Vertex start = vertices.get(0);
        	System.out.println("[INFO] Starting circle calculation. Using "+start.getId()+" as starting point.");
        	List<List<Integer>> steps = new ArrayList<List<Integer>>(vertices.size()*10);
        	while (arePathLeft(vertices))
        	{
        		steps.add(calculateCircle(start, vertices));
        		start = getFirstWithPathsAv(vertices);
        		if(start != null && Main.verbose) System.out.println("[INFO] Using "+start.getId()+" as next point.");
        	}
        	
        	List<Integer> result = steps.get(0);
        	for(int i = 1; i < steps.size(); i++)
        	{
        		
        		for(int x = result.size()-1; x >= 0; x--)
        		{
        			if(result.get(x) == steps.get(i).get(0))
        			{
        				steps.get(i).remove(0);
        				result.addAll(x+1, steps.get(i));
        				break;
        			}
        		}
        	}
        	return pathToString(result);
        }
        
        

        private String calculatePath(List<Vertex> vertices) {
        	
        	Vertex ue = getFirstUneven(vertices);
        	
        	Path padd1 = new Path(ue.getId(), vertices.size()+1);
        	Path paddI1 = new Path(vertices.size()+1, ue.getId());
        	ue.addPath(padd1);
        	
        	ue = getFirstUneven(vertices);
        	
        	Path padd2 = new Path(ue.getId(), vertices.size()+1);
        	Path paddI2 = new Path(vertices.size()+1, ue.getId());
        	ue.addPath(padd2);
        	
        	List<Path> addpI = new ArrayList<Path>(2);
        	addpI.add(paddI1);
        	addpI.add(paddI2);
        	
        	Vertex add = new Vertex(vertices.size()+1, addpI);
        	vertices.add(0, add);
        	
        	System.out.println("[INFO] Starting path calculation. Using "+add.getId()+" as added starting point.");
        	
        	String result = calculateCircle(vertices);
        	
        	if(Main.verbose) System.out.println("[INFO] Removing generated point "+add.getId());
        	
        	vertices.remove(add);
        	
        	for(int i = 0; i < vertices.size(); i++)
        	{
        		vertices.get(i).getPaths().remove(new Path(vertices.get(i).getId(), add.getId()));
        	}
        	
        	return result.replace(add.getId()+"->", "").replace("->"+add.getId(), "");
        }

		private Vertex getFirstUneven(List<Vertex> vertices) {
			
			for(int i = 0; i < vertices.size(); i++)
			{
				if(vertices.get(i).getPathsCount() % 2 != 0)
					return vertices.get(i);
			}
			return null;
		}
}
