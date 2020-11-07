package com.example.root.smartmessenger.util;

import com.example.root.smartmessenger.Models.LoginReq;
import com.example.root.smartmessenger.Models.MessageModel;
import com.example.root.smartmessenger.Models.MessageReq;
import com.example.root.smartmessenger.Models.Registrationfeedback;
import com.example.root.smartmessenger.Models.ResetResponse;
import com.example.root.smartmessenger.Models.ServerRequest;
import com.example.root.smartmessenger.Models.Status;
import com.example.root.smartmessenger.Models.TeacherProfile;
import com.example.root.smartmessenger.Models.UnreadRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ROOT on 2/3/2018.
 */

public interface apiInterface {

    @POST("/smartsystem/subscribecode.php")
    Call<ResetResponse> requestSubscribe(@Body ServerRequest request);

    @POST("/smartsystem/checksubscription.php")
    Call<ResetResponse> checkSubscribe(@Body ServerRequest request);

    @GET("/smartsystem/getsubscription.php")
    Call<ResetResponse> getsubcode();

    @POST("/smartsystem/getTeacherName.php")
    Call <ArrayList<TeacherProfile>> getTeacherNameList(@Body UnreadRequest request);

    @POST("/smartsystem/getMessages.php")
    Call <ArrayList<MessageModel>> getMessageList(@Body MessageReq request);

    @POST("/smartsystem/studentregcheck.php")
    Call<Status> checkValidation(@Body MessageReq request);

    @POST("/smartsystem/registerstudents.php")
    Call<Registrationfeedback> register(@Body MessageReq request);

    @POST("/smartsystem/studentlogincheck.php")
    Call<Registrationfeedback> login(@Body LoginReq request);
}
