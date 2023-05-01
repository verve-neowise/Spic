package com.neowise.spic.services.impl

import com.neowise.spic.api.*
import com.neowise.spic.model.Account
import com.neowise.spic.model.SignUp
import com.neowise.spic.model.Token
import com.neowise.spic.services.*
import com.neowise.spic.util.parse

class AuthServiceImpl(private val client: HttpClient) : AuthService {

    override fun login(account: Account): Token {
        val url = "${API.login}?username=${account.username}&password=${account.password}"
        val response = client.get(url)
        val body = response.body()!!.string()

        when(response.code()) {
            ResponseCode.NotFound -> throw ApiException("Wrong username or password.")
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
        }
        return parse(body, Token::class.java)
    }

    override fun signUp(signUp: SignUp): Token {

        val response = client.put(API.signup, signUp)
        val body = response.body()!!.string()
        val code = response.code()
        println(response.code())
        println(body)

        when(response.code()) {
            ResponseCode.Success, ResponseCode.Accepted -> return parse(body, Token::class.java)
            ResponseCode.AlreadyExists -> throw ApiException("username already used.")
            ResponseCode.ServerError -> throw ApiException("Server error. Please try again later.")
            else -> throw ApiException("other error $code")
        }
    }
}