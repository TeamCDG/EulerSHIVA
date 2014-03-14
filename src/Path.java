
public class Path {

	private int start;
	private int end;
	private boolean visited = false;
	
	public Path(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if(end == other.start && start == other.end)
			return true;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Path [start=" + start + ", end=" + end + "]";
	}

	public boolean contains(int v)
	{
		return this.start == v || this.end == v;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
	
	public boolean getVisited()
	{
		return this.visited;
	}
	
	public void setVisited(boolean value)
	{
		this.visited = value;
	}

}
