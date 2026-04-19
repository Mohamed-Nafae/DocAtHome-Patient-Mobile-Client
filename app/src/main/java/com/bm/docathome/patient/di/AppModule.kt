package com.bm.docathome.patient.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.bm.docathome.patient.common.MyCookieJar
import com.bm.docathome.patient.common.MyInterceptor
import com.bm.docathome.patient.common.RetrofitInstance
import com.bm.docathome.patient.common.TokenIdManager
import com.bm.docathome.patient.data.remote.AppointmentApi
import com.bm.docathome.patient.data.remote.AuthApi
import com.bm.docathome.patient.data.remote.PatientAPI
import com.bm.docathome.patient.data.repository.AppointmentRepositoryImpl
import com.bm.docathome.patient.data.repository.AuthRepositoryImpl
import com.bm.docathome.patient.data.repository.PatientRepositoryImpl
import com.bm.docathome.patient.domain.repository.AppointmentRepository
import com.bm.docathome.patient.domain.repository.AuthRepository
import com.bm.docathome.patient.domain.repository.PatientRepository
import com.bm.docathome.patient.domain.use_case.download_patient_medicalFolder.DownloadMedicalFolderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePatientApi(
        retrofitInstance: RetrofitInstance
    ):PatientAPI{
        return retrofitInstance.patientApi
    }

    @Provides
    @Singleton
    fun provideAppointmentApi(
        retrofitInstance: RetrofitInstance
    ):AppointmentApi{
        return retrofitInstance.appointmentApi
    }

    @Provides
    @Singleton
    fun provideAppointmentRepository(
        appointmentApi: AppointmentApi
    ):AppointmentRepository{
        return AppointmentRepositoryImpl(appointmentApi)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        myInterceptor: MyInterceptor,
        myCookieJar: MyCookieJar
    ):RetrofitInstance{
        return RetrofitInstance(myInterceptor,myCookieJar)
    }

    @Provides
    @Singleton
    fun providePatientRepository(
        api: PatientAPI
    ):PatientRepository{
        return PatientRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs",MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenIdManager(prefs:SharedPreferences):TokenIdManager{
        return TokenIdManager(prefs)
    }

    @Provides
    @Singleton
    fun provideMyInterceptor(
        tokenIdManager: TokenIdManager
    ):MyInterceptor{
        return MyInterceptor(tokenIdManager)
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        retrofitInstance: RetrofitInstance
    ):AuthApi{
        return retrofitInstance.authApi
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi
    ):AuthRepository{
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMyCookieJar(
        prefs: SharedPreferences
    ): MyCookieJar {
        return MyCookieJar(prefs)
    }


}