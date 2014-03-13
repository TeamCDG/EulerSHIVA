import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

        private LinkedList<Integer> useAbleEdges;

        public static void main(String[] args) {

                if (args.length <= 1) {
                        System.err.println("[ERROR] 69: Nein. Einfach nur nein.");
                        System.exit(-1);
                }

                List<Vertex> vertices = new ArrayList<Vertex>();

                for (int i = 0; i < args.length; i++) {

                        List<Integer> connected = new ArrayList<Integer>();

                        for (int x = 0; x < args[i].length(); x++) {
                                connected.add(new Integer((args[i].charAt(x) - 48)));
                        }

                        vertices.add(new Vertex(i, connected));
                }

                new Main(vertices);
        }

        public Main(List<Vertex> vertices) {

                int res = checkEuler(vertices);
                LinkedList<Integer> includes = new LinkedList<Integer>();

                for (Vertex v : vertices) {

                        for (Integer i : v.getEdges()) {

                                if (!includes.contains(i))
                                        includes.add(i);

                        }

                }

                useAbleEdges = new LinkedList<Integer>(includes);

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

        private static ArrayList<Integer> rList = new ArrayList<Integer>();

        private void calculateCircle(int vertex, List<Vertex> vertices) {

                if (vertex < vertices.size()) {
                        for (Integer i : vertices.get(vertex).getEdges()) {
                                if (useAbleEdges.contains(i)) {
                                        useAbleEdges.remove(i);
                                        rList.add(vertex + 1);
                                        calculateCircle(++vertex, vertices);
                                        break;
                                }
                        }
                }
        }

        private String calculateCircle(List<Vertex> vertices) {
                for (int i = 0; i < vertices.size(); i++){
                        calculateCircle(i, vertices);
                }

                for (Integer in : rList){
                        System.out.println(in);
                }

                return null;
        }

        private String calculatePath(List<Vertex> vertices) {
                // TODO Implement Eulerpath logic
                return null;
        }
}
