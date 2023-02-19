package com.batuy.shoplist_sumin.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.batuy.shoplist_sumin.databinding.FragmentShopItemBinding
import com.batuy.shoplist_sumin.domain.ShopItem
import javax.inject.Inject

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    lateinit var onEditingFinishedListener: OnEditingFinishedListener
      @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (requireActivity().application as ShoppingListApp).component
    }

    override fun onAttach(activity: Activity) {
        component.inject(this)
        super.onAttach(activity)
    }


    private lateinit var binding: FragmentShopItemBinding

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel = ViewModelProvider(this,viewModelFactory)[ShopItemViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addTextChangeListener()

        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }

        observeViewModel()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun observeViewModel() {

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addShopItem(binding.etName.text?.toString(), binding.etCount.text?.toString())
        }

    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)

        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString()
            )
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shopItemId is absent")
            }
            shopItemId =
                args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun addTextChangeListener() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }


    companion object {

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

        private const val SCREEN_MODE = "extra mode"
        private const val MODE_EDIT = "mode edit"
        private const val MODE_ADD = "mode add"
        private const val MODE_UNKNOWN = ""
        private const val SHOP_ITEM_ID = "extra_shop_item_id"


    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }
}