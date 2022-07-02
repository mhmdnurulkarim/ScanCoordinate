package com.example.scancoordinate

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.indices
import com.example.scancoordinate.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Listview
        var dataNamaKoordinat = arrayOf("Roma", "Bali", "Toraja", "Rusia", "Arab Saudi","Bengkulu", "Amerika Serikat", "Danau Toba", "China", "Turki")
        var dataKoordinat = arrayOf(
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
        binding.listview.adapter = listAdapter(this, R.layout.list_item, daftar)
        binding.listview.isClickable = true
        binding.listview.setOnItemClickListener { parent, view, position, id ->
            binding.ETinputNama.setText(dataNamaKoordinat[position],TextView.BufferType.EDITABLE)
            binding.ETinputCo.setText(dataKoordinat[position],TextView.BufferType.EDITABLE)
        }

        //Save
        binding.save

        //Delete
        binding.delete

        //Copy
        binding.copy.setOnClickListener{
            copyCoordinate()
        }

        //Help
        binding.help.isClickable = true
        binding.help.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://support.google.com/maps/answer/18539?hl=id&co=GENIE.Platform%3DAndroid")))
        }

        //RealmRealm.init(this)
//        val config = RealmConfiguration.Builder()
//           .name("Scan.db")
//           .schemaVersion(1)
//           .build()
//        Realm.setDefaultConfiguration(config)
    }

    private fun copyCoordinate() {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipCoordinat = ClipData.newPlainText("Coordinate", binding.ETinputCo.text)
        clipboardManager.setPrimaryClip(clipCoordinat)

        Toast.makeText(this, "Koordinat telah dicopy", Toast.LENGTH_SHORT).show()
    }
}
