package surajgiri.swipe.listproduct.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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
                    binding?.btnAddProduct?.visibility = View.GONE
                }

                is ProductResponse.Success -> {
                    products = response.data
                    adapter = ProductAdapter(mContext!!)
                    adapter.productDiffer.submitList(products)
                    binding?.rvProducts?.adapter = adapter
                    binding?.progressBar?.visibility = View.GONE
                    binding?.btnAddProduct?.visibility = View.VISIBLE
                }

                is ProductResponse.Error -> {
                    //todo Show error message
                    binding?.progressBar?.visibility = View.GONE

                }
            }
        }

        viewModel.getProducts()


        binding?.etSearch?.addTextChangedListener {
            val searchString = it.toString()
            filterProductList(searchString)
        }

        binding?.btnAddProduct?.setOnClickListener {
            findNavController().navigate(R.id.action_listProductFragment_to_addProductFragment)
        }

    }

    private fun filterProductList(searchString: String) {
        val filteredList = products.filter { product ->
            product.product_name.lowercase().contains(searchString.lowercase())
        }
        adapter.productDiffer.submitList(filteredList)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    //update the list when the fragment is resumed will be a good practice, so that the list is updated when the user comes back to this fragment
    //since the api is public and provides the latest fixed list with no pagination possibilities, we can just call the api again
    //will fix it if it was a private api to avoid unnecessary api calls for cost saving and data usage
    override fun onResume() {
        super.onResume()
        viewModel.getProducts()
    }



}
