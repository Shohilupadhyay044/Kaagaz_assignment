package com.sohil.kaagaz_scanner_assignment.adapter.viewHolder


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataEntity
import com.sohil.kaagaz_scanner_assignment.onClickListener.OnClickOfItem
import kotlinx.android.synthetic.main.item_layout.view.*

/**
 * View Holder class for single take photos.
 */
class MyViewHolder(private val view: View, var onClick: OnClickOfItem) :
    RecyclerView.ViewHolder(view) {

    fun setData(dataModelItem: MyDataEntity) {

        view.apply {
            Glide.with(ivAvatar).load(dataModelItem.image).into(ivAvatar)
            itemCard.setOnClickListener {
                onClick.showImage(dataModelItem, adapterPosition)
            }
        }
    }

}


