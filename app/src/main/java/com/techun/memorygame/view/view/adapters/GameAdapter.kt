package com.techun.memorygame.view.view.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.techun.memorygame.R
import com.techun.memorygame.domain.model.GameModel
import javax.inject.Inject

class GameAdapter @Inject constructor() : GameBaseAdapter(R.layout.item_game) {

    override val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(list: List<GameModel>) = differ.submitList(list)

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.render(game)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(game)
                }
            }
        }
    }
}