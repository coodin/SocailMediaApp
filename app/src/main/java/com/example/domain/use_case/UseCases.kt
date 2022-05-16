package com.example.domain.use_case.user

import com.example.domain.use_case.CurrentUser
import com.example.domain.use_case.auth.*
import com.example.domain.use_case.validation.*


data class UserUseCases(
  val getUsers: GetUsers,
  val getUser: GetUser,
)

data class AuthUseCases(
  val currentUser: CurrentUser,
  val signOut: SignOutUser,
  val signUpWithEmailUser: SignUpWithEmail,
  val signInWithEmail: SignInWithEmail,
  val authState: AuthState,
  val changePassword: ChangePassword
)

data class ValidateUseCases(
  val validateEmail: ValidateEmail,
  val validatePassword: ValidatePassword,
  val validateRepeatedPassword: ValidateRepeatedPassword,
  val validateTerms: ValidateTerms,
  val validatePasswordAndNewPassword: ValidatePasswordAndNewPassword
)
