package tk.arnabjportfolio.sam

import okhttp3.OkHttpClient
import android.os.AsyncTask
import okhttp3.Request
import java.io.IOException

/*
 * Created by arnabJ on 20-01-2019.
 */
class AsyncGet : AsyncTask<Void, Void, String> {

    private var mUrl = ""
    private var mAsyncResponse : AsyncResponse? = null
    private var errorMessage = ""

    constructor(url: String, asyncResponse: AsyncResponse) {
        mUrl = url
        mAsyncResponse = asyncResponse
    }

    constructor(attributes: Array<String>, values: Array<String>, url: String, asyncResponse: AsyncResponse) {
        val stringUtils = StringUtils()
        mUrl = stringUtils.generateUrl(url, attributes, values)
        if (mUrl == url)
            errorMessage = FailureMessage.ArraysSizeMismatch.msg
        mAsyncResponse = asyncResponse
    }

    override fun doInBackground(vararg voids: Void): String {
        return if (errorMessage == "") {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url(mUrl)
                .build()

            try {
                client.newCall(request).execute().body()!!.string()
            } catch (e: IOException) {
                e.printStackTrace()
                FailureMessage.RequestFailed.msg
            }
        } else
            errorMessage
    }

    override fun onPostExecute(result: String) {
        mAsyncResponse!!.processFinish(result)
    }
}
