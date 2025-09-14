package com.example.demo.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.app.base.BaseFragmentWithBinding
import com.app.base.common.State
import com.app.base.utils.click
import com.example.demo.databinding.FragmentAccountBinding
import com.example.demo.ui.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragmentWithBinding<FragmentAccountBinding>() {

    private val viewModel: AccountViewModel by viewModels()

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        }
    }

    private val galleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openGallery()
        }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val bitmap = data?.extras?.get("data") as? Bitmap
            bitmap?.let {
                viewModel.saveImageFromBitmap(requireContext(), it)
            }
        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val uri: Uri? = data?.data
            uri?.let {
                viewModel.saveImageFromUri(requireContext(), it)
            }
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentAccountBinding {
        return FragmentAccountBinding.inflate(inflater)
    }

    override fun init() {
        setupViewModel()
        setupClickListeners()
        observeViewModel()

        viewModel.loadSavedImage(requireContext())
    }

    override fun initData() {
        // Initialize data if needed
    }

    override fun initAction() {
        // Initialize actions if needed
    }

    private fun setupViewModel() {
        // ViewModel is injected by Hilt
    }

    private fun setupClickListeners() {
        binding.btnFromCamera.click {
            checkCameraPermission()
        }

        binding.btnFromGallery.click {
            checkGalleryPermission()
        }

    }

    private fun observeViewModel() {
        viewModel.profileImageState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    // Handle loading state if needed
                }

                is State.Success -> {
                    binding.profileImageView.setImageBitmap(state.data)
                }

                is State.Error -> {
                    // Handle error state if needed
                }

                else -> {
                    // Handle other states if needed
                }
            }
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }

            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun checkGalleryPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    Manifest.permission.READ_MEDIA_IMAGES
                else
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }

            else -> {
                galleryPermissionLauncher.launch(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        Manifest.permission.READ_MEDIA_IMAGES
                    else
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }
}
