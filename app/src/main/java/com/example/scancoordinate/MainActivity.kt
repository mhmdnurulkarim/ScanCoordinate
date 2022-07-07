package com.example.scancoordinate

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.scancoordinate.databinding.ActivityMainBinding
import com.example.scancoordinate.databinding.InputDialogBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import io.realm.Realm
import io.realm.exceptions.RealmException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var binding2: InputDialogBinding
    lateinit var realm: Realm
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    lateinit var Models: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRealm()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

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
        var listItem = mutableListOf<list>()
        for (i in dataKoordinat.indices){
            listItem.add(list(dataNamaKoordinat[i], dataKoordinat[i]))
        }
        binding.lvKoordinat.adapter = listAdapter(this, R.layout.list_item, listItem)
        binding.lvKoordinat.isClickable = true

        val alertDialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.input_dialog, null)
        alertDialog.setCancelable(true)
        alertDialog.setView(view)
        val alertDialogView = alertDialog.create()

        binding.lvKoordinat.setOnItemClickListener { parent, view, position, id ->
            alertDialogView.show()

            //Tidak bisa berjalan, masalahnya terdapat pada binding2
//            binding2.RealmId.setText(dataNamaKoordinat[position]+1)
//            binding2.ETinputNama.setText(dataNamaKoordinat[position],TextView.BufferType.EDITABLE)
//            binding2.ETinputCo.setText(dataKoordinat[position],TextView.BufferType.EDITABLE)

////            Delete
//            binding2.delete.setOnClickListener{
//                alertDialogView.dismiss()
//            }
//
////            Copy
//            binding2.copy.setOnClickListener{
//                val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
//                val clipCoordinat = ClipData.newPlainText("Coordinate", binding2.ETinputCo.text)
//                clipboardManager.setPrimaryClip(clipCoordinat)
//
//                Toast.makeText(this, "Koordinat telah dicopy", Toast.LENGTH_SHORT).show()
//                alertDialogView.dismiss()
//            }
//
////            Update
//            binding2.update.setOnClickListener{
//                alertDialogView.dismiss()
//            }
        }

        binding.gps.setOnClickListener{
            checkLocationPermission()
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

    fun getAllUser(){
        realm.where(Model::class.java).findAll().let {
        }
    }

    private fun checkLocationPermission() {
        val task = fusedLocationProviderClient.lastLocation
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }
        task.addOnSuccessListener {
            if (it != null){
                binding.ETinputCo.setText("${it.latitude}, ${it.longitude}", TextView.BufferType.EDITABLE)
            }
        }
    }
}
