import java.util.List;

public class Vertex {

	private List<Path> edges;
	private int id;

	public Vertex(int id, List<Path> edges)
	{
		this.edges = edges;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getPathsCount() {
		return edges.size();
	}

	public List<Path> getPaths() {
		return edges;
	}
	
	public Path getFirstNonVisitedPath()
	{
		for(int i = 0; i < edges.size(); i++)
		{
			if(!edges.get(i).getVisited())
				return edges.get(i);				
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "Vertex [edges=" + edges + ", id=" + id + "]";
	}
	
	public boolean hasNonVisitedPaths()
	{
		for(int i = 0; i < edges.size(); i++)
		{
			if(!edges.get(i).getVisited())
				return true;				
		}
		
		return false;
	}

	public void addPath(Path padd) {
		this.edges.add(padd);
	}

	
}
