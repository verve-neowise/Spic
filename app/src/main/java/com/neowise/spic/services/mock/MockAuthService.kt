package com.neowise.spic.services.mock

import com.neowise.spic.model.Account
import com.neowise.spic.model.Role
import com.neowise.spic.model.SignUp
import com.neowise.spic.model.Token
import com.neowise.spic.services.AuthService

class MockAuthService : AuthService {

    override fun login(account: Account): Token {
        return Token("token", Role.LEADER)
    }

    override fun signUp(signUp: SignUp): Token {
        return Token("token", Role.NONE)
    }
}