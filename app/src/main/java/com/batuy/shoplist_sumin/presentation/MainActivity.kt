package com.batuy.shoplist_sumin.presentation

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.batuy.shoplist_sumin.R
import com.batuy.shoplist_sumin.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as ShoppingListApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rcView
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
        viewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]

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

        binding.floatingActionButton.setOnClickListener {
            if (isOnePaneMode()) {
                startActivity(ShopItemActivity.newIntentAddItem(this))
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }

        setupSwipeDeleteItem()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
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
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }


}