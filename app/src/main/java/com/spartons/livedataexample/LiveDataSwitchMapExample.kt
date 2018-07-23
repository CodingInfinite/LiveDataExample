package com.spartons.livedataexample

import android.arch.lifecycle.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.live_data_swicth_map_example.*

class LiveDataSwitchMapExample : AppCompatActivity() {

    companion object {
        fun getSwitchMapLiveData(callingClassContext: Context) = Intent(callingClassContext, LiveDataSwitchMapExample::class.java)
    }

    private val userList: MutableList<User> = ArrayList()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_data_swicth_map_example)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userAdapter = UserAdapter(this, userList)
        userRecyclerView.adapter = userAdapter
        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        addUserButton.setOnClickListener { viewModel.searchUserByName(addNewEditText.text.toString()) }
        viewModel.userNameResult.observe(this, Observer {
            if (userList.isNotEmpty())
                userList.clear()
            userList.addAll(it!!)
            userAdapter.notifyDataSetChanged()
        })
    }
}

class UserViewModel : ViewModel() {

    private val query = MutableLiveData<String>()
    private val userRepo = UserRepo()

    val userNameResult: LiveData<List<User>> = Transformations.switchMap(
            query,
            ::temp
    )

    private fun temp(name: String) = userRepo.searchUserWithName(name)

    fun searchUserByName(name: String) = apply { query.value = name }
}