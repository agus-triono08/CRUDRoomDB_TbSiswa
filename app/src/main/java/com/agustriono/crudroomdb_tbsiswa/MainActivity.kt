package com.agustriono.crudroomdb_tbsiswa

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.agustriono.crudroomdb_tbsiswa.databinding.ActivityMainBinding
import com.agustriono.crudroomdb_tbsiswa.room.Contant
import com.agustriono.crudroomdb_tbsiswa.room.NoteDB
import com.agustriono.crudroomdb_tbsiswa.room.tb_siswa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var noteAdapter: NoteAdapter

    val db by lazy { NoteDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadNote()
    }

    fun loadNote(){
        CoroutineScope(Dispatchers.IO).launch {
            val tb_siswas = db.noteDao().getNotes()
            Log.d("MainActivity", "dbResponse: $tb_siswas")
            withContext(Dispatchers.Main){
                noteAdapter.setData(tb_siswas)
            }
        }
    }

    private fun setupListener() {
        binding.buttonCreate.setOnClickListener {
            intentEdit(0, Contant.TYPE_CREATE)
        }
    }

    fun intentEdit(noteId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(arrayListOf(), object : NoteAdapter.OnAdapterListener{
            override fun onRead(tbSiswa: tb_siswa) {
                intentEdit(tbSiswa.id, Contant.TYPE_READ)
            }

            override fun onUpdate(tbSiswa: tb_siswa) {
                intentEdit(tbSiswa.id, Contant.TYPE_UPDATE)
            }

            override fun onDelete(tbSiswa: tb_siswa) {
                deleteDialog(tbSiswa)
            }

        })
        binding.listNote.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }

    private fun deleteDialog(tbSiswa: tb_siswa){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${tbSiswa.nisn}?" )
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(tbSiswa)
                    loadNote()
                }
            }
        }
        alertDialog.show()
    }
}
