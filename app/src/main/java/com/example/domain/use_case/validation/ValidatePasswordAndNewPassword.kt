package com.example.domain.use_case.validation

class ValidatePasswordAndNewPassword(val validatePassword: ValidatePassword) {
    operator fun invoke(currentPassword: String, newPassword: String): ValidationResult {

        if (currentPassword == newPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The new password and current password must be different"
            )
        }
        return validatePassword(newPassword)
    }
}