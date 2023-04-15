package com.agustriono.crudroomdb_tbsiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agustriono.crudroomdb_tbsiswa.room.tb_siswa
import kotlinx.android.synthetic.main.adapter_main.view.*


class NoteAdapter (private var tb_siswas: ArrayList<tb_siswa>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_main,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = tb_siswas.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = tb_siswas[position]
        holder.view.tv_nisn.text = note.nisn
        holder.view.tv_nama.text = note.nama
        holder.view.tv_alamat.text = note.alamat
        holder.view.tv_asalsekolah.text = note.asalsekolah
        holder.view.tv_nisn.setOnClickListener {
            listener.onRead(note)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(note)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(note)
        }

    }

    inner class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<tb_siswa>) {
        tb_siswas.clear()
        tb_siswas.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onRead(tbSiswa: tb_siswa)
        fun onUpdate(tbSiswa: tb_siswa)
        fun onDelete(tbSiswa: tb_siswa)
    }
}
