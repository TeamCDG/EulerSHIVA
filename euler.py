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

    def __init__(self, id, edges):
        self.id = int(id)
        self.edges = list(edges)

    def getEdgeCount(self) -> "int":
        return len(self.edges)

    def getFirstNonVisitedPath(self):

        for i in range(len(self.edges)):
            if not self.edges[i].visited:
                return self.edges[i]

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

    def generateCircle(self, count):
        ret = []

        if count == 1:
            print("[ERROR] 418: Need to be at least 2", file=sys.stderr)
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
                tmp = vertices[i].edges().get(vertices[i].edges().index(ref))
                tmp.setVisited(True)

                if not printed:
                    print("[INFO] Path ", tmp.start, " ----- ", tmp.end, " marked as visited!")
                    printed = True;

    def getVertexById(self, id, vertices) -> "Vertex":
        id = int(id)
        vertices = list(vertices)

        for i in range(len(vertices)):

            if vertices[i].id == id and vertices[i].getFirstNonVisitedPath() != None:
                return vertices[i]

        return None;

    def findVertexBetween(self, v1, v2, vertices) -> "Vertex":
        pPS = [] #*v1.getEdgeCount()

        for i in range(len(v1.edges)):

            if not v1.edges[i].getVisited():
                pPS.append(self.getVertexById(v1.edges[i].getEnd(), vertices))

        for i in range(len(pPS)):

            for x in range(len(pPS[i].edges)):

                if not pPS[i].edges[x].getVisited() and pPS[i].edges[x].getEnd() == v2.id:

                    print("[INFO] Calculated to use point ", pPS[i].id)

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

    def calculateCircle2(self, start,  vertices) -> "String":
        last = self.getVertexById(start.getFirstNonVisitedPath().end, vertices)
        result = str(start.id) + "->" + str(last.id)
        self.setVisited(start.id, last.id, vertices)

        while last.id != start.id:

            result += "->"

            lid = last.id
            last = self.getVertexById(last.getFirstNonVisitedPath().end, vertices)
            self.setVisited(lid, last.id, vertices)

            result += str(last.id)

        print("[INFO] Partial result: ", result)

        return result+"";

    def calculateCircle(self, vertices) -> "String":
        start = vertices[0]

        print("[INFO] Starting circle calculation. Using ", start.id, "as starting point")

        steps = [] #*(len(vertices)*10)

        while self.arePathLeft(vertices):

            steps.append(self.calculateCircle2(start, vertices))
            start = self.getFirstWithPathsAv(vertices)

            if start is not None:
                print("[INFO] Using ", start.id, "as next point.")

            result = steps[0]

            for i in range(1, len(steps)):

                sub = steps[i].split("->")[0] + "->"
                result = result[0:result.rindex(sub)] + steps[i] + "->" + result[result.rindex(sub) + len(sub) : len(result)]

        return result

    def calculatePath(self, vertices) -> "string":

        ue = self.getFirstUneven(vertices)
        padd1 = Path(ue.id, len(vertices)+1)
        paddI1 = Path(len(vertices)+1, ue.id)

        ue.addPath(padd1)

        ue = self.getFirstUneven(vertices)
        padd2 = Path(ue.id, len(vertices)+1)
        paddI2 = Path(len(vertices)+1, ue.id)

        ue.addPath(padd2)

        addpI = []
        addpI.append(paddI1)
        addpI.append(paddI2)

        add = Vertex(len(vertices)+1, addpI)
        vertices.insert(0, add)

        print("[INFO] Starting path calculation. Using ", add.id, " as added starting point.")

        result = self.calculateCircle(vertices)

        print("[INFO] Removing generated point ", add.id)

        return result.replace(add.id + "->", "").replace("->" + add.id, "")

    def getFirstUneven(self, vertices) -> "Vertex":

        for i in range(len(vertices)):
            if (vertices[i].getEdgeCount() % 2) != 0:
                return vertices[i]

def main():

    if (len(sys.argv) <= 2):
        print("Usage:\tpython Main.py [Vertex] [Vertex] ...")
        print("\tVertex = [Edge:Edge:Edge:...]")
        print("\tEdge = an integer")
        exit(-1)

    vertices = []

    for i in range(1, len(sys.argv)):

        knotId = int(sys.argv[i].split(":")[0])
        connections = sys.argv[i].split(":")[1].split(";")
        connected = [] #*connections

        for x in range(len(connections)):
            connected.append(Path(knotId, int(connections[x])));
            #connected += [Path(knotId, int(connections[x])) for x in range(len(connections))]

        vertices.append(Vertex(knotId, connected))

    Main(vertices)

if (__name__ == '__main__'):
    main()
