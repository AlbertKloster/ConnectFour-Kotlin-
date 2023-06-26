package connectfour

class Game {
    private var gameStatus = GameStatus.NEXT
    private val players = initPlayers()
    private val boardHandler = initBoardHandler()
    private var activePlayerIndex = 0
    fun run() {
        println("${players[0].name} VS ${players[1].name}")
        println("${boardHandler.board.dimensions.rows} X ${boardHandler.board.dimensions.columns} board")
        makeNextMove()
        printEndMessage()
    }

    private fun printEndMessage() {
        when (gameStatus) {
            GameStatus.WON -> {
                boardHandler.printBoard()
                println("Player ${players[getAnotherPlayerIndex(activePlayerIndex)].name} won")
            }
            GameStatus.DRAW -> {
                boardHandler.printBoard()
                println("It is a draw")
            }
            else -> {}
        }
        println("Game over!")
    }

    private fun makeNextMove() {
        while (gameStatus == GameStatus.NEXT) {
            boardHandler.printBoard()
            throwNextDisk()
            if (gameStatus == GameStatus.NEXT) {
                activePlayerIndex = getAnotherPlayerIndex(activePlayerIndex)
                gameStatus = boardHandler.getStatus()
            }
        }
    }

    private fun getAnotherPlayerIndex(playerIndex: Int) = (playerIndex + 1) % 2

    private fun throwNextDisk() {
        while (true) {
            try {
                println("${players[activePlayerIndex].name}'s turn:")
                val inputString = readln()

                if (inputString == "end") {
                    gameStatus = GameStatus.END
                    return
                }

                if (isNotDigit(inputString)) throw RuntimeException("Incorrect column number")

                boardHandler.addDisc(
                    Disc(
                        if (activePlayerIndex == 0) DiscTypes.FIRST else DiscTypes.SECOND,
                        boardHandler.getNextCoordinateByColumn(inputString.toInt())
                    )
                )

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