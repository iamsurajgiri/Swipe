package surajgiri.swipe.addproduct.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import surajgiri.core.data.response.ProductResponse
import surajgiri.swipe.R
import surajgiri.swipe.addproduct.viewmodel.AddProductViewModel
import surajgiri.swipe.databinding.FragmentAddProductBinding
import java.io.File

class AddProductFragment : Fragment() {

    private val viewModel: AddProductViewModel by viewModel()

    private lateinit var selectImageIntent: ActivityResultLauncher<String>
    private lateinit var selectImageIntent2: ActivityResultLauncher<Intent>
    private lateinit var multiPermissionCallback: ActivityResultLauncher<Array<String>>

    private var mContext: Context? = null
    //Fragment Binding
    private var _binding: FragmentAddProductBinding? = null

    private val binding get() = _binding

    private var imageBody: MultipartBody.Part? = null

    //product type
    private var productType: String? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multiPermissionCallback =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (checkRequiredImagePermission().not()) {
                    bringImagePicker()
                }else{
                    Toast.makeText(mContext, "Permission not granted", Toast.LENGTH_SHORT).show()
                }

            }
        selectImageIntent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding?.addImage?.setImageURI(uri)
            if (uri != null && mContext != null) {
                lifecycleScope.launch {
                    val image =
                        File(getRealPathFromUri(uri))
                    val requestImage = image.asRequestBody("image/*".toMediaTypeOrNull())
                    imageBody = MultipartBody.Part.createFormData("files[]", image.name, requestImage)
                }
            } else {
                Toast.makeText(mContext, "Couldn't pick image", Toast.LENGTH_SHORT).show()
            }
        }

        selectImageIntent2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != Activity.RESULT_OK) {
                Toast.makeText(requireContext(), "Couldn't pick image", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val uri = it.data?.data
                if (uri != null) {
                    binding?.addImage?.setImageURI(uri)
                    val image =
                        File(getRealPathFromUri(uri))
                    val requestImage = image.asRequestBody("image/*".toMediaTypeOrNull())
                    imageBody = MultipartBody.Part.createFormData("files[]", image.name, requestImage)

                } else {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }

            }
        }



    }

    private fun getRealPathFromUri(uri: Uri): String {
        var realPath = ""
        val cursor: Cursor? = requireActivity().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex: Int = it.getColumnIndex(MediaStore.Images.Media.DATA)
                realPath = it.getString(columnIndex)
            }
        }
        return realPath
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observing the addProductResponse LiveData
        viewModel.addProductResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ProductResponse.Loading -> {

                }
                is ProductResponse.Success -> {
                    val data = response.data

                }
                is ProductResponse.Error -> {
                    val errorMessage = response.message

                }
            }
        }

        //Caption Language
        val productTypeAdapter = ArrayAdapter(
            mContext!!,
            R.layout.simple_spinner_text,
            mutableListOf("Electronics", "Clothes", "Grocery", "Furniture")
        )

        productTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.productTypeSpinner?.apply {
            adapter = productTypeAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                    productType = parent.getItemAtPosition(pos).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
        binding?.addImage?.setOnClickListener {
            bringImagePicker()
        }

        binding?.btnAddProduct?.setOnClickListener {
            addProduct()
        }

    }



    private fun addProduct() {
        val productName = binding?.productName?.text.toString().trim()
        val price = binding?.productPrice?.text.toString().trim()
        val tax = binding?.productTax?.text.toString().trim()
        if (productName.isEmpty() || productType.isNullOrEmpty() || price.isEmpty() || tax.isEmpty()){
            Toast.makeText(mContext, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.addProduct(productName, productType!!, price, tax, imageBody)
    }

    private fun bringImagePicker() {
        if (mContext == null) return
        if (checkRequiredImagePermission() == false) {
            multiPermissionCallback.launch(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                selectImageIntent2.launch(
                    Intent(MediaStore.ACTION_PICK_IMAGES)
                )
                return
            }
            selectImageIntent.launch("image/*")
        }
    }

    private fun checkRequiredImagePermission(): Boolean {
        if (mContext==null) return false

        return ContextCompat.checkSelfPermission(
            mContext!!,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }




}