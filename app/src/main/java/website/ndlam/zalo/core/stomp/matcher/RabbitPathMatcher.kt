package website.ndlam.zalo.core.stomp.matcher

import website.ndlam.zalo.core.stomp.dto.StompHeader
import website.ndlam.zalo.core.stomp.dto.StompMessage


class RabbitPathMatcher : IPathMatcher {
    /**
     * RMQ-style wildcards.
     * See more info [here](https://www.rabbitmq.com/tutorials/tutorial-five-java.html).
     */
    override fun matches(path: String?, msg: StompMessage?): Boolean {
        val dest: String = msg?.findHeader(StompHeader.DESTINATION) ?: return false

        // split it up into ["lorem", "ipsum", "*", "sit"]
        val split = path?.split("\\.".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
            ?: return false
        val transformed = ArrayList<String?>()
        // check for wildcards and replace with corresponding regex
        for (s in split) {
            when (s) {
                "*" -> transformed.add("[^.]+")
                "#" ->
                    // TODO: make this work with zero-word
                    // e.g. "lorem.#.dolor" should ideally match "lorem.dolor"
                    transformed.add(".*")

                else -> transformed.add(s.replace("\\*".toRegex(), ".*"))
            }
        }
        // at this point, 'transformed' looks like ["lorem", "ipsum", "[^.]+", "sit"]
        val sb = StringBuilder()
        for (s in transformed) {
            if (sb.isNotEmpty()) sb.append("\\.")
            sb.append(s)
        }
        val join = sb.toString()

        // join = "lorem\.ipsum\.[^.]+\.sit"
        return dest.matches(join.toRegex())
    }
}