package website.ndlam.zalo.core.stomp.matcher

import website.ndlam.zalo.core.stomp.dto.StompHeader
import website.ndlam.zalo.core.stomp.dto.StompMessage


class SimplePathMatcher : IPathMatcher {
    override fun matches(path: String?, msg: StompMessage?): Boolean {
        val dest = msg?.findHeader(StompHeader.DESTINATION) ?: return false
        return path.equals(dest)
    }
}