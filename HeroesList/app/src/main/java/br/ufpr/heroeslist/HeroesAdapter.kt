package br.ufpr.heroeslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HeroesAdapter(
    private val heroes : List<Hero>,
    private val ctx: Context,
    private val click: (hero:Hero) -> Unit
): RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder>() {
    inner class HeroesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.heroImage)
        val hName = itemView.findViewById<TextView>(R.id.heroName)
        val company = itemView.findViewById<TextView>(R.id.heroCompany)

        // Maps component to object
        fun bind(hero: Hero) {
            image.setImageResource(hero.image)
            hName.text = hero.name
            company.text = hero.company

            itemView.setOnClickListener { click(hero) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeroesViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false)


        return  HeroesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return heroes.size
    }

    override fun onBindViewHolder(
        holder: HeroesViewHolder,
        position: Int
    ) {
        val hero = heroes[position]

        holder.bind(hero)
    }


}