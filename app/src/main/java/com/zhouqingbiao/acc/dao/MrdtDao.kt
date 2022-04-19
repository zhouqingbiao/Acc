package com.zhouqingbiao.acc.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MrdtDao(
    @PrimaryKey
    val id: Long,
    /**
     * 题
     */
    val t: String,
    /**
     * 提示
     */
    val ts: String,
    /**
     * 答案
     */
    val da: String
)
