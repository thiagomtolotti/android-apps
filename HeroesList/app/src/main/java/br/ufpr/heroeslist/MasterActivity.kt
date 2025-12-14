package br.ufpr.heroeslist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MasterActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_master)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById<RecyclerView>(R.id.rvHeroes)
        recyclerView?.adapter = HeroesAdapter(this.createHeroes(), this) {
            //startActivity(HeroDetailActivity.newIntent(this, it))

            Toast.makeText(this, "Clicou no ${it.name}", Toast.LENGTH_SHORT).show()
        }
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    private fun createHeroes(): List<Hero> {
        val heroes = mutableListOf<Hero>()

        heroes.add(Hero(R.drawable.batman, "Batman", "DC Comics"))
        heroes.add(Hero(R.drawable.dr_strange,"Dr. Strange", "Marvel Comics"))
        heroes.add(Hero(R.drawable.superman, "Superman", "DC Comics"))
        heroes.add(Hero(R.drawable.flash, "Flash", "DC Comics"))
        heroes.add(Hero(R.drawable.hulk, "Hulk", "Marvel Comics"))
        heroes.add(Hero(R.drawable.iron_man, "Iron Man", "Marvel Comics"))

        return  heroes
    }
}