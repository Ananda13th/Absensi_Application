package example.com.absensiapp.di;

import dagger.Module;
import dagger.Provides;
import example.com.absensiapp.model.mapper.BaseResponseMapper;
import example.com.absensiapp.model.mapper.HistoryMapper;
import example.com.absensiapp.model.mapper.OverrideMapper;
import example.com.absensiapp.model.mapper.UserMapper;
import example.com.data.entity.mapper.BaseResponseEntityMapper;
import example.com.data.entity.mapper.HistoryEntityMapper;
import example.com.data.entity.mapper.OverrideReqEntityMapper;
import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.BaseResponseRepositoryImpl;
import example.com.data.repository.HistoryRepositoryImpl;
import example.com.data.repository.OverrideRepositoryImpl;
import example.com.data.repository.UserRepositoryImpl;
import example.com.domain.repository.BaseResponseRepository;
import example.com.domain.repository.HistoryRepository;
import example.com.domain.repository.OverrideRepository;
import example.com.domain.repository.UserRepository;
import example.com.domain.usecase.override.AcceptOverrideUseCase;
import example.com.domain.usecase.override.RejectOverrideUseCase;
import example.com.domain.usecase.user.AddUserUseCase;
import example.com.domain.usecase.user.CheckInUseCase;
import example.com.domain.usecase.user.DeleteUserUseCase;
import example.com.domain.usecase.override.GetOverrideListUseCase;
import example.com.domain.usecase.user.GetUserListUseCase;
import example.com.domain.usecase.user.GetUserUseCase;
import example.com.domain.usecase.user.LoginUseCase;
import example.com.domain.usecase.override.OverrideUseCase;
import example.com.domain.usecase.history.SearchHistoryUseCase;
import example.com.domain.usecase.user.UpdateUserUseCase;
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
    OverrideReqEntityMapper provideOverrideEntityMapper() {return new OverrideReqEntityMapper();}

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
    BaseResponseRepository provideBaseResponseRepository(UserEntityMapper userEntityMapper, Scheduler scheduler, Service service) {
        return new BaseResponseRepositoryImpl(userEntityMapper, scheduler, service);
    }

    @Provides
    OverrideRepository provideOverrideRepository(OverrideReqEntityMapper overrideReqEntityMapper, Scheduler scheduler, Service service) {
        return new OverrideRepositoryImpl(overrideReqEntityMapper, scheduler, service);
    }

    //Provide UseCase
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

    @Provides
    SearchHistoryUseCase provideHistoryUseCase (HistoryRepository historyRepository) {
        return new SearchHistoryUseCase(historyRepository);
    }

    @Provides
    OverrideUseCase provideOverrideUseCase(OverrideRepository overrideRepository) {
        return new OverrideUseCase(overrideRepository);
    }

    @Provides
    GetOverrideListUseCase provideOverridelistUseCase(OverrideRepository overrideRepository) {
        return new GetOverrideListUseCase(overrideRepository);
    }

    @Provides
    AcceptOverrideUseCase provideAcceptOverrideUseCase(OverrideRepository overrideRepository) {
        return new AcceptOverrideUseCase(overrideRepository);
    }

    @Provides
    RejectOverrideUseCase provideRejectOverrideUseCase(OverrideRepository overrideRepository) {
        return new RejectOverrideUseCase(overrideRepository);
    }

}

