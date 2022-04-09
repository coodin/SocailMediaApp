package com.example.utility


import android.util.Log
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException


fun Exception.firebaseException(): String {
    if (this is FirebaseAuthException) {
        val errorCode = this.errorCode
        authErrors.forEach { entry ->
            if (entry.key == errorCode) {
                return entry.value
            }
        }
    }
//    else if(this is FirebaseFirestoreException) {
//        val errorCode = this.code.name
//        firestoreErrors.forEach { entry ->
//            if (entry.key == errorCode) {
//                return entry.value
//            }
//        }
//    }
    Log.d(EXCEPTION, "Un handled Exception ${this.message}")
    return localizedMessage?.toString() ?: "Unhandled Error"
}

val firestoreErrors = mapOf("ALREADY_EXISTS" to "The document is already exists.")

val authErrors = mapOf(
    "ERROR_INVALID_CUSTOM_TOKEN" to "The custom token format is incorrect. Please check the documentation.",
    "ERROR_CUSTOM_TOKEN_MISMATCH" to "The custom token corresponds to a different audience.",
    "ERROR_INVALID_CREDENTIAL" to "The supplied auth credential is malformed or has expired.",
    "ERROR_INVALID_EMAIL" to "The email address is badly formatted.",
    "ERROR_WRONG_PASSWORD" to "The password is invalid or the user does not have a password.",
    "ERROR_USER_MISMATCH" to "The supplied credentials do not correspond to the previously signed in user.",
    "ERROR_REQUIRES_RECENT_LOGIN" to "This operation is sensitive and requires recent authentication. Log in again before retrying this request.",
    "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" to "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.",
    "ERROR_EMAIL_ALREADY_IN_USE" to "The email address is already in use by another account.",
    "ERROR_CREDENTIAL_ALREADY_IN_USE" to "This credential is already associated with a different user account.",
    "ERROR_USER_DISABLED" to "The user account has been disabled by an administrator.",
    "ERROR_USER_TOKEN_EXPIRED" to "The user\\'s credential is no longer valid. The user must sign in again",
    "ERROR_USER_NOT_FOUND" to "There is no user record corresponding to this identifier. The user may have been deleted.",
    "ERROR_INVALID_USER_TOKEN" to "The user\\'s credential is no longer valid. The user must sign in again.",
    "ERROR_OPERATION_NOT_ALLOWED" to "This operation is not allowed. You must enable this service in the console.",
    "ERROR_WEAK_PASSWORD" to "The given password is invalid."
)