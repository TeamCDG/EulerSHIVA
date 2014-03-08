import sys

###############################################################################
##                                 Vertex                                    ##
###############################################################################

class Vertex(object):

    def __init__(self, id, edges):
        self.id = int(id)
        self.edges = list(edges)

    def getEdgeCount(self) -> "int":
        return len(self.edges)

    def __str__(self) -> "string":
        return "Vertex [edges=" + str(self.edges) + "]"

###############################################################################
##                                   Main                                    ##
###############################################################################

class Main(object):

    def __init__(self, vertices):
        self.vertices = list(vertices)
        self.init(self.vertices)

    def init(self, vertices):
        res = self.checkEuler(vertices)

        if (res == 0):
            print(self.calculateCircle(vertices))
        elif (res == 1):
            print(self.calculatePath(vertices))
        else:
            print("Unknown error in `checkEuler(vertices)'", file=sys.stderr)
            exit(-1)

    def checkEuler(self, vertices) -> "int":
        odd = 0

        for i in range(len(self. vertices)):
            if ((self.vertices[i].getEdgeCount() % 2) != 0):
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


    def calculatePath(self, vertices) -> "String":
        pass

    def calculateCircle(self, vertices) -> "String":
        pass

def main():
    if (len(sys.argv) > 2):
        print("Usage:\tpython Main.py [Vertex] [Vertex] ...")
        print("\tVertex = String of Edges e.g. 123")
        print("\tEdge = Specifer e.g. a Number")
        exit(-1)

    vertices = []

    for i in range(1, len(sys.argv)):
        connected = []

        for x in range(0, len(sys.argv[i])):
            connected.append(str(sys.argv[i][x]))

        vertices.append(Vertex(i, connected))

    Main(vertices)

if (__name__ == '__main__'):
    main()
