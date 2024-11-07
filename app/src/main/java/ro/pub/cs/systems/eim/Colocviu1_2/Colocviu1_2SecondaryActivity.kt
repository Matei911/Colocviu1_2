package ro.pub.cs.systems.eim.Colocviu1_2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviu1_2SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Preluăm suma din Intent
        val sum = intent.getIntExtra("SUM_RESULT", 0)

        // Afișăm suma într-un Toast
        Toast.makeText(this, "Suma este: $sum", Toast.LENGTH_LONG).show()

        // Finalizăm activitatea imediat pentru a preveni afișarea ei
        finish()
    }
}