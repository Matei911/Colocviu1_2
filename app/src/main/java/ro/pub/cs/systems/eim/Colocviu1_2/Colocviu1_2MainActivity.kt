package ro.pub.cs.systems.eim.Colocviu1_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_2MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_2_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referințe la componentele din layout
        val putNumberEditText = findViewById<EditText>(R.id.put_number)
        val addButton = findViewById<Button>(R.id.add_button)
        val displayNumberTextView = findViewById<TextView>(R.id.display_number)

        // Setăm un listener pentru butonul "Add"
        addButton.setOnClickListener {
            // Citim textul din EditText
            val numberText = putNumberEditText.text.toString()

            if (numberText.isNotEmpty()) {
                // Dacă există text în EditText, îl adăugăm la TextView cu un "+"
                displayNumberTextView.text = "${displayNumberTextView.text}$numberText+"
                // Curățăm EditText-ul
                putNumberEditText.text.clear()
            } else {
                // Dacă EditText-ul este gol, eliminăm ultimul caracter "+" din TextView, dacă există
                val currentText = displayNumberTextView.text.toString()
                if (currentText.isNotEmpty() && currentText.last() == '+') {
                    displayNumberTextView.text = currentText.dropLast(1)
                }
            }
        }

    }
}