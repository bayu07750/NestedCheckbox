package com.bayu.nestedcheckbox

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bayu.nestedcheckbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listArray = arrayListOf(
        Helpdesk(
            id = "a",
            name = "a",
            subCategories = arrayListOf(
                Helpdesk.SubHelpdesk(id = "aa", name = "aa", ""),
                Helpdesk.SubHelpdesk(id = "ab", name = "ab", ""),
                Helpdesk.SubHelpdesk(id = "ac", name = "ac", ""),
            )
        ),
        Helpdesk(
            id = "b",
            name = "b",
            subCategories = arrayListOf(
                Helpdesk.SubHelpdesk(id = "bb", name = "bb", ""),
                Helpdesk.SubHelpdesk(id = "ba", name = "ba", ""),
                Helpdesk.SubHelpdesk(id = "bc", name = "bc", ""),
            )
        ),
        Helpdesk(
            id = "c",
            name = "c",
            subCategories = arrayListOf(),
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.adapter = MyAdapter(
            data = listArray,
            onItemParentCheckedChange = { position: Int, _: Helpdesk, isChecked: Boolean ->
                listArray[position].isChecked = isChecked
                listArray[position].subCategories.forEach {
                    it.isChecked = isChecked
                }
                binding.rvList.adapter?.notifyItemChanged(position)
            },
            onItemChildCheckedChange = { parentPosition: Int, childPosition: Int, _: Helpdesk, _: Helpdesk.SubHelpdesk, b: Boolean ->
                listArray[parentPosition].subCategories[childPosition].isChecked = b
                val allChecked = listArray[parentPosition].subCategories.all { it.isChecked }
                listArray[parentPosition].isChecked = allChecked
                binding.rvList.adapter?.notifyItemChanged(parentPosition)
            }
        )

        binding.button.setOnClickListener {
            val results = ArrayList<String>()
            listArray.forEach {
                if (it.subCategories.isNotEmpty()) {
                    val allChecked = it.subCategories.all { i -> i.isChecked }
                    if (allChecked) {
                        results.add(it.id)
                    } else {
                        results.addAll(
                            it.subCategories.filter { j -> j.isChecked }.map { j -> j.id }
                        )
                    }
                } else {
                    if (it.isChecked) {
                        results.add(it.id)
                    }
                }
            }

            Log.d("result", results.toString())
            Toast.makeText(this, results.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}