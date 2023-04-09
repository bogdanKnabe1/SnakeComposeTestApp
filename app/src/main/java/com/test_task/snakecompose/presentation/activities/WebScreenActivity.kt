package com.test_task.snakecompose.presentation.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.test_task.snakecompose.databinding.ActivityWebviewBinding
import com.test_task.snakecompose.domain.utils.CAMERA_PERMISSION_CODE
import com.test_task.snakecompose.domain.utils.web_link_key
import com.test_task.snakecompose.presentation.viewmodels.AppViewModel


class WebScreenActivity : AppCompatActivity() {

    private val appViewModel by lazy {
        ViewModelProvider(this)[AppViewModel::class.java]
    }

    private lateinit var binding: ActivityWebviewBinding
    private var cameraUploadCallback: ValueCallback<Array<Uri>>? = null
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    private lateinit var photoPickerLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.extras?.getString(web_link_key).orEmpty()

        binding.initWebView(url)
    }

    private fun ActivityWebviewBinding.initWebView(url: String) {
        onBackPressHandle()

        setUpWebView()

        webView.loadUrl(url)

        whenPageLoadingFinished()
        loadImage()
        downloadFiles()
    }

    private fun ActivityWebviewBinding.loadImage() {
        webView.webChromeClient = object : WebChromeClient() {

            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                cameraUploadCallback = filePathCallback
                val mimeTypes = arrayOf("image/*")
                photoPickerLauncher.launch(mimeTypes)
                return true
            }
        }
        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val photoUri = result.data?.data
                    val resultArray = photoUri?.let { arrayOf(it) }
                    cameraUploadCallback?.onReceiveValue(resultArray)
                    cameraUploadCallback = null
                }
            }
        photoPickerLauncher =
            registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { result ->
                if (result != null) {
                    val resultArray = result.map { it }.toTypedArray()
                    cameraUploadCallback?.onReceiveValue(resultArray)
                    cameraUploadCallback = null
                }
            }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun ActivityWebviewBinding.setUpWebView() {
        if (ContextCompat.checkSelfPermission(
                this@WebScreenActivity,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@WebScreenActivity,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false

    }

    private fun ActivityWebviewBinding.whenPageLoadingFinished() {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                appViewModel.saveLinkToDatabase(url)
            }
        }
    }

    private fun ActivityWebviewBinding.downloadFiles() {
        webView.setDownloadListener { url, _, _, _, _ ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val fileName = URLUtil.guessFileName(url, null, null)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        }
    }

    private fun ActivityWebviewBinding.onBackPressHandle() {
        onBackPressedDispatcher.addCallback(this@WebScreenActivity) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                Toast.makeText(this@WebScreenActivity, "YOU SHALL NOT PASS", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.webView.destroy()
    }
}