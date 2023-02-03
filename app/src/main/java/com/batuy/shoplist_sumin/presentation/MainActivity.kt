package com.batuy.shoplist_sumin.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.batuy.shoplist_sumin.R
import com.batuy.shoplist_sumin.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),ShopItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private lateinit var recyclerView: RecyclerView

    private var shopItemContainerLand: FragmentContainerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainerLand = findViewById(R.id.shop_item_container)

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
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentEditItem(this, it.id))
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }

        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentAddItem(this))
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }

        setupSwipeDeleteItem()
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainerLand == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.shop_item_container, fragment)
            .addToBackStack(null).commit()
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

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity,"Success",Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }


}