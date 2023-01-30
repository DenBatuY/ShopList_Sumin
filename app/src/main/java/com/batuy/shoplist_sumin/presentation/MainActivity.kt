package com.batuy.shoplist_sumin.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.batuy.shoplist_sumin.R
import com.batuy.shoplist_sumin.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rcView)
        adapter = ShopListAdapter()
        recyclerView.adapter = adapter
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.ENABLED,
            ShopListAdapter.MAX_ITEM_POOL
        )
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.DISABLED,
            ShopListAdapter.MAX_ITEM_POOL
        )
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }

        adapter.onShopItemClickListener = {
            startActivity(ShopItemActivity.newIntentEditItem(this,it.id))
            Log.d("test", it.toString())
        }

        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            startActivity(ShopItemActivity.newIntentAddItem(this))
        }

        setupSwipeDeleteItem()
    }

    private fun setupSwipeDeleteItem() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


}