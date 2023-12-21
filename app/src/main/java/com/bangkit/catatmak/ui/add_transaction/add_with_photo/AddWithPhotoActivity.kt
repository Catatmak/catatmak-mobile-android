package com.bangkit.catatmak.ui.add_transaction.add_with_photo

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catatmak.R
import com.bangkit.catatmak.adapter.PreviewAdapter
import com.bangkit.catatmak.data.ResultState
import com.bangkit.catatmak.data.response.OCRDataItem
import com.bangkit.catatmak.databinding.ActivityAddWithPhotoBinding
import com.bangkit.catatmak.ui.ViewModelFactory
import com.bangkit.catatmak.ui.home.BottomSheetDismissListener
import com.bangkit.catatmak.ui.main.MainActivity
import com.bangkit.catatmak.utils.reduceFileImage
import com.bangkit.catatmak.utils.uriToFile
import java.util.Random

interface UpdateTransactionListener {
    fun onUpdateTransaction(updatedTransaction: OCRDataItem)
}

interface DeleteTransactionListener {
    fun onDeleteTransaction(transaction: OCRDataItem)
}

class AddWithPhotoActivity : AppCompatActivity(), UpdateTransactionListener,
    DeleteTransactionListener {

    private lateinit var binding: ActivityAddWithPhotoBinding
    private var imageUri: Uri? = null

    private val viewModel by viewModels<AddWithPhotoViewModel> {
        ViewModelFactory.getInstance(this)
    }

    var isSheetShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWithPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        val upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.dark_blue), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        binding.topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        val imageUriString = intent.getStringExtra(IMAGE_URI)
        imageUri = Uri.parse(imageUriString)

        imageUri.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPreviewImage.setImageURI(it)
        }

        setupRecyclerView()

        imageUri?.let { uri ->
            sendOCR(uri)
        }

        viewModel.listTransaction.observe(this) { result ->
            setTransactionData(result)
        }

        setUpAction()
    }

    private fun sendOCR(uri: Uri) {
        viewModel.sendOCR(uriToFile(uri, this).reduceFileImage()).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                        binding.btnSave.visibility = View.GONE
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        binding.btnSave.visibility = View.VISIBLE
                        val data = result.data.data
                        if (data.isNotEmpty()) {
                            val newList = addIdToOCRData(data)
                            viewModel._listTransaction.value = newList
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(result.error.toString())
                    }
                }
            }
        }
    }

    private fun addIdToOCRData(ocrData: List<OCRDataItem>): List<OCRDataItem> {
        val random = Random()
        return ocrData.map { ocrItem ->
            val uniqueId = random.nextInt(Int.MAX_VALUE)
            ocrItem.copy(id = uniqueId)
        }
    }


    private fun setUpAction() {
        binding.btnSave.setOnClickListener {
            viewModel.updateBulk().observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showLoading(false)
                            showToast(result.data.message)
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is ResultState.Error -> {
                            showLoading(false)
                            showToast(result.error.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onUpdateTransaction(updatedTransaction: OCRDataItem) {
        Log.d("COBA", "$updatedTransaction")
        viewModel.updateSelectedTransaction(updatedTransaction)
    }

    override fun onDeleteTransaction(bulk: OCRDataItem) {
        viewModel.deleteTransaction(bulk)
    }

    private fun setTransactionData(listTransaction: List<OCRDataItem>) {
        val adapter = PreviewAdapter(
            onEditClick = { transaction ->
                if (!isSheetShown) {
                    showUpdateTransactionSheet(transaction)
                }
            },
            onDeleteClick = { transaction -> viewModel.deleteTransaction(transaction) },
            onMenuChanged = { transaction, category ->
                viewModel.updateCategoryTransaction(transaction, category)
            }
        )

        adapter.submitList(listTransaction)
        binding.rvTransactions.adapter = adapter
        if (listTransaction.isNotEmpty()) {
            binding.tvNoTransactionData.visibility = View.GONE
        } else {
            binding.tvNoTransactionData.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(this@AddWithPhotoActivity)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this, message, Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbAddWithPhoto.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showUpdateTransactionSheet(transaction: OCRDataItem) {
        val showBottomSheet = UpdateBulkTransactionSheetFragment(transaction, this, this)
        showBottomSheet.show(supportFragmentManager, UpdateBulkTransactionSheetFragment.TAG)
        isSheetShown = true
    }


    companion object {
        const val IMAGE_URI = "image_uri"
    }
}
