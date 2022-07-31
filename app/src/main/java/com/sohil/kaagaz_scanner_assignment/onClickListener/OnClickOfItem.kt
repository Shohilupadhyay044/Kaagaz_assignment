package com.sohil.kaagaz_scanner_assignment.onClickListener

import com.sohil.kaagaz_scanner_assignment.db.model.MyDataEntity
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity


interface OnClickOfItem {

    /**
     * this function is for when user click on the view of single album folder image set the onsetClickListener.
     */
    fun showImage(dataModelItem: MyDataEntity, position: Int)

    /**
     * this function is for when user click on the view of multiple album folder image set the onsetClickListener.
     */
    fun showMultipleImage(dataModelItem2: MultipleImageEntity, position: Int)


}