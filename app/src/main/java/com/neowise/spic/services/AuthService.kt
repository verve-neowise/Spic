package com.neowise.spic.services

import com.neowise.spic.model.Account
import com.neowise.spic.model.SignUp
import com.neowise.spic.model.Token

interface AuthService {
    // get:/login[account]
    fun login(account: Account): Token

    // put:/signup[signup]
    fun signUp(signUp: SignUp): Token
}