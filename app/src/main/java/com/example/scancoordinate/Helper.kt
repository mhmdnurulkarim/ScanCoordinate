package com.example.scancoordinate

import io.realm.Realm
import io.realm.RealmResults

class Helper {
    lateinit var realm: Realm

    fun save(model: Model?) {
        realm.executeTransaction{
            val s = model?.let { it -> realm.copyToRealm(it) }
        }
    }

    fun retrieve(): List<String>? {
        val Models: ArrayList<String> = ArrayList()
        val model: RealmResults<Model> = realm.where(Model::class.java).findAll()
        for (s in model) {
            Models.add(s.getId().toString())
            Models.add(s.getNama())
            Models.add(s.getkoordinat())
        }
        return Models
    }
}