package br.ufpr.hpapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun exit(view: View) {
        finish()
    }

    fun redirectToTeacherList(view: View) {
        val intent: Intent = Intent(this, TeachersListActivity::class.java)

        startActivity(intent)
    }

    fun redirectToHouseList(view: View) {
        val intent: Intent = Intent(this, HouseListActivity::class.java)

        startActivity(intent)
    }

    fun redirectToCharacterDetails(view: View) {
        val intent: Intent = Intent(this, CharacterDetailsActivity::class.java)

        startActivity(intent)
    }
}