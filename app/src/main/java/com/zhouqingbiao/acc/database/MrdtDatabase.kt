package com.zhouqingbiao.acc.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhouqingbiao.acc.dao.MrdtDao
import com.zhouqingbiao.acc.entity.Mrdt

@Database(entities = [Mrdt::class], version = 1)
abstract class MrdtDatabase : RoomDatabase() {
    abstract fun mrdtDao(): MrdtDao
}