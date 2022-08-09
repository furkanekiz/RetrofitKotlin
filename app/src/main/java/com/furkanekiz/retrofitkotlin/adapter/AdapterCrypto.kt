package com.furkanekiz.retrofitkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanekiz.retrofitkotlin.R
import com.furkanekiz.retrofitkotlin.model.CryptoModel
import kotlinx.android.synthetic.main.row_crypto.view.*
import java.text.DecimalFormat

class AdapterCrypto(
    private val cryptoList: ArrayList<CryptoModel>,
    private val listener: Listener
) : RecyclerView.Adapter<AdapterCrypto.CryptoHolder>() {

    private val colors: Array<String> = arrayOf(
        "#C0392B",
        "#9B59B6",
        "#2980B9",
        "#1ABC9C",
        "#27AE60",
        "#F1C40F",
        "#E67E22",
        "#34495E"
    )

    interface Listener {
        fun onItemClicked(cryptoModel: CryptoModel)
    }

    class CryptoHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            cryptoModel: CryptoModel,
            colors: Array<String>,
            position: Int,
            listener: Listener
        ) {

            val decimalFormat = DecimalFormat("#,###.####")
            val cryptoPrice = cryptoModel.price.toDouble()

            with(itemView) {
                setOnClickListener {
                    listener.onItemClicked(cryptoModel)
                }
                setBackgroundColor(Color.parseColor(colors[position % colors.size]))
                tvCryptoName.text = cryptoModel.currency
                tvCryptoPrice.text = "${decimalFormat.format(cryptoPrice)} $"
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_crypto, parent, false)
        return CryptoHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.bind(cryptoList[position], colors, position, listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
}