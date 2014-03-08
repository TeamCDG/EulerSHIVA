import java.util.ArrayList;
import java.util.List;

public class Main {

        public static void main(String[] args) {

                if (args.length == 0 ^ args.length == 1) {
                        System.err.println("[ERROR] 69: Nein. Einfach nur nein.");
                        System.exit(-1);
                }

                List<Knot> knots = new ArrayList<Knot>();

                for (int i = 0; i < args.length; i++) {

                        List<Integer> connected = new ArrayList<Integer>();

                        for (int x = 0; x < args[i].length(); x++) {
                                connected.add(new Integer(args[i].charAt(x)));
                        }

                        knots.add(new Knot(i, connected));
                }

                new Main(knots);
        }

        public Main(List<Knot> knots) {

                int res = checkEuler(knots);

                switch (res) {

                        case 0:  System.out.println(calculateCircle(knots)); break;
                        case 1:  System.out.println(calculatePath(knots));   break;
                        case -1: System.exit(-1);                            break;
                }
        }

        private int checkEuler(List<Knot> knots) {

                int odd = 0;

                for (int i = 0; i < knots.size(); i++) {

                        if ((knots.get(i).getKnotCount() % 2) != 0)
                                odd++;
                }

                switch (odd) {
                        case 0:  System.out.println("[INFO] Eulercircle possible");
                                 return 0;
                        case 2:  System.out.println("[INFO] Eulerpath possible");
                                 return 1;
                        default: System.err.println("[ERROR] 33: Fuck you.");
                                 return -1;
                }
        }

        private String calculateCircle(List<Knot> knots) {
                // TODO: Implement Eulercircle logic
                return null;
        }

        private String calculatePath(List<Knot> knots) {
                // TODO Implement Eulerpath logic
                return null;
        }
}
