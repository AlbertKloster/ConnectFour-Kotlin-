package connectfour

class Game {
    private var gameStatus = GameStatus.NEXT
    private val players = initPlayers()
    private val boardHandler = initBoardHandler()
    private var activePlayerIndex = 0
    fun run() {
        println("${players[0].name} VS ${players[1].name}")
        println("${boardHandler.board.dimensions.rows} X ${boardHandler.board.dimensions.columns} board")
        while (gameStatus != GameStatus.END) {
            boardHandler.printBoard()
            when (gameStatus) {
                GameStatus.NEXT -> makeNextMove()
                else -> {}
            }
            gameStatus = if (gameStatus == GameStatus.END) gameStatus else boardHandler.getStatus()
        }
        println("Game over!")
    }

    private fun makeNextMove() {
        while (true) {
            try {
                println("${players[activePlayerIndex].name}'s turn:")
                val inputString = readln()

                if (inputString == "end") {
                    gameStatus = GameStatus.END
                    return
                }

                if (isNotDigit(inputString)) throw RuntimeException("Incorrect column number")

                val nextCoordinate = boardHandler.getNextCoordinateByColumn(inputString.toInt())
                val discTypes = if (activePlayerIndex == 0) DiscTypes.FIRST else DiscTypes.SECOND
                boardHandler.addDisc(Disc(discTypes, nextCoordinate))
                activePlayerIndex = (activePlayerIndex + 1) % 2

                break
            } catch (e: RuntimeException) {
                println(e.message)
            }
        }

    }

    private fun isNotDigit(inputString: String) = !inputString.matches(Regex("\\d+"))

    private fun initPlayers(): List<Player> {
        println("Connect Four")
        println("First player's name:")
        val firstPlayer = Player(readln())
        println("Second player's name:")
        val secondPlayer = Player(readln())
        return listOf(firstPlayer, secondPlayer)
    }

    private fun initBoardHandler(): BoardHandler {
        while (true) {
            try {
                println("Set the board dimensions (Rows x Columns)")
                println("Press Enter for default (6 x 7)")
                return BoardHandler(Board(input.readDimensions()))
            } catch (e: RuntimeException) {
                println(e.message)
            }
        }
    }

}