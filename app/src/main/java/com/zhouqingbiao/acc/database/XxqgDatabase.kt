package com.zhouqingbiao.acc.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhouqingbiao.acc.dao.MrdtDao
import com.zhouqingbiao.acc.dao.TzdtDao
import com.zhouqingbiao.acc.entity.Mrdt
import com.zhouqingbiao.acc.entity.Tzdt

@Database(entities = [Mrdt::class, Tzdt::class], version = 1)
abstract class XxqgDatabase : RoomDatabase() {
    abstract fun mrdtDao(): MrdtDao
    abstract fun tzdtDao(): TzdtDao
}