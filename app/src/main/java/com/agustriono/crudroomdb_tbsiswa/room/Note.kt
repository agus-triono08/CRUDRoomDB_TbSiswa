package com.agustriono.crudroomdb_tbsiswa.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tb_siswa(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nisn: String,
    val nama: String,
    val alamat: String,
    val asalsekolah: String

)
