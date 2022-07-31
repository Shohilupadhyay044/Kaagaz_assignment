package com.sohil.kaagaz_scanner_assignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sohil.kaagaz_scanner_assignment.R
import com.sohil.kaagaz_scanner_assignment.adapter.MyAdapter
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataDatabase
import com.sohil.kaagaz_scanner_assignment.db.model.MyDataEntity
import com.sohil.kaagaz_scanner_assignment.db.multipleImage.MultipleImageEntity
import com.sohil.kaagaz_scanner_assignment.db.viewModel.MyViewModel
import com.sohil.kaagaz_scanner_assignment.db.viewModel.ViewModelFactory
import com.sohil.kaagaz_scanner_assignment.onClickListener.OnClickOfItem
import com.sohil.kaagaz_scanner_assignment.repository.MyRepository
import kotlinx.android.synthetic.main.activity_single_take_album.*

class SingleTakeAlbumActivity : AppCompatActivity(), OnClickOfItem {
    private lateinit var myAdapter: MyAdapter
    val dataModelList = mutableListOf<MyDataEntity>()
    private var myViewModel: MyViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_take_album)
        setRecyclerAdapter()
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
        observeLiveData()

        /**
         * press back navigate to previous activity.
         */
        toolbar_top3.setNavigationOnClickListener { onBackPressed() }
    }
    private fun observeLiveData() {

        myViewModel?.getImage()?.observe(this, Observer {
            dataModelList.clear()
            dataModelList.addAll(it)
            myAdapter.notifyDataSetChanged()
        })

    }



    private fun setRecyclerAdapter() {
        myAdapter = MyAdapter(dataModelList, this)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.apply {
            this.layoutManager = layoutManager
            adapter = myAdapter
        }
    }

    override fun showImage(dataModelItem: MyDataEntity, position: Int) {
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("image", dataModelItem.image)
        intent.putExtra("date", dataModelItem.createdAt)
        startActivity(intent)
    }

    override fun showMultipleImage(dataModelItem2: MultipleImageEntity, position: Int) {

    }

}