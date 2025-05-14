package com.example.securenote.di

import com.example.securenote.presentation.helper.biometric.BiometricHelper
import com.example.securenote.presentation.helper.biometric.BiometricHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class BiometricModule {

    @Binds
    abstract fun bindBiometricHelper(
        biometricHelperImpl: BiometricHelperImpl
    ): BiometricHelper
}
