package com.techun.memorygame.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.techun.memorygame.MemoryItem
import com.techun.memorygame.databinding.ItemCardBinding
import com.techun.memorygame.databinding.ItemGameBinding
import com.techun.memorygame.utils.extensions.loadByResource
import com.wajahatkarim3.easyflipview.EasyFlipView


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


            }
        }
    }

    interface OnItemSelected {
        fun onClickListener(
            imageFront: ImageView,
            imageBack: ImageView,
            position: String,
            adapterPosition: Int
        )
    }
}