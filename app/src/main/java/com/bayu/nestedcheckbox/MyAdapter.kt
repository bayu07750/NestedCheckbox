package com.bayu.nestedcheckbox

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.bayu.nestedcheckbox.databinding.ItemCheckboxParentBinding

class MyAdapter(
    private val data: ArrayList<Helpdesk>,
    private val onItemParentCheckedChange: (Int, Helpdesk, Boolean) -> Unit,
    private val onItemChildCheckedChange: (parentPosition: Int, childPosition: Int, parentItem: Helpdesk, subItem: Helpdesk.SubHelpdesk, isChecked: Boolean) -> Unit,
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCheckboxParentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        private val binding: ItemCheckboxParentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(helpdesk: Helpdesk) {
            binding.cbParent.text = helpdesk.name

            binding.cbParent.setOnCheckedChangeListener(null)
            binding.cbParent.isChecked = helpdesk.isChecked
            binding.cbParent.setOnCheckedChangeListener { _, isChecked ->
                onItemParentCheckedChange.invoke(adapterPosition, helpdesk, isChecked)
            }

            binding.llCb.removeAllViews()
            if (helpdesk.subCategories.isNotEmpty()) {
                helpdesk.subCategories.forEachIndexed { index, subHelpdesk ->
                    val cb = CheckBox(binding.root.context)
                    cb.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    cb.text = subHelpdesk.name
                    cb.setOnCheckedChangeListener(null)
                    cb.isChecked = subHelpdesk.isChecked
                    cb.setOnCheckedChangeListener { _, isChecked ->
                        onItemChildCheckedChange.invoke(
                            adapterPosition,
                            index,
                            helpdesk,
                            subHelpdesk,
                            isChecked
                        )
                    }
                    binding.llCb.addView(cb)
                }
            }
        }
    }
}
