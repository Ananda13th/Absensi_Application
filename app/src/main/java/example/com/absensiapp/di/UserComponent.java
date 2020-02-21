package example.com.absensiapp.di;

import dagger.Component;
import example.com.absensiapp.viewmodel.HistoryViewModel;
import example.com.absensiapp.viewmodel.OverrideViewModel;
import example.com.absensiapp.viewmodel.UserViewModel;

@Component(modules = UserModule.class)
public interface UserComponent {
    void inject(UserViewModel userViewModel);
    void inject(HistoryViewModel historyViewModel);
    void inject(OverrideViewModel overrideViewModel);
}
