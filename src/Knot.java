import java.util.List;


public class Knot {

	private List<Integer> cKnots;
	private int id;
	
	public Knot(int id, List<Integer> cKnots)
	{
		this.cKnots = cKnots;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public int getKnotCount()
	{
		return cKnots.size();
	}

	public List<Integer> getcKnots() {
		return cKnots;
	}

	@Override
	public String toString() {
		return "Knot [cKnots=" + cKnots + "]";
	}
	
	
}
