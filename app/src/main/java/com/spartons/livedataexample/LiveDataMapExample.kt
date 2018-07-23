package com.spartons.livedataexample

import android.arch.lifecycle.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.live_data_example.*
import kotlinx.android.synthetic.main.live_data_map_example.*

class LiveDataMapExample : AppCompatActivity() {

    companion object {
        fun getMapLiveData(callingClassContext: Context) = Intent(callingClassContext, LiveDataMapExample::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_data_map_example)
        val viewModel = ViewModelProviders.of(this).get(TransformationViewModel::class.java)
        viewModel.userAddedData.observe(this, Observer<String> { t -> Snackbar.make(mapActivityRootView, t!!, Snackbar.LENGTH_SHORT).show() })
        addUserButton.setOnClickListener {
            viewModel.addNewUser(User(addNewEditText.text.toString()))
        }
    }
}

class TransformationViewModel : ViewModel() {

    private val userLiveData = MutableLiveData<User>()

    val userAddedData: LiveData<String> = Transformations.map(userLiveData, ::someFunc)

    private fun someFunc(user: User) = "New user ${user.username} added to database!"

    fun addNewUser(user: User) = apply { userLiveData.value = user }
}