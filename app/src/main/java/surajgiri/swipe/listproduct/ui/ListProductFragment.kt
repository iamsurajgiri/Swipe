package surajgiri.swipe.listproduct.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import surajgiri.core.data.response.ProductResponse
import surajgiri.core.model.Product
import surajgiri.swipe.R
import surajgiri.swipe.databinding.FragmentListProductBinding
import surajgiri.swipe.listproduct.adapters.ProductAdapter
import surajgiri.swipe.listproduct.viewmodel.ListProductViewModel

class ListProductFragment : Fragment() {

    private val viewModel: ListProductViewModel by viewModel()

    //Fragment Binding
    private var _binding: FragmentListProductBinding? = null

    private val binding get() = _binding

    private lateinit var adapter: ProductAdapter

    private lateinit var products: List<Product>

    private var mContext: Context? = null

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

        if (mContext==null){
            return
        }

        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ProductResponse.Loading -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }

                is ProductResponse.Success -> {
                    products = response.data
                    adapter = ProductAdapter(products, mContext!!)
                    binding?.rvProducts?.adapter = adapter
                    binding?.progressBar?.visibility = View.GONE
                }

                is ProductResponse.Error -> {
                    //todo Show error message
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }

        viewModel.getProducts()

        binding?.btnAddProduct?.setOnClickListener {
            findNavController().navigate(R.id.action_listProductFragment_to_addProductFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}
