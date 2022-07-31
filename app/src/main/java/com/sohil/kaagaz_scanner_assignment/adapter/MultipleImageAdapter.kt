package com.sohil.kaagaz_scanner_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sohil.kaagaz_scanner_assignment.R
import com.sohil.kaagaz_scanner_assignment.adapter.viewHolder.MultipleImageViewHolder
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity
import com.sohil.kaagaz_scanner_assignment.onClickListener.OnClickOfItem

/**
 * Adapter class for Multiple take photos.
 */
class MultipleImageAdapter (private var dataList: List<MultipleImageEntity>, var onClickOfItem: OnClickOfItem) :
    RecyclerView.Adapter<MultipleImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MultipleImageViewHolder(view,onClickOfItem)
    }

    override fun onBindViewHolder(holder: MultipleImageViewHolder, position: Int) {
        val list = dataList[position]
        holder.setData(list)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}