import java.util.List;

public class Vertex {

	private List<Integer> edges;
	private int id;

	public Vertex(int id, List<Integer> edges)
	{
		this.edges = edges;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getEdgeCount() {
		return edges.size();
	}

	public List<Integer> getEdges() {
		return edges;
	}

	@Override
	public String toString() {
		return "Vertex [edges=" + edges + "]";
	}
}
