package com.zhouqingbiao.acc.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zhouqingbiao.acc.entity.Mrdt
import com.zhouqingbiao.acc.entity.Tzdt

@Dao
interface TzdtDao {
    @Insert
    fun insert(vararg tzdt: Tzdt)

    @Query("SELECT * FROM tzdt WHERE t = :t")
    fun findByT(t: String): List<Tzdt>

    @Query("SELECT * FROM tzdt WHERE id = :id")
    fun findById(id: Long): Tzdt

    @Delete
    fun delete(vararg tzdt: Tzdt)
}