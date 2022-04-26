package com.example.todoibrahimaberete_brayanekutlar.user

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.todoibrahimaberete_brayanekutlar.R
import com.example.todoibrahimaberete_brayanekutlar.databinding.ActivityUserBinding
import com.example.todoibrahimaberete_brayanekutlar.databinding.FragmentTaskListBinding
import com.google.android.material.snackbar.Snackbar


class UserInfoActivity : AppCompatActivity(){

    private var _binding: ActivityUserBinding? = null
    private val binding get() = _binding!!
    private val getPhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        binding.imageView.load(bitmap) // afficher
    }
    private val requestCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { accepted ->
            if (accepted)
                getPhoto.launch();
            // lancer l'action souhaitée
            else showMessage("error")// afficher une explication
        }
    private fun showMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
    private fun launchCameraWithPermission() {
        val camPermission = Manifest.permission.CAMERA
        requestCamera.launch(camPermission)
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

    }

    }