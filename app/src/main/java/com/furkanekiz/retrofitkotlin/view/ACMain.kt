package com.furkanekiz.retrofitkotlin.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkanekiz.retrofitkotlin.R
import com.furkanekiz.retrofitkotlin.adapter.AdapterCrypto
import com.furkanekiz.retrofitkotlin.model.CryptoModel
import com.furkanekiz.retrofitkotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.ac_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ACMain : AppCompatActivity(), AdapterCrypto.Listener {

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var adapterCrypto: AdapterCrypto? = null

    //Disposable
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        compositeDisposable = CompositeDisposable()

        rvCrypto.layoutManager = LinearLayoutManager(this)

        loadData()

    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        //val service = retrofit.create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError))

        /*
        val call = service.getData()

        call.enqueue(object : Callback<List<CryptoModel>>{
            override fun onResponse(call: Call<List<CryptoModel>>, response: Response<List<CryptoModel>>) {
                if (response.isSuccessful){
                    response.body()?.let  {it ->
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let {
                            adapterCrypto = AdapterCrypto(it,this@ACMain)
                            rvCrypto.adapter=adapterCrypto
                        }

                    }
                }
            }


            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })

         */
    }

    private fun handleResponse(cryptoList: List<CryptoModel>){

                cryptoModels = ArrayList(cryptoList)

                cryptoModels?.let {
                    adapterCrypto = AdapterCrypto(it,this@ACMain)
                    rvCrypto.adapter=adapterCrypto
                }
    }

    private fun handleError(t: Throwable) {
        t.printStackTrace()
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again", Toast.LENGTH_LONG).show()
    }

    override fun onItemClicked(cryptoModel: CryptoModel) {
        Toast.makeText(this, cryptoModel.currency,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}