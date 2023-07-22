package surajgiri.swipe.addproduct.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import surajgiri.core.data.response.ProductResponse
import surajgiri.swipe.addproduct.viewmodel.AddProductViewModel
import surajgiri.swipe.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment() {

    private val viewModel: AddProductViewModel by viewModel()

    //Fragment Binding
    private var _binding: FragmentAddProductBinding? = null

    private val binding get() = _binding

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                    // Handle loading state
                    Log.i("AddProduct", "Loading")

                }
                is ProductResponse.Success -> {
                    // Handle success state
                    val data = response.data
                    Log.i("AddProduct", "Sucess: $data")
                }
                is ProductResponse.Error -> {
                    // Handle error state
                    val errorMessage = response.message
                    Log.i("AddProduct", "Error: $errorMessage")

                }
            }
        }

    }

}