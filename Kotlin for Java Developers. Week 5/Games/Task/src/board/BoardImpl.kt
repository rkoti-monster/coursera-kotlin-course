package board

import board.Direction.*
import java.lang.IllegalArgumentException

class SquareBoardImpl(override val width: Int): SquareBoard {
    private val board = MutableList(width) {
        MutableList(width) {
            Cell(1, 1)
        }
    }

    init {
        for (i in 1 .. width) {
            for (j in 1 .. width) {
                board[i - 1][j - 1] = Cell(i, j)
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i in 1 .. width && j in 1 .. width) {
            return board[i - 1][j - 1]
        }
        return null
    }

    override fun getCell(i: Int, j: Int): Cell {
        if (i in 1 .. width && j in 1 .. width) {
            return board[i - 1][j - 1]
        }
        throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        return board.flatMap { row ->
            row.map {
                it
            }
        }
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        if( i !in 1..width) {
            return emptyList()
        }

        return jRange.mapNotNull {
            if (it in 1..width) board[i - 1][it - 1] else null
        }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        if( j !in 1..width) {
            return emptyList()
        }

        return iRange.mapNotNull {
            if (it in 1..width) board[it - 1][j - 1] else null
        }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val newI =  when (direction) {
            UP -> this.i - 1
            DOWN -> this.i + 1
            else -> this.i
        }

        val newJ =  when (direction) {
            LEFT -> this.j - 1
            RIGHT -> this.j + 1
            else -> this.j
        }

        if(newI in 1 .. width && newJ in 1 .. width) {
            return board[newI - 1][newJ - 1]
        }
        return null
    }
}

class GameBoardImpl<T>(b: SquareBoard): GameBoard<T>, SquareBoard by b {
    private val data: MutableMap<Cell, T?> = HashMap()

    init {
        for (i in 1..width) {
            for(j in 1..width) {
                data[getCell(i, j)] = null
            }
        }
    }

    override fun get(cell: Cell): T? {
        return data.getOrDefault(cell, null)
    }

    override fun set(cell: Cell, value: T?) {
        data[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return data.filter {
            entry -> predicate.invoke(entry.value)
        }.keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return data.filter {
            entry -> predicate.invoke(entry.value)
        }.keys.firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return data.any {
            entry -> predicate.invoke(entry.value)
        }
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return data.all {
            entry -> predicate.invoke(entry.value)
        }
    }

}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl<T>(createSquareBoard(width))

