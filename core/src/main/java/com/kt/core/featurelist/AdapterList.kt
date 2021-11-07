package com.kt.core.featurelist

import com.kt.core.base.BaseAdapter
import com.kt.core.databinding.RvListBinding
import com.kt.core.utlities.extensions.clickWithDebounce

class AdapterList : BaseAdapter<RvListBinding, ModelList>() {
    var selectedId: Int = 0
    var selectedItem: ModelList = ModelList()
    override fun setContent(binding: RvListBinding, item: ModelList, position: Int) {
        binding.root.clickWithDebounce {
            onViewClicked(binding.root, item, position)
            //selectedId=position
            //notifyDataSetChanged()
        }
        with(binding) {
            rvListRb.text = item.name
            rvListRb.clickWithDebounce {
                onViewClicked(binding.root, item, position)
                //selectedId=position
                // notifyDataSetChanged()
            }
            if (position == selectedId) {
                rvListRb.isChecked = true
            } else {
                rvListRb.isChecked = false
            }
        }
    }

}