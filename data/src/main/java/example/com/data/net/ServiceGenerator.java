package example.com.data.net;

import example.com.domain.BuildConfig;

public class ServiceGenerator {
    private static Service service;

    public  static Service getService() {
        if(service == null) {
            service = RetrofitHelper.getRetrofit(BuildConfig.BASE_URL).create(Service.class);
        }
        return service;
    }
}
