package com.example.nishant.fenrir.domain

data class UserDetails(val id: String, val jwtToken: String, val name: String, val isBITSian: Boolean, val profilePicURL: String, val qrCodeContent: String)