package com.jones.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jones.myapplication.databinding.ActivityFriendsFormBinding
class FriendsFormActivity : AppCompatActivity() {
    lateinit var binding: ActivityFriendsFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //this will get the player name from the edit text and send it to the next activity using intent
        binding.btnSubmit.setOnClickListener {
            val playerOne = binding.editTextTextPersonName.text.toString().trim()
            val playerTwo = binding.editTextTextPersonName2.text.toString().trim()

            if (playerOne.isNotEmpty() && playerTwo.isNotEmpty()) {
                val intent = Intent(this, FriendsActivity::class.java)
                intent.putExtra("PLAYER_ONE", playerOne)
                intent.putExtra("PLAYER_TWO", playerTwo)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Please enter names for both players.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
