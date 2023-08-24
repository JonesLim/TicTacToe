package com.jones.myapplication.utils

object GameLogic {

    val board: Array<Array<Char>> = arrayOf(
        arrayOf('?', '?', '?'),
        arrayOf('?', '?', '?'),
        arrayOf('?', '?', '?')
    )
    var flag = true
    fun printBoard() {
        board.forEach {
            for (ch in it) {
                print("$ch ")
            }
            print("\n")
        }
    }

    fun getStatus(grid: Array<Array<Char>>): Int {
        if (grid[0][0] != '?' && grid[0][0] == grid[1][0] && grid[1][0] == grid[2][0])
            return if (grid[0][0] == 'x') 1 else -1
        if (grid[0][1] != '?' && grid[0][1] == grid[1][1] && grid[1][1] == grid[2][1])
            return if (grid[0][1] == 'x') 1 else -1
        if (grid[0][2] != '?' && grid[0][2] == grid[1][2] && grid[1][2] == grid[2][2])
            return if (grid[0][2] == 'x') 1 else -1
        if (grid[0][0] != '?' && grid[0][0] == grid[0][1] && grid[0][1] == grid[0][2])
            return if (grid[0][0] == 'x') 1 else -1
        if (grid[1][0] != '?' && grid[1][0] == grid[1][1] && grid[1][1] == grid[1][2])
            return if (grid[1][0] == 'x') 1 else -1
        if (grid[2][0] != '?' && grid[2][0] == grid[2][1] && grid[2][1] == grid[2][2])
            return if (grid[2][0] == 'x') 1 else -1
        if (grid[0][0] != '?' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
            return if (grid[0][0] == 'x') 1 else -1
        if (grid[0][2] != '?' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0])
            return if (grid[0][2] == 'x') 1 else -1
        return 0
    }

    fun minimax(grid: Array<Array<Char>>, isMaximizing: Boolean): Int {
        val status: Int = getStatus(grid)
        if (status != 0) {
            return status
        }
        val initialBestScore: Int = if (isMaximizing) -2 else 2
        var bestScore: Int = initialBestScore
        for (i in 0..2) {
            for (j in 0..2) {
                if (grid[i][j] == '?') {
                    grid[i][j] = if (isMaximizing) 'x' else 'o'
                    val score: Int = minimax(grid, !isMaximizing)
                    if (isMaximizing && score > bestScore) {
                        bestScore = score
                    }
                    if (!isMaximizing && score < bestScore) {
                        bestScore = score
                    }
                    grid[i][j] = '?'
                }
            }
        }
        return if (bestScore == initialBestScore) 0 else bestScore
    }

    fun getBestMove(grid: Array<Array<Char>>): Pair<Int, Int> {
        var bestMove: Pair<Int, Int> = Pair(-1, -1)
        var bestScore: Int = -2
        for (i in 0..2) {
            for (j in 0..2) {
                if (grid[i][j] == '?') {
                    grid[i][j] = 'x'
                    val score: Int = minimax(grid, false)
                    if (score > bestScore) {
                        bestScore = score
                        bestMove = Pair(i, j)
                    }
                    grid[i][j] = '?'
                }
            }
        }
        return bestMove
    }

    fun takeTurn() {
        if (!flag) {
            print("Computer's turn\n")
            val move = getBestMove(board)
            board[move.first][move.second] = 'x'
            return
        }
        print("Your turn (a,b): ")
        val (a, b) = readln().split(",")
        val x = a.trim().toInt() - 1
        val y = b.trim().toInt() - 1
        board[x][y] = 'o'
    }

    fun isGameFinished(): Boolean {
        for (row in board) {
            for (ch in row) {
                if (ch == '?') return false
            }
        }
        return true
    }

    fun resetBoard() {
        //reset the board and clear all the values
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = '?'
            }
        }
    }

}