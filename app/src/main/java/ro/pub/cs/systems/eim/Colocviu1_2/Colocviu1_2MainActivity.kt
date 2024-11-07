package ro.pub.cs.systems.eim.Colocviu1_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
        val computeButton = findViewById<Button>(R.id.compute_button)

        // Restaurăm conținutul din TextView dacă este disponibil
        savedInstanceState?.let {
            displayNumberTextView.text = it.getString("displayText", "")
        }

        // Setăm un listener pentru butonul "Add"
        addButton.setOnClickListener {
            val numberText = putNumberEditText.text.toString()
            if (numberText.isNotEmpty()) {
                displayNumberTextView.text = "${displayNumberTextView.text}$numberText+"
                putNumberEditText.text.clear()
            } else {
                val currentText = displayNumberTextView.text.toString()
                if (currentText.isNotEmpty() && currentText.last() == '+') {
                    displayNumberTextView.text = currentText.dropLast(1)
                }
            }
        }

        // Setăm un listener pentru butonul "Compute"
        computeButton.setOnClickListener {
            val currentText = displayNumberTextView.text.toString().removeSuffix("+")
            val sum = currentText.split("+")
                .filter { it.isNotEmpty() }
                .map { it.toIntOrNull() ?: 0 }
                .sum()

            val intent = Intent(this, Colocviu1_2SecondaryActivity::class.java)
            intent.putExtra("SUM_RESULT", sum)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            //daca suma este mai mare decat 10, se foloseste serviciul Colocviu1_2Service
            if (sum > 10) {
                val serviceIntent = Intent()
                serviceIntent.setClassName("ro.pub.cs.systems.eim.Colocviu1_2", "ro.pub.cs.systems.eim.Colocviu1_2.Colocviu1_2Service")
                serviceIntent.putExtra("SUM_RESULT", sum)
                startService(serviceIntent)
            }
        }
    }

    // Salvăm textul din TextView înainte de a schimba orientarea
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val displayTextView = findViewById<TextView>(R.id.display_number)
        outState.putString("displayText", displayTextView.text.toString())
    }

    // Restaurăm textul din TextView după schimbarea orientării
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val displayTextView = findViewById<TextView>(R.id.display_number)
        displayTextView.text = savedInstanceState.getString("displayText", "")
    }
}