package com.techun.memorygame.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techun.memorygame.MemoryItem
import com.techun.memorygame.databinding.ItemCardBinding
import com.techun.memorygame.utils.extensions.loadByResource

class CardAdapter(
    private val boardList: List<MemoryItem>,
    private val onItemSelected: OnItemSelected? = null
) : RecyclerView.Adapter<CardAdapter.BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        return BoardViewHolder(
            ItemCardBinding.inflate(
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

    inner class BoardViewHolder(val binding: ItemCardBinding, private val onItemSelected: OnItemSelected? = null) : RecyclerView.ViewHolder(binding.root) {
            private val image = binding.imgPreview

        fun bind(card: MemoryItem) = with(binding.root) {
            image.loadByResource(card.urlImagen)
            image.setOnClickListener {
                onItemSelected?.onClickListener(adapterPosition)
            }
            /*    container.background = ContextCompat.getDrawable(context, board.background)
                image.setAnimation(board.image)
                title.text = board.title
                description.text = board.description

                if (adapterPosition == (boardList.size - 1)) {
                    button.text = "Finalizar"
                }

                button.setOnClickListener {
                    onItemSelected?.onClickListener(adapterPosition)
                }*/
        }
    }

    interface OnItemSelected {
        fun onClickListener(position: Int)
    }
}