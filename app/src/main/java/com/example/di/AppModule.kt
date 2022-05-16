package com.example.di


//import com.example.data.repository.FirebaseRepository
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.use_case.*
import com.example.domain.use_case.auth.*
import com.example.domain.use_case.user.*
import com.example.domain.use_case.validation.*
import com.example.utility.UserPreferenceData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun FirebaseFirestore() = Firebase.firestore

    @Singleton
    @Provides
    fun FirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun userService(firestore: FirebaseFirestore, auth: FirebaseAuth): UserRepository {
        return UserRepositoryImpl(firestore, auth)
    }

    @Singleton
    @Provides
    fun authService(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): AuthRepository {
        return AuthRepositoryImpl(context, auth, firestore)
    }

    @Singleton
    @Provides
    fun getUserPreference(@ApplicationContext context: Context): UserPreferenceData {
        return UserPreferenceData(context)
    }


    @Provides
    fun provideUserUseCases(repositoryImpl: UserRepository) = UserUseCases(
        getUsers = GetUsers(repositoryImpl),
        getUser = GetUser(repositoryImpl),
    )

    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCases(
        currentUser = CurrentUser(authRepository),
        signOut = SignOutUser(authRepository),
        signUpWithEmailUser = SignUpWithEmail(authRepository),
        signInWithEmail = SignInWithEmail(authRepository),
        authState = AuthState(authRepository),
        changePassword = ChangePassword(authRepository)
    )

    @Provides
    fun provideValidationUseCases() = ValidateUseCases(
        validateEmail = ValidateEmail(),
        validatePassword = ValidatePassword(),
        validateRepeatedPassword = ValidateRepeatedPassword(),
        validateTerms = ValidateTerms(),
        validatePasswordAndNewPassword = ValidatePasswordAndNewPassword(ValidatePassword())
    )


}