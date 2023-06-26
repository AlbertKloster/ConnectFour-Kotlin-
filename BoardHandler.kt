package connectfour

class BoardHandler(val board: Board) {
    fun printBoard() {
        (1..board.dimensions.columns).forEach { print(" $it") }
        for (row in board.dimensions.rows downTo 1) {
            print("\n║")
            for (column in 1..board.dimensions.columns) {
                val discByCoordinate = getDiscByCoordinate(Coordinate(row, column))
                printDiscSymbol(discByCoordinate)
                print("║")
            }
        }
        print("\n╚")
        print("═╩".repeat(board.dimensions.columns - 1))
        println("═╝")

    }

    private fun printDiscSymbol(disc: Disc?) {
        print(disc?.discTypes?.char ?: " ")
    }

    private fun getDiscByCoordinate(coordinate: Coordinate): Disc? {
        return board.discs.find { disc: Disc -> disc.coordinate.column == coordinate.column && disc.coordinate.row == coordinate.row }
    }

    fun addDisc(disc: Disc) {
        if (isFull(disc))
            throw RuntimeException("Column ${disc.coordinate.column} is full")
        board.add(disc)
    }

    private fun isFull(disc: Disc): Boolean {
        val discsInColumn = board.discs.filter { it.coordinate.column == disc.coordinate.column }
        if (discsInColumn.isEmpty()) {
            return false
        }
        return discsInColumn.maxOf { it.coordinate.row } >= board.dimensions.rows
    }

    fun getStatus(): GameStatus {
        return GameStatus.NEXT
    }

    fun getNextCoordinateByColumn(column: Int): Coordinate {
        if (column !in 1..board.dimensions.columns)
            throw RuntimeException("The column number is out of range (1 - ${board.dimensions.columns})")

        val discsInColumn = board.discs.filter { disc -> disc.coordinate.column == column }

        if (discsInColumn.isEmpty()) return Coordinate(1, column)

        return Coordinate(discsInColumn.maxOf { it.coordinate.row } + 1, column)
    }
}