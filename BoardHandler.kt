package connectfour

class BoardHandler(val board: Board) {

    private var lastAddedDisc: Disc? = null

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
        lastAddedDisc = disc
    }

    private fun isFull(disc: Disc): Boolean {
        val discsInColumn = board.discs.filter { it.coordinate.column == disc.coordinate.column }
        if (discsInColumn.isEmpty()) {
            return false
        }
        return discsInColumn.maxOf { it.coordinate.row } >= board.dimensions.rows
    }

    fun getStatus(): GameStatus {
        if (isDraw()) return GameStatus.DRAW
        if (hasWon()) return GameStatus.WON
        return GameStatus.NEXT
    }

    private fun hasWon(): Boolean {
        if (lastAddedDisc == null) return false
        return isFourUpDown() || isFourLeftRight() || isFourUpLeftDownRight() || isFourUpRightDownLeft()
    }

    private fun isFourUpDown(): Boolean {
        return countDown() + countUp() >= 3
    }

    private fun isFourLeftRight(): Boolean {
        return countLeft() + countRight() >= 3
    }

    private fun isFourUpLeftDownRight(): Boolean {
        return countUpLeft() + countDownRight() >= 3
    }

    private fun isFourUpRightDownLeft(): Boolean {
        return countUpRight() + countDownLeft() >= 3
    }

    private fun countUp(): Int {
        if (lastAddedDisc == null) return 0

        var count = 0
        var currentDisc = lastAddedDisc
        while (true) {
            currentDisc = getDiscByCoordinate(Coordinate(currentDisc!!.coordinate.row + 1, currentDisc.coordinate.column))
            if (currentDisc == null) return count
            if (currentDisc.discTypes != lastAddedDisc!!.discTypes) return count
            count++
        }
    }

    private fun countDown(): Int {
        if (lastAddedDisc == null) return 0

        var count = 0
        var currentDisc = lastAddedDisc
        while (true) {
            currentDisc = getDiscByCoordinate(Coordinate(currentDisc!!.coordinate.row - 1, currentDisc.coordinate.column))
            if (currentDisc == null) return count
            if (currentDisc.discTypes != lastAddedDisc!!.discTypes) return count
            count++
        }
    }

    private fun countLeft(): Int {
        if (lastAddedDisc == null) return 0

        var count = 0
        var currentDisc = lastAddedDisc
        while (true) {
            currentDisc = getDiscByCoordinate(Coordinate(currentDisc!!.coordinate.row, currentDisc.coordinate.column - 1))
            if (currentDisc == null) return count
            if (currentDisc.discTypes != lastAddedDisc!!.discTypes) return count
            count++
        }
    }

    private fun countRight(): Int {
        if (lastAddedDisc == null) return 0

        var count = 0
        var currentDisc = lastAddedDisc
        while (true) {
            currentDisc = getDiscByCoordinate(Coordinate(currentDisc!!.coordinate.row, currentDisc.coordinate.column + 1))
            if (currentDisc == null) return count
            if (currentDisc.discTypes != lastAddedDisc!!.discTypes) return count
            count++
        }
    }

    private fun countUpLeft(): Int {
        if (lastAddedDisc == null) return 0

        var count = 0
        var currentDisc = lastAddedDisc
        while (true) {
            currentDisc = getDiscByCoordinate(Coordinate(currentDisc!!.coordinate.row + 1, currentDisc.coordinate.column - 1))
            if (currentDisc == null) return count
            if (currentDisc.discTypes != lastAddedDisc!!.discTypes) return count
            count++
        }
    }

    private fun countDownRight(): Int {
        if (lastAddedDisc == null) return 0

        var count = 0
        var currentDisc = lastAddedDisc
        while (true) {
            currentDisc = getDiscByCoordinate(Coordinate(currentDisc!!.coordinate.row - 1, currentDisc.coordinate.column + 1))
            if (currentDisc == null) return count
            if (currentDisc.discTypes != lastAddedDisc!!.discTypes) return count
            count++
        }
    }

    private fun countUpRight(): Int {
        if (lastAddedDisc == null) return 0

        var count = 0
        var currentDisc = lastAddedDisc
        while (true) {
            currentDisc = getDiscByCoordinate(Coordinate(currentDisc!!.coordinate.row + 1, currentDisc.coordinate.column + 1))
            if (currentDisc == null) return count
            if (currentDisc.discTypes != lastAddedDisc!!.discTypes) return count
            count++
        }
    }

    private fun countDownLeft(): Int {
        if (lastAddedDisc == null) return 0

        var count = 0
        var currentDisc = lastAddedDisc
        while (true) {
            currentDisc = getDiscByCoordinate(Coordinate(currentDisc!!.coordinate.row - 1, currentDisc.coordinate.column - 1))
            if (currentDisc == null) return count
            if (currentDisc.discTypes != lastAddedDisc!!.discTypes) return count
            count++
        }
    }

    private fun isDraw() = board.discs.size >= board.dimensions.rows * board.dimensions.columns

    fun getNextCoordinateByColumn(column: Int): Coordinate {
        if (column !in 1..board.dimensions.columns)
            throw RuntimeException("The column number is out of range (1 - ${board.dimensions.columns})")

        val discsInColumn = board.discs.filter { disc -> disc.coordinate.column == column }

        if (discsInColumn.isEmpty()) return Coordinate(1, column)

        return Coordinate(discsInColumn.maxOf { it.coordinate.row } + 1, column)
    }

    fun clear() {
        board.discs.clear()
    }
}