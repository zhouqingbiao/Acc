package com.zhouqingbiao.acc.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tzdt(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    /**
     * 题
     */
    val t: String,
    /**
     * 答案
     */
    val da: String
)