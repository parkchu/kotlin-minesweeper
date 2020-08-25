package minesweeper.domain

class Board(width: Int, length: Int, private val mines: Int = 0) {
    private val coordinates = Coordinates(width, length)
    private val points = Points(coordinates, coordinates.makeMineCoordinates(mines))
    var isPlaying = true
        private set

    fun findPoint(x: Int, y: Int): Point? = points.findPoint(x, y)

    fun getPoints(): List<Point> {
        return points.allPoints
    }

    fun open(coordinate: Coordinate) {
        val point = findPoint(coordinate.x, coordinate.y)!!
        point.openPoint()
        endGame(point.isMine()!! || isPlayerWin(point))
        if (point.mineCount == OPEN_AROUND_NUMBER) {
            getAroundPoint(point).forEach { againOpen(it) }
        }
    }

    private fun getAroundPoint(point: Point): List<Point> {
        return Direction.values().filter { isRealPoint(point.coordinate move it) }.map {
            val coordinate = point.coordinate move it
            findPoint(coordinate.x, coordinate.y)!!
        }
    }

    private fun isRealPoint(coordinate: Coordinate): Boolean {
        return findPoint(coordinate.x, coordinate.y) != null
    }

    private fun againOpen(point: Point) {
        if (!point.isOpen) {
            open(point.coordinate)
        }
    }

    private fun endGame(isGameOver: Boolean) {
        if (isGameOver) {
            isPlaying = false
        }
    }

    fun isPlayerWin(point: Point): Boolean {
        if (point.isMine() ?: throw IllegalArgumentException("해당 point는 open되어 있지 않습니다.")) {
            return false
        }
        return points.getClosePointsSize() == mines
    }

    companion object {
        const val OPEN_AROUND_NUMBER = 0
    }
}
