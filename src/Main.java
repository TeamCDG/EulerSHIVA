import java.util.ArrayList;
import java.util.List;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length == 0) {
			System.err.println("[ERROR] 69: Nein. Einfach nur nein.");
			System.exit(-1);
		}
		
		List<Knot> knots = new ArrayList<Knot>();
		for (int i = 0; i < args.length; i++) {
			
			List<Integer> connected = new ArrayList<Integer>();
			for(int x = 0; x < args[i].length(); x++) {
				connected.add(new Integer(args[i].charAt(x)));
			}
			knots.add(new Knot(i, connected));
		}
		
		new Main(knots);
	}
	
	public Main(List<Knot> knots) {
		
		int odd = 0;
		for (int i = 0; i <knots.size(); i++) {
			if(knots.get(i).getKnotCount() % 2 != 0) {
				odd++; //increments odd
			}
		}
		
		if(odd == 0)
			System.out.println("[INFO] Eulercircle possible");
		else if (odd == 2)
			System.out.println("[INFO] Eulerpath possible");
		else
			System.err.println("[ERROR] 33: Fuck you.");
		
		
		
	}

}
