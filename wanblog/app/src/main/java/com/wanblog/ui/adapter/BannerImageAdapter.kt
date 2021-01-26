package com.wanblog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wanblog.R
import com.wanblog.model.bean.Top3Bean
import com.youth.banner.adapter.BannerAdapter
import kotlinx.android.synthetic.main.item_home_banner_item.view.*

class BannerImageAdapter(top3BeanList: MutableList<Top3Bean>) :
    BannerAdapter<Top3Bean, BannerImageAdapter.ViewHolder>(top3BeanList) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(top3Bean: Top3Bean, position: Int) {
            itemView.tv_home_banner_item_username.text = top3Bean.username
            itemView.tv_home_banner_item_count.text = top3Bean.count.toString()

            when (position) {
                0 -> {
                    itemView.iv_home_banner_item_ranking.setImageResource(R.drawable.ic_blog_top1)
                }
                1 -> {
                    itemView.iv_home_banner_item_ranking.setImageResource(R.drawable.ic_blog_top2)
                }
                2 -> {
                    itemView.iv_home_banner_item_ranking.setImageResource(R.drawable.ic_blog_top3)
                }
            }

        }
    }

    override fun onBindView(holder: ViewHolder, top3BeanList: Top3Bean, position: Int, size: Int) {
        holder.bindData(top3BeanList, position)
    }

}