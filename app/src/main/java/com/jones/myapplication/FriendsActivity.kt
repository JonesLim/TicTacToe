package com.jones.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jones.myapplication.databinding.ActivityFriendsBinding

class FriendsActivity : AppCompatActivity() {
    // Enum for player turn
    enum class Turn {
        NOUGHT,
        CROSS
    }

    // Player names
    private var playerOne: String = ""
    private var playerTwo: String = ""

    // Current turn and starting turn
    private var currentTurn = Turn.CROSS
    private var firstTurn = Turn.CROSS

    // Player scores
    private var crossScore = 0
    private var noughtScore = 0

    // Board
    private var boardList = mutableListOf<Button>()
    lateinit var binding: ActivityFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
        getPlayerNames()

        // Back button click listener
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to retrieve player names from the intent extras
    private fun getPlayerNames() {
        playerOne = intent.getStringExtra("PLAYER_ONE") ?: ""
        playerTwo = intent.getStringExtra("PLAYER_TWO") ?: ""
        setTurnLabel()
    }

    // Function to initialize the board
    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    // Function to handle the board button clicks
    fun boardTapped(view: View) {
        val button = view as Button
        var player = ""

        if (currentTurn == Turn.CROSS) {
            player = "X"
            currentTurn = Turn.NOUGHT
        } else if (currentTurn == Turn.NOUGHT) {
            player = "O"
            currentTurn = Turn.CROSS
        }

        button.text = player
        button.isEnabled = false

        if (checkForVictory(player)) {
            if (player == "X") {
                crossScore++
                showResult("$playerOne Wins!")
            } else {
                noughtScore++
                showResult("$playerTwo Wins!")
            }
        } else if (isBoardFull()) {
            showResult("It's a Draw!")
        } else {
            setTurnLabel()
        }
    }

    // Function to check for victory
    private fun checkForVictory(s: String): Boolean {
        // Horizontal check for victory
        if (match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s))
            return true
        if (match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s))
            return true
        if (match(binding.c1, s) && match(binding.c2, s) && match(binding.c3, s))
            return true

        // Vertical check for victory
        if (match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s))
            return true
        if (match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s))
            return true
        if (match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s))
            return true

        // Diagonal check for victory
        if (match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s))
            return true
        if (match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s))
            return true

        return false
    }

    private fun match(button: Button, symbol: String) = button.text == symbol

    // Function to show the result in an AlertDialog
    private fun showResult(title: String) {
        val message = "$title\n\n$playerOne: $crossScore\n$playerTwo: $noughtScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset") { _, _ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    // Function to reset the board
    private fun resetBoard() {
        for (button in boardList) {
            button.text = ""
            button.isEnabled = true
        }
        currentTurn = firstTurn
        setTurnLabel()
    }

    // Function to check if the board is full
    private fun isBoardFull(): Boolean {
        for (button in boardList) {
            if (button.text == "")
                return false
        }
        return true
    }

    // Function to set the turn label
    private fun setTurnLabel() {
        val turnText = if (currentTurn == Turn.CROSS)
            "Turn $playerOne (${Companion.CROSS})"
        else
            "Turn $playerTwo (${Companion.NOUGHT})"
        binding.turnTV.text = turnText
    }

    companion object {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}
