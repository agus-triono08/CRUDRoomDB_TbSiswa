package com.agustriono.crudroomdb_tbsiswa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.agustriono.crudroomdb_tbsiswa.databinding.ActivityEditBinding
import com.agustriono.crudroomdb_tbsiswa.room.Contant
import com.agustriono.crudroomdb_tbsiswa.room.NoteDB
import com.agustriono.crudroomdb_tbsiswa.room.tb_siswa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class EditActivity : AppCompatActivity() {

    val db by lazy { NoteDB(this) }
    private var noteId: Int = 0

    private lateinit var binding : ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Contant.TYPE_CREATE -> {
                binding.buttonUpdate.visibility = View.GONE
            }
            Contant.TYPE_READ -> {
                binding.buttonSave.visibility = View.GONE
                binding.buttonUpdate.visibility = View.GONE
                getNote()
            }
            Contant.TYPE_UPDATE -> {
                binding.buttonSave.visibility = View.GONE
                getNote()
            }
        }
    }

    private fun setupListener() {
        binding.buttonSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    tb_siswa(0, binding.editNisn.text.toString(), binding.editNama.text.toString(), binding.editAlamat.text.toString(), binding.editAsalsekolah.text.toString())
                )
                finish()
            }
        }

        binding.buttonUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().updateNote(
                    tb_siswa(noteId, binding.editNisn.text.toString(), binding.editNama.text.toString(), binding.editAlamat.text.toString(), binding.editAsalsekolah.text.toString())
                )
                finish()
            }
        }
    }

    fun getNote(){
        noteId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNote(noteId)[0]
            binding.editNisn.setText(notes.nisn)
            binding.editNama.setText(notes.nama)
            binding.editAlamat.setText(notes.alamat)
            binding.editAsalsekolah.setText(notes.asalsekolah)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
