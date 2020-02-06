package example.com.absensiapp.di;

import dagger.Module;
import dagger.Provides;
import example.com.absensiapp.model.mapper.BaseResponseMapper;
import example.com.absensiapp.model.mapper.UserMapper;
import example.com.data.entity.mapper.BaseResponseEntityMapper;
import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.BaseResponseRepositoryImpl;
import example.com.data.repository.UserRepositoryImpl;
import example.com.domain.repository.BaseResponseRepository;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.user.AddUserUseCase;
import example.com.domain.usecase.user.CheckInUseCase;
import example.com.domain.usecase.user.DeleteUserUseCase;
import example.com.domain.usecase.user.GetUserListUseCase;
import example.com.domain.usecase.user.GetUserUseCase;
import example.com.domain.usecase.user.LoginUseCase;
import example.com.domain.usecase.user.UpdateUserUseCase;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public class UserModule {

    @Provides
    BaseResponseMapper provideBaseResponseMapper() {return new BaseResponseMapper();}

    @Provides
    UserMapper provideUserMapper() {return new UserMapper();}

    @Provides
    BaseResponseEntityMapper provideBaseResponseEntityMapper() {return new BaseResponseEntityMapper();}

    @Provides
    UserEntityMapper provideUserEntityMapper() {return new UserEntityMapper();}

    @Provides
    Scheduler provideScheduler() {return Schedulers.io();}

    @Provides
    Service provideService() {return ServiceGenerator.getService();}

    @Provides
    UserRepository provideUserRepository(UserEntityMapper userEntityMapper, Scheduler scheduler, Service service) {
        return new UserRepositoryImpl(userEntityMapper, scheduler, service);
    }

    @Provides
    BaseResponseRepository provideBaseResponseRepository(UserEntityMapper userEntityMapper, Scheduler scheduler, Service service) {
        return new BaseResponseRepositoryImpl(userEntityMapper, scheduler, service);
    }

    @Provides
    AddUserUseCase proviceAddUserUseCase(BaseResponseRepository baseResponseRepository) {
        return new AddUserUseCase(baseResponseRepository);
    }

    @Provides
    DeleteUserUseCase provideDeleteUserUseCase(BaseResponseRepository baseResponseRepository) {
        return new DeleteUserUseCase(baseResponseRepository);
    }

    @Provides
    GetUserListUseCase provideGetUserListUseCase(UserRepository userRepository) {
        return new GetUserListUseCase(userRepository);
    }

    @Provides
    GetUserUseCase provideGetUserUseCase(UserRepository userRepository) {
        return new GetUserUseCase(userRepository);
    }

    @Provides
    LoginUseCase provideLoginUseCase(UserRepository userRepository) {
        return new LoginUseCase(userRepository);
    }

    @Provides
    UpdateUserUseCase provideUpdateUserUseCase(BaseResponseRepository baseResponseRepository) {
        return new UpdateUserUseCase(baseResponseRepository);
    }

    @Provides
    CheckInUseCase provideCheckInUseCase (BaseResponseRepository baseResponseRepository) {
        return new CheckInUseCase(baseResponseRepository);
    }

}

