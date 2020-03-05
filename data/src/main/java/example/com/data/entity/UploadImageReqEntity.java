package example.com.data.entity;

import lombok.Data;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Data
public class UploadImageReqEntity {

    private RequestBody userid;
    private MultipartBody.Part[] multipart;
}
