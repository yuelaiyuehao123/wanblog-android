package com.wanblog.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wanblog.R
import com.wanblog.model.bean.Top3Bean
import com.youth.banner.adapter.BannerAdapter
import kotlinx.android.synthetic.main.item_home_banner_item.view.*

class HomeBannerAdapter(top3BeanList: MutableList<Top3Bean>, val context: Context) :
    BannerAdapter<Top3Bean, HomeBannerAdapter.ViewHolder>(top3BeanList) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_banner_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindView(holder: ViewHolder, top3BeanList: Top3Bean, position: Int, size: Int) {
        holder.bindData(top3BeanList, position, context)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(top3Bean: Top3Bean, position: Int, context: Context) {
            itemView.tv_home_banner_item_username.text = "用户名: ${top3Bean.username}"
            itemView.tv_home_banner_item_count.text = "发表博客数量: ${top3Bean.count}"

            when (position) {
                0 -> {
                    itemView.tv_home_banner_item_ranking.setText(R.string.blog_top1)
                    itemView.iv_home_banner_item_ranking.setImageResource(R.drawable.ic_blog_top1)
                }
                1 -> {
                    itemView.tv_home_banner_item_ranking.setText(R.string.blog_top2)
                    itemView.iv_home_banner_item_ranking.setImageResource(R.drawable.ic_blog_top2)
                }
                2 -> {
                    itemView.tv_home_banner_item_ranking.setText(R.string.blog_top3)
                    itemView.iv_home_banner_item_ranking.setImageResource(R.drawable.ic_blog_top3)
                }
            }
        }
    }

}