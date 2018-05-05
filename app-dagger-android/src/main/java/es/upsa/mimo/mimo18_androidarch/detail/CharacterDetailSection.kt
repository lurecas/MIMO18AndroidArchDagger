package es.upsa.mimo.mimo18_androidarch.detail

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import es.upsa.mimo.mimo18_androidarch.R
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection


class CharacterDetailSection(
        var title: String,
        private var items: List<String>
) : StatelessSection(
        SectionParameters.builder()
                .itemResourceId(R.layout.character_detail_list_section_item)
                .headerResourceId(R.layout.character_detail_list_header_section)
                .build()) {

    override fun getContentItemsTotal(): Int {
        return items.size
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return HeaderViewHolder(view)
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ItemViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val name = items[position]
            holder.tvItem.text = name
        }
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        if (holder is HeaderViewHolder) {
            holder.tvTitle.text = title
        }

    }

    private inner class HeaderViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById<View>(R.id.tvTitle) as TextView
    }

    private inner class ItemViewHolder constructor(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val tvItem: TextView = rootView.findViewById<View>(R.id.tvItem) as TextView
    }

}