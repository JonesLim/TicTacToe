package com.jones.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jones.myapplication.databinding.ActivityPlayWithComBinding
import com.jones.myapplication.utils.GameLogic
import com.jones.myapplication.utils.GameLogic.resetBoard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayWithCom : AppCompatActivity() {
    private lateinit var binding: ActivityPlayWithComBinding
    private lateinit var buttons: Array<Array<Button>>
    private var isRunning = false
    private var currTurn = Turn.NOUGHT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayWithComBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        start()
    }

    private fun start() {
        binding.run {
            buttons = arrayOf(
                arrayOf(a1, a2, a3),
                arrayOf(b1, b2, b3),
                arrayOf(c1, c2, c3)
            )

            for (i in 0..2) {
                for (j in 0..2) {
                    buttons[i][j].setOnClickListener {
                        if (isRunning && currTurn == Turn.NOUGHT) {
                            if (GameLogic.board[i][j] == '?') {
                                buttons[i][j].text = "O"
                                GameLogic.board[i][j] = 'o'
                                nextTurn()
                            }
                        }
                    }
                }
            }
            isRunning = true
        }
    }

    fun nextTurn() {
        checkStatus()
        if (currTurn == Turn.NOUGHT) {
            currTurn = Turn.CROSS
            computerMove()
        } else {
            currTurn = Turn.NOUGHT
        }
    }

    fun computerMove() {
        lifecycleScope.launch {
            delay(1000)
            if (isRunning) {
                val (x, y) = GameLogic.getBestMove(GameLogic.board)
                GameLogic.board[x][y] = 'x'
                buttons[x][y].text = "X"
                nextTurn()
            }
        }
    }

    fun checkStatus() {
        val status = GameLogic.getStatus(GameLogic.board)
        if (status == -1) {
//            Log.d("debugging", "You win")
            showResult("You win")
            isRunning = false
            return
        }
        if (status == 1) {
//            Log.d("debugging", "Computer wins")
            showResult("Computer wins")
            isRunning = false
            return
        }
        if (GameLogic.isGameFinished()) {
//            Log.d("debugging", "Draw")
            showResult("Draw")
            isRunning = false
            return
        }
    }

    private fun showResult(title: String) {
        val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage("Do you want to play again?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            resetBoard()
            start()
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->
            finish()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
}

    enum class Turn{
        CROSS,
        NOUGHT
    }
}






