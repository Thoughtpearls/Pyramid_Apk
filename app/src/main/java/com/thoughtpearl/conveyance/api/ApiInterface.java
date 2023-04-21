package com.thoughtpearl.conveyance.api;

import com.thoughtpearl.conveyance.RideDetailsResponse;
import com.thoughtpearl.conveyance.RideReason;
import com.thoughtpearl.conveyance.api.response.Attendance;
import com.thoughtpearl.conveyance.api.response.LocationRequest;
import com.thoughtpearl.conveyance.api.response.LoginResponse;
import com.thoughtpearl.conveyance.api.response.Ride;
import com.thoughtpearl.conveyance.api.response.EmployeeProfile;
import com.thoughtpearl.conveyance.api.response.LoginRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;*/
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers("Accept: application/json")
    @POST("/api/users/login")     // API's endpoints
    public Call<LoginResponse> login(@Header("userName") String username,
                                     @Header("password") String password,
                                     @Header("deviceId") String deviceId);


    @Multipart
    @POST("/api/employees/uploadProfile")     // API's endpoints
    public Call<Void> uploadProfile(@Header("userName") String username,
                              @Header("deviceId") String deviceId,
                              @PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("/api/attendances/markAttendance")     // API's endpoints
    public Call<String> markAttendance(@Header("userName") String username,
                                       @Header("deviceId") String deviceId,
                                       @PartMap Map<String, RequestBody> map);

    @GET("/api/attendances/calculatedleaves")
    public Call<LeavesDetails> getLeaveDetails(@Header("userName") String username,
                                         @Header("deviceId") String deviceId);



    @GET("/api/employees/employeeProfile")     // API's endpoints
    public Call<EmployeeProfile> getEmployeeProfile(@Header("userName") String userName,
                                                    @Header("deviceId") String deviceId);


    @Headers({"Accept: application/json"})
    @POST("/api/rides/create")     // API's endpoints
    public Call<String> createRide(@Header("userName") String userName,
                           @Header("deviceId") String deviceId,
                           @Body Ride ride);

    @Headers({"Accept: application/json"})
    @POST("/api/rides/create")     // API's endpoints
    @FormUrlEncoded
    public Call<String> createRide(@Header("userName") String userName,
                                   @Header("deviceId") String deviceId,
                                   @FieldMap Map<String, String> params);

    @Headers({"Accept: application/json"})
    @GET("/api/rides/rideDetails")     // API's endpoints
    public Call<RideDetailsResponse> getRideDetails(@Header("userName") String userName,
                                                    @Header("deviceId") String deviceId,
                                                    @Header("rideId") String rideId);


    @Headers({"Accept: application/json"})
    @GET("/api/ridePurpose/getAll")
    public Call<List<RideReason>> getRidePurpose(@Header("userName") String userName,
                               @Header("deviceId") String deviceId);

    @Headers({"Accept: application/json"})
    @POST("/api/rideLocations/create")     // API's endpoints
    public Call<List<String>> createLocation(@Header("userName") String userName,
                               @Header("deviceId") String deviceId,
                               @Body ArrayList<LocationRequest> locationRequest);

    @Multipart
    @PUT("/api/rides/update/{rideId}")     // API's endpoints
    Call<Void> updateRide( @Header("userName") String userName,
                           @Header("deviceId") String deviceId,
                           @Path(value = "rideId") String rideId,
                           @PartMap Map<String, RequestBody> map);

    @POST("/api/rides/rideStatistics")
    Call<SearchRideResponse> searchRideStatistics(@Header("userName") String userName,
                                    @Header("deviceId") String deviceId,
                                    @Body SearchRideFilter rideFilter);
}
