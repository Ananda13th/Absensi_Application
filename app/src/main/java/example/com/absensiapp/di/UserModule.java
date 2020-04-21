package example.com.absensiapp.di;

import dagger.Module;
import dagger.Provides;
import example.com.absensiapp.model.mapper.BaseResponseMapper;
import example.com.absensiapp.model.mapper.HistoryMapper;
import example.com.absensiapp.model.mapper.OverrideMapper;
import example.com.absensiapp.model.mapper.UserMapper;
import example.com.data.entity.mapper.BaseResponseEntityMapper;
import example.com.data.entity.mapper.HistoryEntityMapper;
import example.com.data.entity.mapper.OverrideEntityMapper;
import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.HistoryRepositoryImpl;
import example.com.data.repository.OverrideRepositoryImpl;
import example.com.data.repository.UserRepositoryImpl;
import example.com.domain.repository.HistoryRepository;
import example.com.domain.repository.OverrideRepository;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.override.ApproveOverrideUseCase;
import example.com.domain.usecase.override.DeletePendingOverrideUseCase;
import example.com.domain.usecase.override.GetOverrideHistoryUseCase;
import example.com.domain.usecase.override.RejectOverrideUseCase;
import example.com.domain.usecase.override.RequestOverrideUseCase;
import example.com.domain.usecase.user.AddUserUseCase;
import example.com.domain.usecase.user.CheckInUseCase;
import example.com.domain.usecase.user.DeleteUserUseCase;
import example.com.domain.usecase.override.GetOverrideListUseCase;
import example.com.domain.usecase.user.GetUserListUseCase;
import example.com.domain.usecase.user.GetUserUseCase;
import example.com.domain.usecase.user.LoginUseCase;
import example.com.domain.usecase.history.SearchHistoryUseCase;
import example.com.domain.usecase.user.UpdateUserUseCase;
//import example.com.domain.usecase.user.UploadImageUseCase;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public class UserModule {

    @Provides
    Scheduler provideScheduler() {return Schedulers.io();}

    @Provides
    Service provideService() {return ServiceGenerator.getService();}

    //Provide Mapper
    @Provides
    BaseResponseMapper provideBaseResponseMapper() {return new BaseResponseMapper();}

    @Provides
    UserMapper provideUserMapper() {return new UserMapper();}

    @Provides
    HistoryMapper provideHistoryMapper() {return new HistoryMapper();}

    @Provides
    OverrideMapper provideOverrideMapper() {return new OverrideMapper();}

    @Provides
    BaseResponseEntityMapper provideBaseResponseEntityMapper() {return new BaseResponseEntityMapper();}

    @Provides
    UserEntityMapper provideUserEntityMapper() {return new UserEntityMapper();}

    @Provides
    HistoryEntityMapper provideHistoryEntityMapper() {return new HistoryEntityMapper();}

    @Provides
    OverrideEntityMapper provideOverrideEntityMapper() {return new OverrideEntityMapper();}

    //Provide Repository
    @Provides
    UserRepository provideUserRepository(UserEntityMapper userEntityMapper, Scheduler scheduler, Service service) {
        return new UserRepositoryImpl(userEntityMapper, scheduler, service);
    }

    @Provides
    HistoryRepository provideHistoryRepository(HistoryEntityMapper historyEntityMapper, Scheduler scheduler, Service service) {
        return new HistoryRepositoryImpl(historyEntityMapper, scheduler,service);
    }

    @Provides
    OverrideRepository provideOverrideRepository(OverrideEntityMapper overrideEntityMapper, Scheduler scheduler, Service service) {
        return new OverrideRepositoryImpl(overrideEntityMapper, scheduler, service);
    }

    //Provide UseCase
    @Provides
    AddUserUseCase proviceAddUserUseCase(UserRepository userRepository) {
        return new AddUserUseCase(userRepository);
    }

    @Provides
    DeleteUserUseCase provideDeleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
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
    UpdateUserUseCase provideUpdateUserUseCase(UserRepository userRepository) {
        return new UpdateUserUseCase(userRepository);
    }

    @Provides
    CheckInUseCase provideCheckInUseCase (UserRepository userRepository) {
        return new CheckInUseCase(userRepository);
    }

    @Provides
    SearchHistoryUseCase provideHistoryUseCase (HistoryRepository historyRepository) {
        return new SearchHistoryUseCase(historyRepository);
    }

    @Provides
    RequestOverrideUseCase provideOverrideUseCase(OverrideRepository overrideRepository) {
        return new RequestOverrideUseCase(overrideRepository);
    }

    @Provides
    GetOverrideListUseCase provideOverridelistUseCase(OverrideRepository overrideRepository) {
        return new GetOverrideListUseCase(overrideRepository);
    }

    @Provides
    ApproveOverrideUseCase provideAcceptOverrideUseCase(OverrideRepository overrideRepository) {
        return new ApproveOverrideUseCase(overrideRepository);
    }

    @Provides
    RejectOverrideUseCase provideRejectOverrideUseCase(OverrideRepository overrideRepository) {
        return new RejectOverrideUseCase(overrideRepository);
    }

    @Provides
    GetOverrideHistoryUseCase provideGetOverrideHistoryUseCase(OverrideRepository overrideRepository) {
        return new GetOverrideHistoryUseCase(overrideRepository);
    }

    @Provides
    DeletePendingOverrideUseCase provideDeletePendingOverrideUseCase(OverrideRepository overrideRepository) {
        return new DeletePendingOverrideUseCase(overrideRepository);
    }

//    @Provides
//    UploadImageUseCase provideUploadImageUseCase(UserRepository userRepository) {
//        return new UploadImageUseCase(userRepository);
//    }

}

