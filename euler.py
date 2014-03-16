import sys

###############################################################################
##                                  Path                                     ##
###############################################################################

class Path(object):

    def __init__(self, start, end):
        self.start = int(start)
        self.end   = int(end)
        self.visited = False

    def hashCode(self): # hash()
        pass

    def equals(self): # __eq__ and __ne__
        pass

    def __str__(self) -> "string":
        return "Path [start=" + str(self.start) + ", end=" + str(self.end) + "]"

###############################################################################
##                                 Vertex                                    ##
###############################################################################

class Vertex(object):

    def __init__(self, iD, edges):
        self.iD = int(iD)
        self.edges = list(edges)

    def getEdgeCount(self) -> "int":
        return len(self.edges)

    def getFirstNonVisitedPath(self):

        for i in range(len(self.edges)):
            if not self.edges[i].visited:
                return self.edges[i]

        return None

    def __str__(self) -> "string":
        return "Vertex [edges=" + str(self.edges) + "]"

    def hasNonVisitedPaths(self) -> "boolean":

        for i in range(len(self.edges)):
            if not self.edges[i].visited:
                return True

        return False

    def addPath(self, padd):
        self.edges.append(padd)

###############################################################################
##                                   Main                                    ##
###############################################################################

class Main(object):

    def __init__(self, vertices):
        self.init(vertices)

    def init(self, vertices):
        res = self.checkEuler(vertices)

        if (res == 0):
            print("[INFO] Got result circle: ", self.calculateCircle(vertices))
        elif (res == 1):
            print("[INFO] Got result path: ", self.calculatePath(vertices))
        else:
            print("Unknown error in `checkEuler(vertices)'", file=sys.stderr)
            exit(-1)

    def checkEuler(self, vertices) -> "int":
        odd = 0

        for i in range(len(vertices)):
            if ((vertices[i].getEdgeCount() % 2) != 0):
                odd += 1

        if (odd == 0):
            print("[INFO] Eulercircle possible")
            return 0
        elif (odd == 2):
            print("[INFO] Eulerpath possible")
            return 1
        else:
            print("[ERROR] 33: Not possible", file=sys.stderr)
            return -1

    def setVisited(self, start, end, vertices):
        ref = Path(start, end)
        printed = False

        for i in range(len(vertices)):

            if ref in vertices[i].edges:
                tmp = Path(-1,-1)
                tmp = vertices[i].edges.get(vertices[i].edges.index(ref))
                tmp.setVisited(True)

                if not printed:
                    print("[INFO] Path ", tmp.start, " ----- ", tmp.end, " marked as visited!")
                    printed = True;

    def getVertexByiD(self, iD, vertices) -> "Vertex":
        iD = int(iD)
        vertices = list(vertices)

        for i in range(len(vertices)):

            if vertices[i].iD == iD and vertices[i].getFirstNonVisitedPath() is not None:
                return vertices[i]

        return None;

    def findVertexBetween(self, v1, v2, vertices) -> "Vertex":
        pPS = [] #*v1.getEdgeCount()

        for i in range(len(v1.edges)):

            if not v1.edges[i].getVisited():
                pPS.append(self.getVertexByiD(v1.edges[i].end, vertices))

        for i in range(len(pPS)):

            for x in range(len(pPS[i].edges)):

                if not pPS[i].edges[x].visited and pPS[i].edges[x].end == v2.iD:

                    print("[INFO] Calculated to use point ", pPS[i].iD)

                    return pPS[i]

    def arePathLeft(self, vertices) -> "boolean":

        for i in range(len(vertices)):

            if vertices[i].hasNonVisitedPaths():
                return True

        return False


    def getFirstWithPathsAv(self, vertices) -> "Vertex":

        for i in range(len(vertices)):

            if vertices[i].hasNonVisitedPaths():
                return vertices[i]

        return None

    def calculateCircle2(self, start,  vertices) -> "String":
        last = self.getVertexByiD(start.getFirstNonVisitedPath().end, vertices)
        result = str(start.iD) + "->" + str(last.iD)
        self.setVisited(start.iD, last.iD, vertices)

        while last.iD is not start.iD:

            result += "->"

            liD = last.iD
            last = self.getVertexByiD(last.getFirstNonVisitedPath().end, vertices)
            self.setVisited(liD, last.iD, vertices)

            result += str(last.iD)

        print("[INFO] Partial result: ", result)

        return result+"";

    def calculateCircle(self, vertices) -> "String":
        start = vertices[0]

        print("[INFO] Starting circle calculation. Using ", start.iD, "as starting point")

        steps = [] #*(len(vertices)*10)

        while self.arePathLeft(vertices):

            steps.append(self.calculateCircle2(start, vertices))
            start = self.getFirstWithPathsAv(vertices)

            if start is not None:
                print("[INFO] Using ", start.iD, "as next point.")

            result = steps[0]

            for i in range(1, len(steps)):

                sub = steps[i].split("->")[0] + "->"
                result = result[0:result.rindex(sub)] + steps[i] + "->" + result[result.rindex(sub) + len(sub):len(result)]

        return result

    def calculatePath(self, vertices) -> "string":

        ue = self.getFirstUneven(vertices)
        padd1 = Path(ue.iD, len(vertices)+1)
        paddI1 = Path(len(vertices)+1, ue.iD)

        ue.addPath(padd1)

        ue = self.getFirstUneven(vertices)
        padd2 = Path(ue.iD, len(vertices)+1)
        paddI2 = Path(len(vertices)+1, ue.iD)

        ue.addPath(padd2)

        addpI = []
        addpI.append(paddI1)
        addpI.append(paddI2)

        add = Vertex(len(vertices)+1, addpI)
        vertices.insert(0, add)

        print("[INFO] Starting path calculation. Using ", add.iD, " as added starting point.")

        result = self.calculateCircle(vertices)

        print("[INFO] Removing generated point ", add.iD)

        return result.replace(add.iD + "->", "").replace("->" + add.iD, "")

    def getFirstUneven(self, vertices) -> "Vertex":

        for i in range(len(vertices)):
            if (vertices[i].getEdgeCount() % 2) != 0:
                return vertices[i]

        return None

def main():

    if (len(sys.argv) <= 2):
        print("Usage:\tpython Main.py [Vertex] [Vertex] ...")
        print("\tVertex = [Edge:Edge:Edge:...]")
        print("\tEdge = an integer")
        exit(-1)

    vertices = []

    for i in range(1, len(sys.argv)):

        knotiD = int(sys.argv[i].split(":")[0])
        connections = sys.argv[i].split(":")[1].split(";")
        connected = [] #*connections

        for x in range(len(connections)):
            connected.append(Path(knotiD, int(connections[x])));
            #connected += [Path(knotiD, int(connections[x])) for x in range(len(connections))]

        vertices.append(Vertex(knotiD, connected))

    Main(vertices)

if (__name__ == '__main__'):
    main()
