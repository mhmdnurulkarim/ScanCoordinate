package com.example.scancoordinate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class listAdapter (var mycontext:Context, var resource: Int, var items:List<(list)>): ArrayAdapter<list>(mycontext, resource, items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(mycontext)
        val view:View = layoutInflater.inflate(resource, null)

        val namaLokasi = view.findViewById<TextView>(R.id.namaLokasi)
        val koordinat = view.findViewById<TextView>(R.id.koordinat)

        var mItem:list = items[position]
        namaLokasi.text = mItem.namaLokasi
        koordinat.text = mItem.koordinat

        return view
    }

}