package com.example.domain.use_case.validation

import android.util.Patterns
import com.example.domain.model.UserProfile
import com.example.utility.State
import kotlinx.coroutines.flow.Flow

class ValidateEmail {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}