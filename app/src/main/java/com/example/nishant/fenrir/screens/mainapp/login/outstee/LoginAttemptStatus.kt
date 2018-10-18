package com.example.nishant.fenrir.screens.mainapp.login.outstee

sealed class LoginAttemptStatus {

    object Idle : LoginAttemptStatus()

    object InProgress : LoginAttemptStatus()

    object Success : LoginAttemptStatus()

    class Failure(val message: String) : LoginAttemptStatus()
}