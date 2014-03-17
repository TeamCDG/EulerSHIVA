import java.util.ArrayList;
import java.util.List;


public abstract class EulerCircleGen {
	
	public static List<Vertex> generate(int knotCount, int randomDepth)
	{
		List<Vertex> ret = new ArrayList<Vertex>(knotCount);
		
		
		System.out.println("[Info] Generating euler circle");
		
		long startingTime = Visualizer.getTime();
		for(int i = 1; i <= knotCount; i++)
		{
			if(i != 1 && i < knotCount)
			{
				List<Path> n = new ArrayList<Path>();
				n.add(new Path(i, i-1));
				Vertex v = new Vertex(i,n);
				ret.add(v);
				ret.get(ret.indexOf(v)-1).addPath(new Path(i-1, i));
			}
			else if(i == knotCount)
			{
				List<Path> n = new ArrayList<Path>();
				n.add(new Path(i, 1));
				n.add(new Path(i, i-1));
				Vertex v = new Vertex(i,n);
				ret.add(v);
				ret.get(0).addPath(new Path(1, i));
				ret.get(ret.indexOf(v)-1).addPath(new Path(i-1, i));
			}
			else
			{
				ret.add(new Vertex(i, new ArrayList<Path>()));
			}
		}
		
		for(int i = 0; i < Math.min(knotCount/5, randomDepth); i++)
		{
		
			Vertex _v1 = getRandom(ret, knotCount);
			if(_v1 == null) break;
			
			Vertex _v2 = getNextRandom(ret, knotCount, _v1);
			if(_v2 == null) break;
			
			while (_v1.getId() == _v2.getId())
			{
				_v2 = ret.get(new java.util.Random().nextInt(knotCount));
			}
			
			ret.get(ret.indexOf(_v1)).addPath(new Path(_v1.getId(), _v2.getId()));
			if(Main.verbose) System.out.println("[INFO] Adding path: "+_v1.getId()+" ----- "+_v2.getId());
			ret.get(ret.indexOf(_v2)).addPath(new Path(_v2.getId(), _v1.getId()));
			
			while(hasEmptyVertex(ret) || !isCirclePossible(ret))
			{
				Vertex v1 = getEmptyOrUneven(ret);
				Vertex v2 = getNext(ret,v1);
								
				ret.get(ret.indexOf(v1)).addPath(new Path(v1.getId(), v2.getId()));
				if(Main.verbose) System.out.println("[INFO] Adding path: "+v1.getId()+" ----- "+v2.getId());
				ret.get(ret.indexOf(v2)).addPath(new Path(v2.getId(), v1.getId()));
				
			}
			
			
		}
		
		for(int i = 0; i < ret.size(); i++)
		{
			for(Path p: ret.get(i).getPaths())
			{
				if(Main.verbose) System.out.println(ret.get(i).getId()+": "+p.getStart()+" ----- "+p.getEnd());
			}
			if(Main.verbose) System.out.println("-----------------");
		}
		
		if(knotCount == 1) {
    		System.err.println("[ERROR] 418: you are an idiot hahahahahahahahaha :)");
    		System.exit(-1);
    	}
		
		long time = Visualizer.getTime()-startingTime;
    	System.out.println("[INFO] Generation took "+time+"ms");
		
		return ret;
		
	}
	
	private static Vertex getRandom(List<Vertex> vertices, int knotCount)
	{
		List<Vertex> possi = new ArrayList<Vertex>(vertices.size());
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getPathsCount() +2 < knotCount)
                    possi.add(vertices.get(i));
		}
		
		if(possi.size() != 0)
			return possi.get(new java.util.Random().nextInt(possi.size()));
		else
			return null;
	}
	
	private static Vertex getNextRandom(List<Vertex> vertices, int knotCount, Vertex already)
	{
		List<Vertex> possi = new ArrayList<Vertex>(vertices.size());
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getPathsCount() +2 < knotCount && !already.getPaths().contains(new Path(already.getId(), vertices.get(i).getId())))
                    possi.add(vertices.get(i));
		}
		
		if(possi.size() >= 4)
		{
			return possi.get(new java.util.Random().nextInt(possi.size()));
		}
		else
			return null;
	}
	
	private static Vertex getNext(List<Vertex> vertices, Vertex already)
	{
		List<Vertex> possi = new ArrayList<Vertex>(vertices.size());
		for (int i = 0; i < vertices.size(); i++) {
			if ((vertices.get(i).getPathsCount() == 0 || vertices.get(i).getPathsCount()%2 != 0) && already.getId() != vertices.get(i).getId() && !already.getPaths().contains(new Path(already.getId(), vertices.get(i).getId())))
				 possi.add(vertices.get(i));
		}
		
		
		if(possi.size() != 0)
		{
			return possi.get(new java.util.Random().nextInt(possi.size()));
		}
		for (int i = 0; i < vertices.size(); i++) {
			
			
            if (already.getId() != vertices.get(i).getId() && !already.getPaths().contains(new Path(already.getId(), vertices.get(i).getId())))
                    return vertices.get(i);
		}
		
		return null;
	}
	
	private static Vertex getEmptyOrUneven(List<Vertex> vertices)
	{
		for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i).getPathsCount() == 0 || vertices.get(i).getPathsCount()%2 != 0)
                    return vertices.get(i);
		}
		return null;
	}
	
	private static boolean hasEmptyVertex(List<Vertex> vertices)
	{
		for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i).getPathsCount() == 0)
                    return true;
		}
		
		return false;
	}
	
	private static boolean isCirclePossible(List<Vertex> vertices)
	{
		for (int i = 0; i < vertices.size(); i++) {

            if ((vertices.get(i).getPathsCount() % 2) != 0)
                    return false;
		}
		
		return true;

	}

}
