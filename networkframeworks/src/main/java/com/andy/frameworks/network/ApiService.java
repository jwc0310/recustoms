package com.andy.frameworks.network;

import com.andy.frameworks.bean.GitRepo;
import com.andy.frameworks.bean.NetResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * 定义网络接口
 */
public interface ApiService {

    @GET("users/{user}/repos")
    Call<List<GitRepo>> listRepos(@Path("user") String user);

    /**
     * 通过 List<MultipartBody.part> 传入多个part实现多文件上传
     */
    @Multipart
    @POST("users/image")
    Call<NetResponse<Object>> uploadFilesWithPart(@Part() List<MultipartBody.Part> parts);

    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("users/image")
    Call<NetResponse<Object>> uploadFileWithRequestBody(@Body MultipartBody multipartBody);

}
