package website.ndlam.zalo.core.stomp.matcher

import website.ndlam.zalo.core.stomp.StompClient
import website.ndlam.zalo.core.stomp.dto.StompHeader
import website.ndlam.zalo.core.stomp.dto.StompMessage


class SubscriptionPathMatcher(private val stompClient: StompClient) : IPathMatcher {
    override fun matches(path: String?, msg: StompMessage?): Boolean {
        val pathSubscription = stompClient.getTopicId(path) ?: return false
        val subscription = msg!!.findHeader(StompHeader.SUBSCRIPTION)
        return pathSubscription == subscription
    }
}