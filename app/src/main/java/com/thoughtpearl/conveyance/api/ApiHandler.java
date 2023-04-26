package com.thoughtpearl.conveyance.api;

/*import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;*/

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    public static ApiInterface getClient() {
        // change your base URL

     /*   OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new AddHeaderInterceptor())*/;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://43.204.194.87:8091/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        ApiInterface retrofitAPI = retrofit.create(ApiInterface.class);
        
        /*RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://103.195.80.253:8091/api") //Set the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        ApiInterface api = adapter.create(ApiInterface.class);*/
        return retrofitAPI; // return the APIInterface object
    }

    public static class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("userName", "aditi");
            builder.addHeader("deviceId", "89ABCDEF-01234567-89ABCDEF");

            return chain.proceed(builder.build());
        }
    }
}
