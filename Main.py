from Knot import Knot
import sys

class Main(object):

    def __init__(self, knots):
        self.knots = list(knots)
        self.init(self.knots)

    def init(self, knots):
        res = self.checkEuler(knots)

        if (res == 0):
            print(self.calculateCircle(knots))
        elif (res == 1):
            print(self.calculatePath(knots))
        else:
            print("Unknown error in `checkEuler(knots)'", file=sys.stderr)
            exit(-1)

    def checkEuler(self, knots) -> "int":
        odd = 0

        for i in range(len(self.knots)):
            if ((self.knots[i].getKnotCount() % 2) != 0):
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


    def calculatePath(self, knots) -> "String":
        pass

    def calculateCircle(self, knots) -> "String":
        pass

def main():
    if (len(sys.argv) == 1):
        print("Usage:\tpython Main.py [Knot]...")
        exit(-1)

    knots = []

    for i in range(1, len(sys.argv)):
        connected = []

        for x in range(0, len(sys.argv[i])):
            connected.append(str(sys.argv[i][x]))

        knots.append(Knot(i, connected))

    Main(knots)

if (__name__ == '__main__'):
    main()
