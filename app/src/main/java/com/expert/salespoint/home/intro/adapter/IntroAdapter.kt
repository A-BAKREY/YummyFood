package com.expert.salespoint.home.intro.adapter


import com.kt.core.base.BaseAdapter
import com.entities.IntroModel
import com.expert.salespoint.databinding.IntroPageBinding


class IntroAdapter: BaseAdapter<IntroPageBinding, IntroModel>(){
    override fun setContent(binding: IntroPageBinding, item: IntroModel, position: Int) {

        binding.imageView1.setImageResource(item.icon)
        binding.firstDis.text = item.title
        binding.secondDis.text = item.dest
    }

}