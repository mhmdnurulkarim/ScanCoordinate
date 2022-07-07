package com.example.scancoordinate

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.scancoordinate.databinding.ActivityMainBinding
import com.example.scancoordinate.databinding.InputDialogBinding
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.exceptions.RealmException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var binding2: InputDialogBinding
    lateinit var realm: Realm
//    lateinit var Models: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRealm()

//        val helper = Helper()
//        Models = helper.retrieve()!!
//
//        binding.lvKoordinat.adapter = listAdapter(this, R.layout.list_item, Models)

        //Listview
        val dataNamaKoordinat = arrayOf("Roma", "Bali", "Toraja", "Rusia", "Arab Saudi","Bengkulu", "Amerika Serikat", "Danau Toba", "China", "Turki")
        val dataKoordinat = arrayOf(
            "-3.8958674397239164, 102.27939432259899",
            "-0.9139841225013886, 100.34937045954474",
            "2.7875607432569285, 98.74984595792846",
            "-102.8764841713407713, -102.0495679887555346",
            "15.818964644328123, 47.65456585981439",
            "23.589430410791227, 45.47927278632665",
            "23.770533958363913, 53.98269097125534",
            "39.130608892314534, 35.39745664286574",
            "62.80644954491442, 93.4268471229949",
            "36.688622511141425, 139.4815332488359",
        )
        var daftar = mutableListOf<list>()
        for (i in dataKoordinat.indices){
            daftar.add(list(dataNamaKoordinat[i], dataKoordinat[i]))
        }
        binding.lvKoordinat.adapter = listAdapter(this, R.layout.list_item, daftar)
        binding.lvKoordinat.isClickable = true
        binding.lvKoordinat.setOnItemClickListener { parent, view, position, id ->
            val alertDialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.input_dialog, null)
            alertDialog.setView(view)
            val alertDialogView = alertDialog.show()
            binding2.RealmId.setText(dataNamaKoordinat[position]+1)
            binding2.ETinputNama.setText(dataNamaKoordinat[position],TextView.BufferType.EDITABLE)
            binding2.ETinputCo.setText(dataKoordinat[position],TextView.BufferType.EDITABLE)

            //Delete
            binding2.delete.setOnClickListener{
                alertDialogView.dismiss()
            }

            //Copy
            binding2.copy.setOnClickListener{
                copyCoordinate()
                alertDialogView.dismiss()
            }

            //Update
            binding2.update.setOnClickListener{
                alertDialogView.dismiss()
            }
        }

        //Save
        binding.save.setOnClickListener{
            realm.beginTransaction()
            var count:Int = 0
            realm.where(Model::class.java).findAll().let {
                for (i in it){
                    count++
                }
            }
            try {
                val user = realm.createObject(Model::class.java)
                user.setId(count+1)
                user.setNama(binding.ETinputNama.text.toString())
                user.setkoordinat(binding.ETinputCo.text.toString())

                binding.ETinputNama.setText("")
                binding.ETinputCo.setText("")

                realm.commitTransaction()
            } catch(e: RealmException){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        //Help
        binding.help.isClickable = true
        binding.help.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://support.google.com/maps/answer/18539?hl=id&co=GENIE.Platform%3DAndroid")))
        }
    }

    private fun initRealm() {
        realm = Realm.getDefaultInstance()
    }

    private fun copyCoordinate() {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipCoordinat = ClipData.newPlainText("Coordinate", binding2.ETinputCo.text)
        clipboardManager.setPrimaryClip(clipCoordinat)

        Toast.makeText(this, "Koordinat telah dicopy", Toast.LENGTH_SHORT).show()
    }

    fun getAllUser(){
        realm.where(Model::class.java).findAll().let {
        }
    }
}
