package com.example.di


//import com.example.data.repository.FirebaseRepository
import com.example.data.repository.FirebaseRepository
import com.example.domain.use_case.CurrenUser
import com.example.domain.use_case.GetUser
import com.example.domain.use_case.GetUsers
import com.example.domain.use_case.UseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun firebaseService(firebase: Firebase): FirebaseRepository {
        return FirebaseRepository(firebase)
    }


    @Singleton
    @Provides
    fun firebase(): Firebase = Firebase

    @Provides
    fun provideUseCases(repository: FirebaseRepository) = UseCases(
        getUsers = GetUsers(repository),
        getUser = GetUser(repository),
        currentUser = CurrenUser(repository)
    )
}