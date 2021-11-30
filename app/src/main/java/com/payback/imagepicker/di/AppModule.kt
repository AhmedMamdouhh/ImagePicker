package com.payback.imagepicker.di

import android.content.Context
import androidx.room.Room
import com.payback.imagepicker.BuildConfig
import com.payback.imagepicker.data.db.ImagePickerDataBase
import com.payback.imagepicker.data.remote.Api
import com.payback.imagepicker.presentation.utils.manager.ResponseManager
import com.payback.imagepicker.presentation.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Retrofit configuration :
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor)
            : OkHttpClient = OkHttpClient
        .Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    //Room configuration :
    @Singleton
    @Provides
    fun provideImagePickerDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ImagePickerDataBase::class.java,
        Constants.DATA_BASE_NAME
    ).allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideImagePickerDao(imagePickerDataBase: ImagePickerDataBase) = imagePickerDataBase.getImagePickerDao()


    //App
    @Singleton
    @Provides
    fun provideResponseManager() = ResponseManager()


}