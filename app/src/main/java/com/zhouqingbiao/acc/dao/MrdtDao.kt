package com.zhouqingbiao.acc.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zhouqingbiao.acc.entity.Mrdt

@Dao
interface MrdtDao {
    @Insert
    fun insert(vararg mrdt: Mrdt)

    @Query("SELECT * FROM mrdt WHERE t = :t AND ts = :ts")
    fun findByTs(t: String, ts: String): Mrdt
}