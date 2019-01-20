package tk.arnabjportfolio.sam

class StringUtils {

    fun generateUrl(baseUrl: String, attributes: Array<String>, values: Array<String>): String {
        return if (attributes.size == values.size) {
            var temp = attributes[0].plus("=").plus(values[0])
            for (i in 1 until attributes.size)
                temp = temp.plus("&").plus(attributes[i]).plus("=").plus(values[i])
            "$baseUrl?$temp"
        } else
            baseUrl
    }
}