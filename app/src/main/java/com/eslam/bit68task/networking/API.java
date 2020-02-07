package com.eslam.bit68task.networking;

import com.eslam.bit68task.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("task/categories")
    Call<List<Category>> getCatefories();
}
