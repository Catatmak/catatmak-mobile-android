package com.bangkit.catatmak.ui.add_transaction

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bangkit.catatmak.R
import com.bangkit.catatmak.databinding.FragmentAddTransactionBinding
import com.bangkit.catatmak.ui.add_transaction.add_with_photo.AddWithPhotoActivity
import com.bangkit.catatmak.utils.getImageUri

class AddTransactionFragment : Fragment() {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding

    private var currentImageUri: Uri? = null

    var isSheetShown = false

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireActivity(), "Permission request granted", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireActivity(), "Permission request denied", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireActivity(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTransactionBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        setUpAction()

    }


    private fun setUpAction() {
        binding?.btnRecordManually?.setOnClickListener {
            if (!isSheetShown) {
                val modalBottomSheet = AddTransactionSheetFragment()
                modalBottomSheet.show(childFragmentManager, AddTransactionSheetFragment.TAG)
                isSheetShown = true
            }
        }
        binding?.btnRecordWithPhoto?.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Pilih Opsi")
            val options = arrayOf("Galeri", "Kamera")
            dialog.setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        startGallery()
                    }

                    1 -> {
                        startCamera()
                    }
                }
            }
            dialog.show()
        }
        binding?.btnRecordWithWhatsapp?.setOnClickListener {
           openWhatsApp()
        }
    }

    private fun openWhatsApp() {
        val phone = getString(R.string.catatmak_phone)
        val message =  getString(R.string.send_message)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        intent.data = Uri.parse("https://wa.me/$phone?text=$message")

        context?.startActivity(intent)
    }
    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            val intent = Intent(requireActivity(), AddWithPhotoActivity::class.java)
            intent.putExtra(AddWithPhotoActivity.IMAGE_URI, currentImageUri.toString())
            startActivity(intent)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            val intent = Intent(requireActivity(), AddWithPhotoActivity::class.java)
            intent.putExtra(AddWithPhotoActivity.IMAGE_URI, currentImageUri.toString())
            startActivity(intent)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}

