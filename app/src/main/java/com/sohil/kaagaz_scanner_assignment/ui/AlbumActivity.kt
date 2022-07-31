package com.sohil.kaagaz_scanner_assignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sohil.kaagaz_scanner_assignment.R
import com.sohil.kaagaz_scanner_assignment.adapter.MultipleImageAdapter
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataDatabase
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataEntity
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity
import com.sohil.kaagaz_scanner_assignment.db.viewModel.MyViewModel
import com.sohil.kaagaz_scanner_assignment.db.viewModel.ViewModelFactory
import com.sohil.kaagaz_scanner_assignment.onClickListener.OnClickOfItem
import com.sohil.kaagaz_scanner_assignment.repository.MyRepository
import kotlinx.android.synthetic.main.activity_album.*


class AlbumActivity : AppCompatActivity(), OnClickOfItem {

    private lateinit var myAdapter: MultipleImageAdapter
    val dataModelList2 = mutableListOf<MultipleImageEntity>()
    private var myViewModel: MyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        setRecyclerAdapter2()

        /**
         * Initialize dataDao
         */
        val MyDataDao by lazy {
            val roomDatabase = MyDataDatabase.getDatabase(this)
            roomDatabase.getMyDao()
        }

        /**
         * Initialize repository
         */
        val repository by lazy {
            MyRepository(MyDataDao)
        }
        val factory = ViewModelFactory(repository)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)

        /**
         * Observe the data from room database
         */
        observeLiveData2()

        /**
         * press back navigate to previous activity.
         */
        toolbar_top1.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setRecyclerAdapter2() {
        myAdapter = MultipleImageAdapter(dataModelList2, this)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView2.apply {
            this.layoutManager = layoutManager
            adapter = myAdapter
        }
    }

    private fun observeLiveData2() {

        myViewModel?.getMultipleImage()?.observe(this, Observer {
            dataModelList2.clear()
            dataModelList2.addAll(it)
            myAdapter.notifyDataSetChanged()
        })

    }

    override fun showImage(dataModelItem: MyDataEntity, position: Int) {

    }

    override fun showMultipleImage(dataModelItem2: MultipleImageEntity, position: Int) {
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("image", dataModelItem2.image)
        intent.putExtra("date", dataModelItem2.createdAt)
        startActivity(intent)
    }
}