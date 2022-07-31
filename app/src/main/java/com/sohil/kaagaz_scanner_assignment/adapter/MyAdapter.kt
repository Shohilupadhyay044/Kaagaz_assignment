package com.sohil.kaagaz_scanner_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sohil.kaagaz_scanner_assignment.R
import com.sohil.kaagaz_scanner_assignment.adapter.viewHolder.MyViewHolder
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataEntity
import com.sohil.kaagaz_scanner_assignment.onClickListener.OnClickOfItem

/**
 * Adapter class for single take photos.
 */
class MyAdapter(private var dataList: List<MyDataEntity>, var onClickOfItem: OnClickOfItem) :
    RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view,onClickOfItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = dataList[position]
        holder.setData(list)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}