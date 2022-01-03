package com.nicoalex.todo.User

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.google.modernstorage.mediastore.FileType
import com.google.modernstorage.mediastore.MediaStoreRepository
import com.google.modernstorage.mediastore.SharedPrimary
import com.nicoalex.todo.R
import com.nicoalex.todo.databinding.ActivityFormBinding
import com.nicoalex.todo.databinding.ActivityUserInfoBinding
import com.nicoalex.todo.network.Api
import com.nicoalex.todo.network.UserInfo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private val userWebService = Api.userWebService
    val mediaStore by lazy { MediaStoreRepository(this) }
    val viewModel = UserInfoViewModel()

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { accepted ->
            if (accepted) handleImage(photoUri);
            else Snackbar.make(binding.root, "Échec!", Snackbar.LENGTH_LONG).show();
        }
    private val permissionAndCameraLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        // pour simplifier on ne fait rien ici, il faudra que le user re-clique sur le bouton

    }

    private var takePictureButton: Button? = null
    private var uploadImageButton: Button? = null

    private lateinit var photoUri: Uri

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if(it != null) {
            handleImage(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        takePictureButton = binding.takePictureButton
        uploadImageButton = binding.uploadImageButton
        takePictureButton?.setOnClickListener {
            launchCameraWithPermission();
        }
        uploadImageButton?.setOnClickListener {
            galleryLauncher.launch("image/*");
        }

        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            binding.imageView.load(userInfo.avatar) {
                // affiche une image par défaut en cas d'erreur:
                error(R.drawable.ic_launcher_background)
            }
        }

    }
    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { accepted ->
            if (accepted) launchCamera()// lancer l'action souhaitée
            else showExplanation();// afficher une explication
        }

    private fun launchCameraWithPermission() {
        val camPermission = Manifest.permission.CAMERA
        val permissionStatus = checkSelfPermission(camPermission)
        val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val isAlreadyAccepted = permissionStatus == PackageManager.PERMISSION_GRANTED
        val isExplanationNeeded = shouldShowRequestPermissionRationale(camPermission)
        when {
            mediaStore.canWriteSharedEntries() && isAlreadyAccepted -> launchCamera() // lancer l'action souhaitée
            isExplanationNeeded -> showExplanation() // afficher une explication
            else -> permissionAndCameraLauncher.launch(arrayOf(camPermission, storagePermission)) // lancer la demande de permission
        }
    }

    private fun showExplanation() {
        // ici on construit une pop-up système (Dialog) pour expliquer la nécessité de la demande de permission
        AlertDialog.Builder(this)
            .setMessage("🥺 On a besoin de la caméra, vraiment! 👉👈")
            .setPositiveButton("Bon, ok") { _, _ -> launchAppSettings() }
            .setNegativeButton("Nope") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun launchAppSettings() {
        // Cet intent permet d'ouvrir les paramètres de l'app (pour modifier les permissions déjà refusées par ex)
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        // ici pas besoin de vérifier avant car on vise un écran système:
        startActivity(intent)
    }

    private fun handleImage(imageUri: Uri) {
        // afficher l'image dans l'ImageView
        viewModel.updateAvatar(convert(imageUri))
    }

    private fun launchCamera() {
        lifecycleScope.launchWhenStarted {
            photoUri = mediaStore.createMediaUri(
                filename = "picture-${UUID.randomUUID()}.jpg",
                type = FileType.IMAGE,
                location = SharedPrimary
            ).getOrThrow()
            cameraLauncher.launch(photoUri)
        }

    }

    private fun convert(uri: Uri): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = contentResolver.openInputStream(uri)!!.readBytes().toRequestBody()
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.refresh();
    }

}