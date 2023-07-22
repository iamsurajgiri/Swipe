package surajgiri.swipe.listproduct.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import surajgiri.core.data.response.ProductResponse
import surajgiri.swipe.databinding.FragmentListProductBinding
import surajgiri.swipe.listproduct.viewmodel.ListProductViewModel

class ListProductFragment : Fragment() {

    private val viewModel: ListProductViewModel by viewModel()

    //Fragment Binding
    private var _binding: FragmentListProductBinding? = null

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
        _binding = FragmentListProductBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ProductResponse.Loading -> {
                    //todo Show loading indicator
                }

                is ProductResponse.Success -> {
                    //todo Update adapter with data
                    binding?.text?.text = "${response.data}"
                }

                is ProductResponse.Error -> {
                    //todo  Show error message
                }
            }
        }

        viewModel.getProducts()
    }
}
