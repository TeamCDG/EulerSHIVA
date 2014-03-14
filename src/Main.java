import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

		private static List<Vertex> generateCircle(int count)
		{
			List<Vertex> ret = new ArrayList<Vertex>(count);
			if(count == 1) {
        		System.err.println("[ERROR] 418: you are an idiot hahahahahahahahaha :)");
        		System.exit(-1);
        	}
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
                
                if(args[0].equals("-gen") || args[0].equals("/gen") || 
                   args[0].equals("-g") || args[0].equals("/g") || 
                   args[0].equals("-generate") || args[0].equals("/generate"))
                {
                	if(args.length == 1) {
                		System.err.println("[ERROR] 42: I'm leaving on a jet plane cause there is no number and nothing to do for me...");
                		System.exit(-1);
                	} else {                		
                		vertices = generateCircle(Integer.parseInt(args[1]));
                	}
                }
                else if (args[0].equals("-v") || args[0].equals("/v") || 
                        args[0].equals("-viz") || args[0].equals("/viz") || 
                        args[0].equals("-visualize") || args[0].equals("/visualize"))
                {
                	visualisation = true;
                	offset++;
                }
                else if(args[args.length-1].equals("-v") || args[args.length-1].equals("/v") || 
                        args[args.length-1].equals("-viz") || args[args.length-1].equals("/viz") || 
                        args[args.length-1].equals("-visualize") || args[args.length-1].equals("/visualize"))
                {
                	visualisation = true;
                }
                
                for (int i = offset; i < args.length; i++) {

                    List<Path> connected = new ArrayList<Path>();
                    int knotid = Integer.parseInt(args[i].split(":")[0]);
                    String[] connections = args[i].split(":")[1].split(";");
                    
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

        public Main(List<Vertex> vertices) {

                int res = checkEuler(vertices);

                switch (res) {

                        case 0:  System.out.println("[INFO] Got result circle: "+calculateCircle(vertices)); break;
                        case 1:  System.out.println("[INFO] Got result path: "+calculatePath(vertices));   break;
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
        	for (int i = 0; i < vertices.size(); i++)
        	{
        		if(vertices.get(i).getPaths().contains(ref))
        		{
        			vertices.get(i).getPaths().get(vertices.get(i).getPaths().indexOf(ref)).setVisited(true);
        			System.out.println("[INFO] Path "+vertices.get(i).getPaths().get(vertices.get(i).getPaths().indexOf(ref)).getStart()+" ----- "+vertices.get(i).getPaths().get(vertices.get(i).getPaths().indexOf(ref)).getEnd()+" marked as visited!");
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
        				System.out.println("[INFO] Calculated to use point "+pPS.get(i).getId());
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

        private static ArrayList<Integer> rList = new ArrayList<Integer>();

        private String calculateCircle(Vertex start, List<Vertex> vertices) {        	
        	Vertex last = getVertexById(start.getFirstNonVisitedPath().getEnd(), vertices);
        	String result = start.getId()+""+last.getId();
        	setVisited(start.getId(), last.getId(), vertices);
        	while(last.getId() != start.getId())
        	{
        		int lid = last.getId();
        		last = getVertexById(last.getFirstNonVisitedPath().getEnd(), vertices);
        		setVisited(lid, last.getId(), vertices);
        		result += last.getId();
        	}
        	
        	return result+"";
        }

        private String calculateCircle(List<Vertex> vertices) {
        	Vertex start = vertices.get(0);
        	System.out.println("[INFO] Starting circle calculation. Using "+start.getId()+" as starting point.");
        	List<String> steps = new ArrayList<String>(vertices.size()*10);
        	while (arePathLeft(vertices))
        	{
        		steps.add(calculateCircle(start, vertices));
        		start = getFirstWithPathsAv(vertices);
        	}
        	
        	String result = steps.get(0);
        	for(int i = 1; i < steps.size(); i++)
        	{
        		
        		result = result.substring(0, result.lastIndexOf(steps.get(i).substring(0, 1))) + steps.get(i) + result.substring(result.lastIndexOf(steps.get(i).substring(0, 1)) + 1, result.length());
        	}
        	return result;
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
        	vertices.add(add);
        	
        	Vertex start = add;
        	System.out.println("[INFO] Starting path calculation. Using "+start.getId()+" as added starting point.");
        	List<String> steps = new ArrayList<String>(vertices.size()*10);
        	while (arePathLeft(vertices))
        	{
        		steps.add(calculateCircle(start, vertices));
        		start = getFirstWithPathsAv(vertices);
        	}
        	
        	String result = steps.get(0);
        	for(int i = 1; i < steps.size(); i++)
        	{
        		
        		result = result.substring(0, result.lastIndexOf(steps.get(i).substring(0, 1))) + steps.get(i) + result.substring(result.lastIndexOf(steps.get(i).substring(0, 1)) + 1, result.length());
        	}
        	
        	System.out.println("[INFO] Removing generated point "+add.getId());
        	return result.replace(""+add.getId(), "");
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
