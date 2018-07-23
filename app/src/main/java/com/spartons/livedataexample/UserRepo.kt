package com.spartons.livedataexample

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class UserRepo {

    private val userList: MutableList<User> = ArrayList()

    init {
        for (i in 1..100)
            userList.add(User("Hello user $i"))
    }

    fun searchUserWithName(name: String): LiveData<List<User>> {
        val searchUserList = ArrayList<User>()
        for (user in userList)
            if (user.username.contains(regex = Regex(name)))
                searchUserList.add(user)
        val temp = MutableLiveData<List<User>>()
        temp.value = searchUserList
        return temp
    }
}
