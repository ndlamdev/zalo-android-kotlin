/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:31 PM-08/07/2025
 *  User: kimin
 **/

package com.lamnguyen.zalo.domain.dtos

class AccessTokenPayload : SimplePayload() {
    var refreshTokenId: String? = null
    var roles: MutableSet<String?>? = mutableSetOf()
}