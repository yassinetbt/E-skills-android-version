package io.eskills.Models

class UpdateAccountBody(
    private val firstName: String,
    private val lastName: String,
    private val email: String,
    private val oldPassword: String,
    private val newPassword: String,
)