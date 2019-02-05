package tk.arnabjportfolio.sam

import org.json.JSONArray
import org.json.JSONException

class StringToArray {

    fun convertDataStringToJSonArray(result: String): JSONArray? {
        return try {
            JSONArray(result)
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    fun convertJSonArrayToArrayList(array: JSONArray, attributes: Array<String>): ArrayList<ArrayList<String>>? {
        val lenArray = array.length()

        return if (lenArray > 0) {
            val dataList = ArrayList<ArrayList<String>>()
            for (jIndex in 0 until lenArray) {
                val innerObject = array.getJSONObject(jIndex)
                val temp = ArrayList<String>()
                for (index in attributes.indices) {
                    temp += innerObject.getString(attributes[index])
                }
                dataList.add(temp)
            }
            dataList
        } else null
    }

    fun convertDataStringToArrayList(result: String, attributes: Array<String>): ArrayList<ArrayList<String>>? {
        try {
            val array = JSONArray(result)
            val lenArray = array.length()

            return if (lenArray > 0) {
                val dataList = ArrayList<ArrayList<String>>()
                for (jIndex in 0 until lenArray) {
                    val innerObject = array.getJSONObject(jIndex)
                    val temp = ArrayList<String>()
                    for (index in attributes.indices) {
                        temp += innerObject.getString(attributes[index])
                    }
                    dataList.add(temp)
                }
                dataList
            } else null
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }
}