package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)

private fun getOrdinalMap(board: GameBoard<Int?>): Map<Cell, Int?> {
    var count = 1
    val valueMap: MutableMap<Cell, Int?> = HashMap()

    for(i in 1..board.width) {
        for(j in 1..board.width) {
            valueMap[board.getCell(i, j)] = count
            count++
        }
    }

    valueMap[Cell(4, 4)] = null
    return valueMap
}

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {


    private val board: GameBoard<Int?> = createGameBoard(4)

    override fun initialize() {
        board.addValues(initializer)
    }

    override fun canMove() = true

    override fun hasWon(): Boolean {
        val ordinalMap  = getOrdinalMap(board)

        return (1..board.width).flatMap{
            i -> (1..board.width).map {
                j ->  Pair(i, j)
            }
        }.map {
            (i, j) -> ordinalMap[board.getCell(i, j)] == board[board.getCell(i, j)]
        }.all { it }
    }

    override fun processMove(direction: Direction) {
        board.moveValuesFifteen(direction)
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

}

/**
 * Move values by the rules of the game of fifteen to the specified direction.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesFifteen(direction: Direction) {
    val emptyCells = this.getAllCells().filter { it -> this[it] == null }
    when (direction) {
        Direction.UP -> swapValues(this.getCell(emptyCells.get(0).i + 1, emptyCells.get(0).j), emptyCells)
        Direction.DOWN -> swapValues(this.getCell(emptyCells.get(0).i - 1, emptyCells.get(0).j), emptyCells)
        Direction.LEFT -> swapValues(this.getCell(emptyCells.get(0).i, emptyCells.get(0).j + 1), emptyCells)
        Direction.RIGHT -> swapValues(this.getCell(emptyCells.get(0).i, emptyCells.get(0).j - 1), emptyCells)
    }
}

private fun GameBoard<Int?>.swapValues(rowToBeUpdated: Cell, emptyCells: List<Cell>) {
    val temp = this[rowToBeUpdated]
    this[rowToBeUpdated] = this[emptyCells.get(0)]
    this[emptyCells.get(0)] = temp
}


private fun GameBoard<Int?>.addValues(initializer: GameOfFifteenInitializer) {
    val data = initializer.initialPermutation
    var count = 0;

    for(i in 1..width) {
        for(j in 1..width) {
            if(count < 15) {
                this[this.getCell(i, j)] = data[count]
                count++
            }
        }
    }

}

