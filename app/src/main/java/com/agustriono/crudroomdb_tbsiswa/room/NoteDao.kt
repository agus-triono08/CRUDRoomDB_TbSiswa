package com.agustriono.crudroomdb_tbsiswa.room

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(tbSiswa: tb_siswa)

    @Update
    suspend fun updateNote(tbSiswa: tb_siswa)

    @Delete
    suspend fun deleteNote(tbSiswa: tb_siswa)

    @Query("SELECT * FROM tb_siswa")
    suspend fun getNotes(): List<tb_siswa>

    @Query("SELECT * FROM tb_siswa WHERE id=:note_id")
    suspend fun getNote(note_id: Int): List<tb_siswa>

}