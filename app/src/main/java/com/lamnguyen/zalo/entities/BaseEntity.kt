/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:01 PM-08/07/2025
 *  User: kimin
 **/

package com.lamnguyen.zalo.entities

import java.time.LocalDateTime

open class BaseEntity() {
    lateinit var createdAt: LocalDateTime
    lateinit var updatedAt: LocalDateTime
    var createdBy: String? = null
    var updatedBy: String? = null
    var locked: Boolean = false
    var deleted: Boolean = false
}