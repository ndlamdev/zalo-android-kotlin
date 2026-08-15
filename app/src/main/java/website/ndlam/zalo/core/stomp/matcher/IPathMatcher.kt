package website.ndlam.zalo.core.stomp.matcher

import website.ndlam.zalo.core.stomp.dto.StompMessage


interface IPathMatcher {
    fun matches(path: String?, msg: StompMessage?): Boolean
}