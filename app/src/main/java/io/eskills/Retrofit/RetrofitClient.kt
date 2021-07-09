package io.eskills.Retrofit


import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/"//Base Url for Emulator
    //private const val BASE_URL = "https://e-skills.io/"//Base Url for Phone

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    private var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization",
                "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmNjIzNTUxNTUxZGZlMDQwOWU4MWY4MiIsInR5cGUiOiJBIiwiaWF0IjoxNjEyNTE4OTAwfQ.iRR1GBm9A9dlS_UXnlg6bwjWsTW4I7gidZw8ptiO-IQ")
            .build()
        chain.proceed(newRequest)
    }.build()

    val instance: INodeJS by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(INodeJS::class.java)
    }

    val instanceConnected: INodeJS by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(INodeJS::class.java)
    }

    val stackOverFlow: INodeJS by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(INodeJS::class.java)
    }


}