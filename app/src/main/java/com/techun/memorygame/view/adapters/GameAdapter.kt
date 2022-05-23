package com.techun.memorygame.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techun.memorygame.view.MemoryItem
import com.techun.memorygame.databinding.ItemGameBinding
import com.techun.memorygame.data.utils.extensions.loadByResource

class GameAdapter(
    private var boardList: List<MemoryItem>,
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

    inner class BoardViewHolder(
        val binding: ItemGameBinding,
        private val onItemSelected: OnItemSelected? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: MemoryItem) = with(binding.root) {
            binding.imgPreview.loadByResource(card.urlImagen)
            binding.tvTitleGame.text = card.name
            binding.imgPreview.setOnClickListener {
                onItemSelected?.onClickListener(card)
            }
        }
    }

    interface OnItemSelected {
        fun onClickListener(card: MemoryItem)
    }
}