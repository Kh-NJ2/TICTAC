package com.example.tictac


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tictac.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

//enum class for switching turns
    enum class turn {
        CROSS,
        CIRCLE
    }

    private var firstTurn = turn.CROSS
    private var currentTurn = turn.CROSS

    private lateinit var binding: ActivityMainBinding // imp
    private var btns = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //imp
        setContentView(binding.root) // imp2

        initbtns()

    }

//adding the buttons to the lost
    private fun initbtns() {
        btns.add(binding.button00)
        btns.add(binding.button01)
        btns.add(binding.button02)
        btns.add(binding.button10)
        btns.add(binding.button11)
        btns.add(binding.button12)
        btns.add(binding.button20)
        btns.add(binding.button21)
        btns.add(binding.button22)
    }


//create this first in the main.xml
    fun boardTapped(view: View) {

        if (view !is Button)
            return

//calls the function to add the X or O to the button
        addonbtn(view)
        setturn()

//Checks for who wins
        if (checkForVic("X"))
            result("Cross WINS")

        if(checkForVic("O"))
            result("Nought WINS")

//checks if the board is filled without anyone winning
        if (fullboard()) {
            result("Draw")
        }
    }

    private fun setturn() {
        if(currentTurn == turn.CROSS)
            binding.p1.text = "Turn X"
        else if (currentTurn == turn.CIRCLE)
            binding.p1.text = "Turn O"
    }

    private fun checkForVic(s: String): Boolean {

//Horizontal Wins
        if(match(binding.button00, s) && match(binding.button01, s) && match(binding.button02, s))
            return true
        if(match(binding.button10, s) && match(binding.button11, s) && match(binding.button12, s))
            return true
        if(match(binding.button20, s) && match(binding.button21, s) && match(binding.button22, s))
            return true

//Vertical Wins
        if(match(binding.button00, s) && match(binding.button10, s) && match(binding.button20, s))
            return true
        if(match(binding.button01, s) && match(binding.button11, s) && match(binding.button21, s))
            return true
        if(match(binding.button02, s) && match(binding.button12, s) && match(binding.button22, s))
            return true

//Diagonal Wins
        if(match(binding.button00, s) && match(binding.button11, s) && match(binding.button22, s))
            return true
        if(match(binding.button02, s) && match(binding.button11, s) && match(binding.button20, s))
            return true

        return false
    }

    private fun match(btn : Button, s: String): Boolean = btn.text == s

    private fun fullboard(): Boolean
    {
        for(btn in btns) {
            if (btn.text == "")
                return false
        }
            return true
    }


    private fun result(s: String) {
        AlertDialog.Builder(this)
            .setTitle(s)
            .setPositiveButton("Reset")
            { _, _ ->
                reset()
            }
            .setCancelable(false)
            .show()

    }


    private fun reset() {

        for (btn in btns) {
            btn.text = ""
        }

        if (firstTurn == turn.CROSS) {
            firstTurn = turn.CIRCLE
        } else if (firstTurn == turn.CIRCLE) {
            firstTurn = turn.CROSS
        }

        currentTurn = firstTurn

    }


    //adds X or O to the button that is clicked
    private fun addonbtn(btn: Button) {

        if (btn.text != "")
            return

        if (currentTurn == turn.CROSS) {
            btn.text = "X"

            currentTurn = turn.CIRCLE
        } else if (currentTurn == turn.CIRCLE) {
            btn.text = "O"
            currentTurn = turn.CROSS
        }

    }



    fun resetTapped(view: View) {
        reset()
    }






}
