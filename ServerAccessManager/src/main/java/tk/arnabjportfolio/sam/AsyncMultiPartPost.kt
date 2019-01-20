package tk.arnabjportfolio.sam

import android.os.AsyncTask
import okhttp3.*
import java.io.File
import java.io.IOException

/*
 * Created by arnabJ on 20-01-2019.
 */
class AsyncMultiPartPost(private val mAttributes: Array<String>, private val mValues: Array<String>,
                         private val mFileAttributes: Array<String>, private val mFileValues: Array<String>,
                         private val mUrl: String, private val mAsyncResponse: AsyncResponse) :
    AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg voids: Void): String? {
        if ((mAttributes.size == mValues.size) and (mFileAttributes.size == mFileValues.size)) {
            val client = OkHttpClient()

            val formBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            for(i in mAttributes.indices)
                formBuilder.addFormDataPart(mAttributes[i], mValues[i])
            for (i in mFileAttributes.indices)
                formBuilder.addFormDataPart(mFileAttributes[i], mFileValues[i], RequestBody.create(
                    MediaType.parse("*/*"), File(mFileValues[i])
                ))

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
