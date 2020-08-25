package minesweeper.view

import minesweeper.domain.Board
import minesweeper.domain.Point

object ResultView {
    fun resultBoard(board: Board, lastX: Int) = board.getPoints().forEach { draw(it, lastX) }

    private fun draw(point: Point, lastX: Int) {
        if (point.isLastX(lastX)) {
            println(drawWhat(point))
        } else {
            print(drawWhat(point))
            print(" ")
        }
    }

    private fun drawWhat(point: Point): String {
        if (point.isOpen) {
            return drawOpen(point)
        }
        return "C"
    }

    private fun drawOpen(point: Point): String {
        if (point.isMine() ?: throw IllegalArgumentException("해당 point는 open되어 있지 않습니다.")) {
            return "*"
        }
        return point.mineCount.toString()
    }

    fun resultGame(isWin: Boolean) {
        if (isWin) {
            println("당신은 모든 지뢰를 피하셨습니다. 승리를 축하합니다!!")
        } else {
            println("당신은 지뢰를 오픈했습니다. 패배하셧습니다!!")
        }
    }
}
