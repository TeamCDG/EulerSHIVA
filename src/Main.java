import java.util.ArrayList;
import java.util.List;

public class Main {

        // TODO: Use this for calculate*
        private ArrayList<boolean[]> visited = new ArrayList<boolean[]>();

        public static void main(String[] args) {

                if (args.length <= 1) {
                        System.err.println("[ERROR] 69: Nein. Einfach nur nein.");
                        System.exit(-1);
                }

                List<Vertex> vertices = new ArrayList<Vertex>();

                for (int i = 0; i < args.length; i++) {

                        List<Integer> connected = new ArrayList<Integer>();

                        for (int x = 0; x < args[i].length(); x++) {
                                connected.add(new Integer(args[i].charAt(x)));
                        }

                        vertices.add(new Vertex(i, connected));
                }

                new Main(vertices);
        }

        public Main(List<Vertex> vertices) {

                int res = checkEuler(vertices);

                // XXX: May be implemented nicer?
                for (Vertex v : vertices) {
                        boolean[] boolAr = new boolean[v.getEdgeCount()];
                        java.util.Arrays.fill(boolAr, false);
                        visited.add(boolAr);
                }

                switch (res) {

                        case 0:  System.out.println(calculateCircle(vertices)); break;
                        case 1:  System.out.println(calculatePath(vertices));   break;
                        case -1: System.exit(-1);                               break;
                }
        }

        private int checkEuler(List<Vertex> vertices) {

                int odd = 0;

                for (int i = 0; i < vertices.size(); i++) {

                        if ((vertices.get(i).getEdgeCount() % 2) != 0)
                                odd++;
                }

                switch (odd) {
                        case 0:  System.out.println("[INFO] Eulercircle possible"); return 0;
                        case 2:  System.out.println("[INFO] Eulerpath possible");   return 1;
                        default: System.err.println("[ERROR] 33: Fuck you.");       return -1;
                }
        }

        private String calculateCircle(List<Vertex> vertices) {
                // TODO: Implement Eulercircle logic
                return null;
        }

        private String calculatePath(List<Vertex> vertices) {
                // TODO Implement Eulerpath logic
                return null;
        }
}
