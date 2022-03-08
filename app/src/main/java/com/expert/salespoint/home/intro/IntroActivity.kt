package com.expert.salespoint.home.intro

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.entities.IntroModel
import com.expert.salespoint.R
import com.expert.salespoint.databinding.ActivityIntroBinding
import com.expert.salespoint.home.MainActivity
import com.expert.salespoint.home.intro.adapter.IntroAdapter
import com.kt.core.base.BaseActivity

class IntroActivity : BaseActivity<ActivityIntroBinding>() {

    private val introAdapter by lazy { IntroAdapter() }
    private lateinit var introList: ArrayList<IntroModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideToolbar()

        initialize()

        click()

        setIndicators()

        selectedIndicator(0)

        binding.introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectedIndicator(position)
            }
        })

    }

    private fun initialize() {
        introList = ArrayList()
        binding.back.text = ""
        val list: List<IntroModel> = listOf(
        IntroModel(
            "Yummy Food",
            "Big, beautiful, cheesy, sloppy, juicy burgers. That’s what Willy’s Kitchen is all about and we love it!",
            R.drawable.in1
        ),
        IntroModel(
            "Food Delivery",
            "Yummy is the best service to order online food delivery from the best restaurants in Egypt.",
            R.drawable.in2
        ),
        IntroModel(
            "Food Pizza",
            "Pizza is a dish of Italian origin consisting of a usually round,",
            R.drawable.in3
        )
        )
        introAdapter.addItems(list)
        binding.introViewPager.adapter = introAdapter

    }

    private fun click(){
        binding.next.setOnClickListener {

            if(binding.introViewPager.currentItem+1<introAdapter.itemCount){
                binding.introViewPager.currentItem+=1
                binding.back.text = "back"
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

        binding.back.setOnClickListener {
            if(binding.introViewPager.currentItem<introAdapter.itemCount){
                binding.introViewPager.currentItem-=1
            }
        }

        binding.skip.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setIndicators(){
        val indicators = arrayOfNulls<ImageView>(introAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,R.drawable.seconddot
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.dots.addView(indicators[i])
        }
    }

    private fun selectedIndicator(position: Int){
        val count = binding.dots.childCount
        for (i in 0 until count){
            val imageView = binding.dots.get(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,R.drawable.selected
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,R.drawable.seconddot
                    )
                )
            }
        }
    }
}