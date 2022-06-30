package com.example.scancoordinate

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.realm.mongodb.User

class listAdapter (val context: Activity, private val arrayList: ArrayList<User>): ArrayAdapter<User>(context, R.layout.list_item, arrayList){

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val inflater: LayoutInflater = LayoutInflater.from(context)
//        val view: View = inflater.inflate(R.layout.list_item, null)
//
//        val koordinat: TextView = view.findViewById(R.id.koordinat)
//
////        koordinat.text = arrayList[position].dataKoordinat
//
//        return view
//    }
}