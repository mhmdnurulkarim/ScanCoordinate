package com.example.scancoordinate

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Model: RealmObject() {
    private var id:Int = 0
    private var nama: String = ""
    private var koordinat: String = ""

    fun setId(id:Int){
        this.id = id
    }
    fun getId(): Int{
        return id
    }

    fun setNama(nama:String){
        this.nama = nama
    }
    fun getNama(): String{
        return nama
    }

    fun setkoordinat(koordinat:String){
        this.koordinat = koordinat
    }
    fun getkoordinat(): String{
        return koordinat
    }
}