package com.paa.allsafeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.paa.allsafeproject.data_structs.AttachedFile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val REQUEST_CODE_OK = 1
//    private val REQUEST_CODE_ERROR = 4

    private val TAG:String = "ACTIVITY_MAIN"

    var mails:java.util.ArrayList<String> = ArrayList()
    var files:ArrayList<AttachedFile> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        setListeners()
        super.onResume()
    }

    private fun fillMailListView(receivedMails: java.util.ArrayList<String>?) {
        if (receivedMails != null) {
            mails = receivedMails // плохо!
            var ll_mailList: LinearLayout = linearLayout_mailList
            for (i: String in mails) {
                var tw = TextView(this)
                tw.text = i
                ll_mailList.addView(tw)
            }
        } else {
            Log.d(TAG, "fillMailList: ArrayList of received mails is null")
        }
    }

    private fun fillFileList(receivedFiles: ArrayList<AttachedFile>?) {
        Log.d(TAG, "fillFileList: received ArrayList of AttachedFiles = $receivedFiles")
        if (receivedFiles != null) {
            files = receivedFiles
            var adapter:FileListAdapter = FileListAdapter(applicationContext, R.layout.view_file_rec, files)
            MainActivity_ListView_fileList.adapter = adapter
            Log.d(TAG, "files size ${files.size}")
            for (f in files) {
                Log.d(TAG, "DATA STRUCT ${f}")
            }
        } else {
//            Toast.makeText(applicationContext, "Cant fill files NullPointer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListeners() {
        Log.d(TAG, "setListeners method")
        button_tomails.setOnClickListener {
            val editMailsIntent = Intent(this, MailListActivity::class.java)
            editMailsIntent.putStringArrayListExtra("MAILS", mails)
            Log.d(TAG, "Sending ${mails}")
            startActivityForResult(editMailsIntent,1)
        }
        button_toFiles.setOnClickListener {
            val editFilesIntent = Intent(this, FileListActivity::class.java)
            if(files.size!=0) editFilesIntent.putExtra("FILES", files)
            startActivityForResult(editFilesIntent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult")
        // отображение почт
        fillMailListView(data?.getStringArrayListExtra("MAILS"))
        // отображение файлов
        fillFileList(data?.getParcelableArrayListExtra("FILES"))
        super.onActivityResult(requestCode, resultCode, data)
    }
}
