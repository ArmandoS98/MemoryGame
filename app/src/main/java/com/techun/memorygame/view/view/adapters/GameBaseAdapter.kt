package com.techun.memorygame.view.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.techun.memorygame.databinding.ItemGameBinding
import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.utils.loadByResource

abstract class GameBaseAdapter(private val layoutId: Int) :
    RecyclerView.Adapter<GameBaseAdapter.GameViewHolder>() {

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGameBinding.bind(itemView)
        fun render(game: GameModel) {
            binding.imgPreview.loadByResource(game.cover!!)
            binding.tvTitleGame.text = game.title
        }
    }

    protected val diffCallback = object : DiffUtil.ItemCallback<GameModel>() {
        override fun areItemsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected abstract val differ: AsyncListDiffer<GameModel>

    open var games: List<GameModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layoutId,
                parent,
                false
            )
        )
    }

    protected var onItemClickListener: ((GameModel) -> Unit)? = null
    protected var onDeleteClickListener: ((GameModel) -> Unit)? = null

    fun setItemClickListener(listener: (GameModel) -> Unit) {
        onItemClickListener = listener
    }

    fun setDeleteClickListener(listener: (GameModel) -> Unit) {
        onDeleteClickListener = listener
    }

    override fun getItemCount() = games.size
}