package az.zero.singleselectionrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.zero.singleselectionrecyclerview.databinding.ItemBinding

/**
 * Simple Adapter to handle single selection on RecyclerView
 * Implemented using [ListAdapter] with [DiffUtil.ItemCallback]
 * for computing diffs between Lists on a background thread,
 * viewBinding, and clickListeners
 * */
class ItemAdapter : ListAdapter<Item, ItemAdapter.ItemViewHolder>(COMPARATOR) {

    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

    inner class ItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // add clickListener in init block to limit the number of clickListeners being instantiated
        init {
            binding.apply {
                cvRootLayout.setOnClickListener {
                    onSingleItemUnSelected(binding)
                    notifyItemChanged(lastCheckedPosition)
                    lastCheckedPosition = adapterPosition
                }
            }
        }

        fun bind(currentItem: Item) {
            binding.apply {
                tvName.text = currentItem.name
                if (adapterPosition != lastCheckedPosition) {
                    onSingleItemSelected(binding)
                } else {
                    onSingleItemUnSelected(binding)
                }
            }
        }
    }

    /**
     * replace [onSingleItemSelected] with logic for selected item
     * */

    private fun onSingleItemSelected(binding: ItemBinding) = binding.apply {
        imgFace.setImageResource(R.drawable.ic_smiling)
        tvName.text = binding.root.context.getString(R.string.happy)
        cvRootLayout.cardElevation = 8f
    }

    /**
     * replace [onSingleItemUnSelected] with logic for default or unselected item
     * */
    private fun onSingleItemUnSelected(binding: ItemBinding) = binding.apply {
        imgFace.setImageResource(R.drawable.ic_sad)
        tvName.text = binding.root.context.getString(R.string.sad)
        cvRootLayout.cardElevation = 2f
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.name == newItem.name


            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }
}