package com.spartons.livedataexample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class UserAdapter(context: Context, private val userList: List<User>) : RecyclerView.Adapter<UserViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(layoutInflater.inflate(R.layout.users_list, parent, false))
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.userNameTextView.text = user.username
    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
}