package example.com.domain.model;

import lombok.Data;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Data
public class UploadImageReq {
    private RequestBody userid;
    private MultipartBody.Part[] multipart;
}
