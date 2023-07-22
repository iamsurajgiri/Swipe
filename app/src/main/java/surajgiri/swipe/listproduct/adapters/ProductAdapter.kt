package surajgiri.swipe.listproduct.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import surajgiri.core.model.Product
import surajgiri.swipe.R
import surajgiri.swipe.databinding.ProductItemsBinding
import surajgiri.swipe.utils.loadUrl

class ProductAdapter(
    private val products: List<Product>,
    private val context: Context
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ProductViewHolder(private val binding: ProductItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                productName.text = product.product_name
                if (product.image.isNullOrEmpty()){
                    productImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    productImage.setImageResource(R.drawable.no_photo)
                }else{
                    productImage.scaleType = ImageView.ScaleType.FIT_XY
                    productImage.loadUrl(product.image!!)
                }
                val formattedTax = if (product.tax % 1 == 0.0) {
                    product.tax.toInt().toString()
                } else {
                    "%.1f".format(product.tax)
                }
                productTax.text = context.getString(R.string.tax, formattedTax)
                productPrice.text = context.getString(R.string.price, product.price)
                productType.text = product.product_type
            }
        }
    }
}