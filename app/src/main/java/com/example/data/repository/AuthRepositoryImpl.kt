package com.example.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserProfile
import com.example.domain.repository.AuthRepository
import com.example.domain.use_case.user.GetUser
import com.example.utility.EXCEPTION
import com.example.utility.State
import com.example.utility.TAG
import com.example.utility.firebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val context: Context,
    val auth: FirebaseAuth,
    val db: FirebaseFirestore
) : AuthRepository {

    fun emailVerification(){
        val user = auth.currentUser!!

        val url = "http://www.example.com/verify?uid=" + user.uid
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl(url)
            .setIOSBundleId("com.example.ios")
            // The default for this is populated with the current android package name.
            .setAndroidPackageName("com.example.android", false, null)
            .setHandleCodeInApp(true)
            .build()

        user.sendEmailVerification(actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
    }


    override fun isUserAuthenticatedInFirebase(): Boolean {
        return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.flowOn(Dispatchers.IO)
        .catch { error -> Log.d(TAG, "An error has happened about authState: ${error.message}") }


    override fun signOut() {
        auth.signOut()
    }


    override suspend fun firebaseSignInWithEmail(
        email: String,
        password: String
    ): Flow<State<Boolean>> = flow {
        if (email.isEmpty() || password.isEmpty()) {
            val message = "The email or password field is empty"
            Log.d(EXCEPTION, message)
            emit(State.failed(message))
        } else {
            try {
                emit(State.loading())
                auth.signInWithEmailAndPassword(email, password).await()
                emit(State.success(true))
            } catch (e: Exception) {
                val customErrorMessage = e.firebaseException()
                //val errorCode = (e as FirebaseAuthException).errorCode
                emit(State.failed(customErrorMessage))
                Log.d(EXCEPTION, "createUserWithEmail:failure ${e.localizedMessage}")
            }
        }
    }

    override suspend fun firebaseSignUpWithEmail(
        email: String,
        password: String
    ): Flow<State<Boolean>> = flow {
        if (email.isEmpty() || password.isEmpty()) {
            val message = "The email or password field is empty"
            emit(State.failed(message))
        } else {
            try {
                emit(State.loading())
                val user = auth.createUserWithEmailAndPassword(email, password).await().user
                user?.let {
                    val profile = UserProfile(
                        uid = it.uid,
                        email = it.email ?: ""
                    )
                    db.collection("users").document(it.uid)
                        .set(profile, SetOptions.merge()).await()
                    emit(State.success(true))
                }
                //sendVerificationEmail(user)

                Log.d(TAG, "User signed Up by firebase auth $user")
            } catch (e: Exception) {
                val customErrorMessage = e.firebaseException()
                emit(State.failed(customErrorMessage))
                Log.d(EXCEPTION, "createUserWithEmail:failure ${e.localizedMessage}")
            }
        }
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ) {
        // if (currentPassword.isNotBlank() && newPassword.isNotBlank() && confirmPassword.isNotBlank()) {
        //if (newPassword == confirmPassword) {
        val user = auth.currentUser
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider
                .getCredential(user.email!!, currentPassword)

            // Prompt the user to re-provide their sign-in credentials
            try {
                user.reauthenticate(credential).await()
//                Toast.makeText(context, "Re-authentication success", Toast.LENGTH_SHORT)
//                    .show()
                user.updatePassword(newPassword).await()
                Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT)
                    .show()
                //delay(100)
                auth.signOut()
            } catch (e: CancellationException) {

            } catch (e: FirebaseAuthWeakPasswordException) {
                Toast.makeText(
                    context,
                    " ${e.reason}",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    " ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {
            Toast.makeText(
                context,
                "Current User is not found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}