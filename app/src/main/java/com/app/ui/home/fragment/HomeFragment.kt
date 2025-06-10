package com.app.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.R
import com.app.data.model.MultiDataModel
import com.app.data.model.User
import com.app.databinding.HomeFragmentHomeBinding
import com.app.databinding.ItemLayoutBinding
import com.app.ui.adapter.BaseMultiTypeRecyclerView
import com.app.ui.adapter.BaseRecyclerView
import com.app.ui.base.BaseFragment
import com.app.ui.home.viewmodel.HomeViewModel
import com.app.utils.convertAnyToModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentHomeBinding>() {

    private lateinit var mainViewModel: HomeViewModel
    private lateinit var myAdapter: BaseRecyclerView<User, ItemLayoutBinding>
    private lateinit var adapter: BaseMultiTypeRecyclerView<MultiDataModel>
    private val list = arrayListOf(
        MultiDataModel(0, "User Data 1"),
        MultiDataModel(1, User("Dhanraj", "we", "sda", "dhanraj@gmai.com")),
    )

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean,
    ): HomeFragmentHomeBinding {
        return HomeFragmentHomeBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        )[HomeViewModel::class.java]

        mainViewModel.getUserData()


    }

    private fun onLoading(it: Boolean) {
        if (it) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }


    private fun onData(data: MutableList<User>) {
        if (data.isNotEmpty()) {
            data.take(2).forEachIndexed { _, user ->
                list.add(MultiDataModel(1, user))
            }
            list.add(MultiDataModel(0, "User Data 2"))
            data.take(4).forEachIndexed { _, user ->
                list.add(MultiDataModel(1, user))
            }
            adapter.setData(list)
        } else {
            adapter.noDataFound(resources.getString(R.string.no_data_found))
        }
        Log.e("HomeFragment", data.toString())
    }

    override fun onViewCreated() {
        setupUI()
    }

    private fun setupUI() = with(binding) {
        myAdapter =
            BaseRecyclerView(requireActivity(), itemBinding = { layoutInflater, viewGroup, b ->
                ItemLayoutBinding.inflate(layoutInflater, viewGroup, b)
            }) { binding, model, _ ->
                val context = binding.root.context
                model.apply {
                    binding.textViewUserName.text = name
                    binding.textViewUserEmail.text = email
                    Glide.with(context)
                        .load(avatar)
                        .into(binding.imageViewAvatar)
                }

                binding.root.setOnClickListener {
                    Toast.makeText(context, model.name, Toast.LENGTH_SHORT).show()
                }
            }

        val map = mapOf(
            0 to R.layout.item_text,
            1 to R.layout.item_layout,
        )

        adapter = BaseMultiTypeRecyclerView(requireActivity(), map) { view, _, position ->
            when (adapter.getItemViewType(position)) {
                0 -> {
                    // Bind data for view type 0
                    val textView: TextView = view.findViewById(R.id.textView)
                    textView.text = list[position].data.toString()
                }

                1 -> {
                    // Bind data for view type 1
                    val textViewUserName: TextView = view.findViewById(R.id.textViewUserName)
                    val textViewUserEmail: TextView = view.findViewById(R.id.textViewUserEmail)
                    val imageView: ImageView = view.findViewById(R.id.imageViewAvatar)
                    val data = list[position].data
                    val user = data.convertAnyToModel<User>()

                    textViewUserName.text = user.name.toString()
                    textViewUserEmail.text = user.email.toString()
                    Glide.with(requireContext())
                        .load(user.avatar)
                        .into(imageView)
                    view.setOnClickListener {
                        Toast.makeText(requireContext(), user.name, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        recyclerView.adapter = adapter
        adapter.setData(list)
        btnLoad.setOnClickListener {
            mainViewModel.getUserData()
            Log.e("HomeFragment", "click")
        }
    }

    override fun onBackActionPerform(): Boolean {
        return false
    }
}