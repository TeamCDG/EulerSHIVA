class Knot(object):

    def __init__(self, id, cKnots):
        self.id = int(id)
        self.cKnots = list(cKnots)

    def getKnotCount(self) -> "int":
        return len(self.cKnots)

    def __str__(self) -> "string":
        return "Knot [cKnots=" + str(self.cKnots) + "]"
