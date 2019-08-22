package com.kyd3snik.surfmemes.api;

import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/auth/login")
    Call<UserResponse> login(@Body AuthRequest auth);

}