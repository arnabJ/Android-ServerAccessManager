package tk.arnabjportfolio.sam

import okhttp3.OkHttpClient
import okhttp3.FormBody
import android.os.AsyncTask
import okhttp3.Request
import java.io.IOException

/*
 * Created by arnabJ on 20-01-2019.
 */
class AsyncPost(private val mAttributes: Array<String>, private val mValues: Array<String>, private val mUrl: String,
                private val mAsyncResponse: AsyncResponse) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg voids: Void): String {
        if (mAttributes.size == mValues.size) {
            val client = OkHttpClient()

            val formBuilder = FormBody.Builder()
            for (i in mAttributes.indices)
                formBuilder.add(mAttributes[i], mValues[i])

            val formBody = formBuilder.build()

            val request = Request.Builder()
                .url(mUrl)
                .post(formBody)
                .build()

            return try {
                client.newCall(request).execute().body()!!.string()
            } catch (e: IOException) {
                e.printStackTrace()
                FailureMessage.RequestFailed.msg
            }
        } else
            return FailureMessage.ArraysSizeMismatch.msg
    }

    override fun onPostExecute(result: String) {
        mAsyncResponse.processFinish(result)
    }
}
