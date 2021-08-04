package com.yorukarustudio.shop

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_parking.*
import org.jetbrains.anko.*
import java.net.URL


class ParkingActivity : AppCompatActivity() {
    private val TAG = ParkingActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)

        // 記得要改成http 因為kotlin沒有信任這個https的機制

        val url = "http//data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=f4cc0b12-86ac-40f9-8745-885bddc18f79&rid=0daad6e6-0632-44f5-bd25-5e1de1e9146f"
//        val url = "https://jsonplaceholder.typicode.com/posts/1"

//        val httpAsync = url
//            .httpGet()
//            .responseString { request, response, result ->
//                when (result) {
//                    is Result.Failure -> {
//                        val ex = result.getException()
//                        Log.d(TAG, "xxxx: ${ex}")
//                    }
//                    is Result.Success -> {
//                        val data = result.get()
//                        Log.d(TAG, "Success: data ${data}")
//                    }
//                }
//            }

//        httpAsync.join()


        doAsync {
            val url = URL(url)
            val json = url.readText()
            Log.d(TAG, "onCreate: jjooonn $json")
//            uiThread {
//                Toast.makeText(it, "GGGGGot it", Toast.LENGTH_LONG).show()
//                toast("aaaaa")
//                parseGson(json)
//                alert("Got it", "ALERT") {
//                    okButton {
//                        parseGson(json)
//                    }
//                }
//            }
        }



    }

    private fun parseGson(json: String) {
        val parking = Gson().fromJson<Parking>(json, Parking::class.java)
        Log.d(TAG, "parseGson: ${parking.ParkingLogs.size}")
    }

    inner class ParkingTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val url = URL(params[0])
            val json = url.readText()

            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Toast.makeText(this@ParkingActivity, "got it", Toast.LENGTH_LONG).show()
            info.text = result
        }
    }
}

/*
       "userId": 10,
        "id": 97,
        "title": "quas fugiat ut perspiciatis vero provident",
        "body":
 */

class Parking(val ParkingLogs: List<ParkingLot>)

data class ParkingLot(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
