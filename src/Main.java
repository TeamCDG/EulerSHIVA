import java.util.ArrayList;
import java.util.List;

public class Main {

        // TODO: Use this for calculate*
        private boolean[] walkAble;

        public static void main(String[] args) {

                if (args.length <= 1) {
                        System.err.println("[ERROR] 69: Nein. Einfach nur nein.");
                        System.exit(-1);
                }

                List<Vertex> vertices = new ArrayList<Vertex>();

                for (int i = 0; i < args.length; i++) {

                        List<Integer> connected = new ArrayList<Integer>();

                        for (int x = 0; x < args[i].length(); x++) {
                                //FIXME: connected adds the int representation of the char
                                connected.add(new Integer(args[i].charAt(x)));
                        }

                        vertices.add(new Vertex(i, connected));
                }

                new Main(vertices);
        }

        public Main(List<Vertex> vertices) {

                int res = checkEuler(vertices);
                ArrayList<Integer> includes = new ArrayList<Integer>();

                for (Vertex v : vertices) {
                        for (Integer i : v.getEdges()) {
                                // XXX: Delete me, please
                                System.out.println(i);
                                if (!includes.contains(i)) {
                                        includes.add(i);
                                }
                        }
                }

                walkAble = new boolean[includes.size()];
                java.util.Arrays.fill(walkAble, true);

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
                ArrayList<Integer> rList = new ArrayList<Integer>();

                // FIXME: Logic not correct with the booleans, will make it later

                for (int i = 0; i < vertices.size(); i++) {
                        for (int j = 0; j < vertices.get(i).getEdgeCount(); j++) {
                                if (walkAble[j]) {
                                        walkAble[j] = false;
                                        rList.add(i);
                                }
                        }
                }

                for (Integer i : rList) {
                        System.out.println(i+1);
                }

                return null;
        }

        private String calculatePath(List<Vertex> vertices) {
                // TODO Implement Eulerpath logic
                return null;
        }
}
