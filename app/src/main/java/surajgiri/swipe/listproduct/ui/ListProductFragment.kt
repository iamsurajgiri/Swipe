package surajgiri.swipe.listproduct.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import surajgiri.core.data.response.ApiResponse
import surajgiri.swipe.R
import surajgiri.swipe.listproduct.viewmodel.ListProductViewModel

class ListProductFragment : Fragment() {

    private val viewModel: ListProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_product, container, false)




        var inc = 1
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    // Show loading indicator
                    Log.i("ProductList", "$inc Products Loading")
                }

                is ApiResponse.Success -> {
                    // Update adapter with data
                    Log.i("ProductList", "$inc Products ${response.data}")
                }

                is ApiResponse.Error -> {
                    // Show error message
                    Log.i("ProductList", "$inc Products Error ${response.message}")
                }
            }
            inc++
        }

        viewModel.getProducts()

        return view
    }
}
