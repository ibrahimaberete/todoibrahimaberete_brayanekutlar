package com.example.todoibrahimaberete_brayanekutlar.user

import android.Manifest
import android.app.Notification
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.todoibrahimaberete_brayanekutlar.R
import com.example.todoibrahimaberete_brayanekutlar.databinding.ActivityUserBinding
import com.example.todoibrahimaberete_brayanekutlar.databinding.FragmentTaskListBinding
import com.example.todoibrahimaberete_brayanekutlar.network.Api
import com.example.todoibrahimaberete_brayanekutlar.network.UserWebService
import com.google.android.material.snackbar.Snackbar
import com.google.modernstorage.permissions.RequestAccess
import com.google.modernstorage.permissions.StoragePermissions
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class UserInfoActivity : AppCompatActivity(){

    private var _binding: ActivityUserBinding? = null
    private val binding get() = _binding!!
    private val webService = Api.userWebService

    private val getPhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->

        binding.imageView.load(bitmap) // afficher


        if (bitmap == null) return@registerForActivityResult

        lifecycleScope.launch {
            webService.updateAvatar(bitmap.toRequestBody())
        }

    }

    // register
    private fun Uri.toRequestBody(): MultipartBody.Part {
        val fileInputStream = contentResolver.openInputStream(this)!!
        val fileBody = fileInputStream.readBytes().toRequestBody()
        return MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = fileBody
        )
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        binding.imageView.load(uri)
        if (uri == null) return@registerForActivityResult
        lifecycleScope.launch{
           val response=  webService.updateAvatar(uri.toRequestBody())
            binding.imageView.load(response.body()?.avatar){
                error(R.drawable.ic_launcher_background)
                placeholder(R.drawable.ic_launcher_background)
            }
        }
        // au retour de la galerie on fera quasiment pareil qu'au retour de la caméra mais avec une URI àla place du bitmap
    }
    private val requestCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { accepted ->
            if (accepted)
                getPhoto.launch();
            // lancer l'action souhaitée
            else showMessage("error")// afficher une explication
        }
    private fun showMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setAction("Open Settings") {
                val intent = Intent(
                    ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                startActivity(intent)
            }
            .show()
    }
    private fun launchCameraWithPermission() {
        val camPermission = Manifest.permission.CAMERA
        val permissionStatus = checkSelfPermission(camPermission)
        val isAlreadyAccepted = permissionStatus == PackageManager.PERMISSION_GRANTED
        val isExplanationNeeded = shouldShowRequestPermissionRationale(camPermission)
        when {
            isAlreadyAccepted -> getPhoto// lancer l'action souhaitée
                isExplanationNeeded -> showMessage("error") // afficher une explication
            else -> requestCamera// lancer la demande de permission
        }
    }
    private fun Bitmap.toRequestBody(): MultipartBody.Part {
        val tmpFile = File.createTempFile("avatar", "jpeg")
        tmpFile.outputStream().use {
            this.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                it
            ) // this est le bitmap dans ce contexte
        }
        return MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = tmpFile.readBytes().toRequestBody()
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        _binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imageView.load("https://goo.gl/gEgYUd") {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.takePictureButton.setOnClickListener{
            launchCameraWithPermission()
            getPhoto.launch()
        }

        binding.uploadImageButton.setOnClickListener{
            openGallery()
        }

        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()
           binding.imageView.load(userInfo?.avatar) {
                error(R.drawable.ic_launcher_background) // affiche une image par défaut en cas d'erreur:
            }
        }

    }
    // launcher pour la permission d'accès au stockage
    val requestReadAccess = registerForActivityResult(RequestAccess()) { hasAccess ->
        if (hasAccess) {
            galleryLauncher.launch("image/*")
        } else {
            // message
            showMessage("error")
        }
    }
    fun openGallery() {
        requestReadAccess.launch(
            RequestAccess.Args(
                action = StoragePermissions.Action.READ,
                types = listOf(StoragePermissions.FileType.Image),
                createdBy = StoragePermissions.CreatedBy.AllApps
            )
        )
    }



    }