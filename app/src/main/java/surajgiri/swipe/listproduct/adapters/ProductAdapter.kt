package surajgiri.swipe.listproduct.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import surajgiri.core.model.Product
import surajgiri.swipe.R
import surajgiri.swipe.databinding.ProductItemsBinding
import surajgiri.swipe.utils.loadUrl
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val diffUtilCallback = object
        : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }

    val productDiffer = AsyncListDiffer(this, diffUtilCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productDiffer.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productDiffer.currentList.size
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
                val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
                if (product.price % 1 == 0.0) {
                    format.minimumFractionDigits = 0
                    format.maximumFractionDigits = 0
                }
                val formattedPrice = format.format(product.price)
                productPrice.text = formattedPrice

                productType.text = product.product_type
            }
        }
    }
}