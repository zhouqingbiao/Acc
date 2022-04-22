package com.zhouqingbiao.acc.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zhouqingbiao.acc.entity.Mrdt

@Dao
interface MrdtDao {
    @Insert
    fun insert(vararg mrdt: Mrdt)

    @Query("SELECT * FROM mrdt WHERE t = :t AND ts = :ts")
    fun findByTAndTs(t: String, ts: String): List<Mrdt>

    @Query("SELECT * FROM mrdt WHERE id = :id")
    fun findById(id: Long): Mrdt

    @Delete
    fun delete(vararg mrdt: Mrdt)
}