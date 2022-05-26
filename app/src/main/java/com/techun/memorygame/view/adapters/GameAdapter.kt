package com.techun.memorygame.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techun.memorygame.data.CardItem
import com.techun.memorygame.data.GameItem
import com.techun.memorygame.databinding.ItemGameBinding
import com.techun.memorygame.data.utils.extensions.loadByResource

class GameAdapter(
    private var boardList: ArrayList<GameItem>,
    private val onItemSelected: OnItemSelected? = null
) : RecyclerView.Adapter<GameAdapter.BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        return BoardViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemSelected
        )
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.bind(boardList[position])
    }

    override fun getItemCount() = boardList.size

    inner class BoardViewHolder(val binding: ItemGameBinding,
        private val onItemSelected: OnItemSelected? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: GameItem) = with(binding.root) {
            binding.imgPreview.loadByResource(card.cover!!)
            binding.tvTitleGame.text = card.title
            binding.imgPreview.setOnClickListener {
                onItemSelected?.onClickListener(card.cards)
            }
        }
    }

    interface OnItemSelected {
        fun onClickListener(card: MutableList<CardItem>?)
    }
}