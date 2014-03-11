import java.util.ArrayList;
import java.util.List;

public class Main {

        // TODO: Use this for calculate*
        private ArrayList<Boolean> walkAble;

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
                ArrayList<Integer> includes = new ArrayList<Integer>();

                Integer highestValue = 0;
                for (Vertex v : vertices) {
                        for (Integer i : v.getEdges()) {
                                if (!includes.contains(i)) {
                                        includes.add(i);
                                }

                                if (i > highestValue) {
                                        highestValue = i;
                                }
                        }
                }

                highestValue++;

                walkAble = new ArrayList<Boolean>(highestValue);

                for (int i = 0; i < highestValue; i++)
                        walkAble.add(i, false);

                for (Integer i : includes)
                        walkAble.set(i, true);

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
                        rList.clear();
                        for (Integer j : vertices.get(i).getEdges()) {
                                if (walkAble.get(j)) {
                                        walkAble.set(j, false);
                                        rList.add(i);
                                        break;
                                }
                        }

                        System.out.println("{ " + i + " }");
                        for (Integer x : rList) {
                                System.out.println(x+1);
                        }
                }


                return null;
        }

        private String calculatePath(List<Vertex> vertices) {
                // TODO Implement Eulerpath logic
                return null;
        }
}
