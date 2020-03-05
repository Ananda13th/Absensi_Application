package example.com.absensiapp.model;

import lombok.Data;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Data
public class UploadImageReqModel {
    private RequestBody userid;
    private MultipartBody.Part[] multipart;
}
