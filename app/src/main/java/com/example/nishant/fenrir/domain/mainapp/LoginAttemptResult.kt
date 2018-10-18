package com.example.nishant.fenrir.domain.mainapp

sealed class LoginAttemptResult {

    object Success : LoginAttemptResult()

    sealed class Failure : LoginAttemptResult() {

        object NoInternet : Failure()

        object NoSuchUser : Failure()
    }
}