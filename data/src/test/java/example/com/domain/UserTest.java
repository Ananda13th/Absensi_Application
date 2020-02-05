package example.com.domain;

import android.os.UserManager;

import org.junit.Assert;
import org.junit.Test;

import example.com.data.entity.BaseResponseEntity;
import example.com.data.entity.UserEntity;
import example.com.data.entity.UserRespEntity;
import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.BaseResponseRepositoryImpl;
import example.com.data.repository.UserRepositoryImpl;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.User;
import example.com.domain.model.UserResp;
import example.com.domain.usecase.user.AddUserUseCase;
import example.com.domain.usecase.user.DeleteUserUseCase;
import example.com.domain.usecase.user.GetUserListUseCase;
import example.com.domain.usecase.user.GetUserUseCase;
import example.com.domain.usecase.user.LoginUseCase;
import example.com.domain.usecase.user.UpdateUserUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class UserTest {
    @Test
    public void T001_GetUserListTest() {
        Service service = ServiceGenerator.getService();
        Single<UserRespEntity> resp = service.getUserList();

        TestObserver<UserRespEntity> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T002_GetUserTest() {
        Service service = ServiceGenerator.getService();
        Single<UserEntity> resp = service.getUser("512");

        TestObserver<UserEntity> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T003_LoginTest() {
        Service service = ServiceGenerator.getService();
        UserEntity user = new UserEntity();
        user.setUserId("512");
        user.setPassword("12345678");
        Single<UserEntity> resp = service.loginUser(user);

        TestObserver<UserEntity> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T004_DeleteUserTest() {
        Service service = ServiceGenerator.getService();
        Single<BaseResponseEntity> resp = service.deleteUser("512");

        TestObserver<BaseResponseEntity> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T005_UpdateUserTest() {
        Service service = ServiceGenerator.getService();
        UserEntity user = new UserEntity();
        Single<BaseResponseEntity> resp = service.updateUser("512", user);

        TestObserver<BaseResponseEntity> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T006_AddUserTest() {
        Service service = ServiceGenerator.getService();
        UserEntity user = new UserEntity();
        Single<BaseResponseEntity> resp = service.addUser(user);

        TestObserver<BaseResponseEntity> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T007_GetUserListImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userMapper,scheduler,service);
        Single<UserResp> resp = userRepository.doGetListUser();

        TestObserver<UserResp> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T008_GetUserImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userMapper,scheduler,service);
        Single<User> resp = userRepository.doGetUser("512");

        TestObserver<User> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T009_LoginImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userMapper,scheduler,service);
        User user = new User();
        user.setUserId("512");
        user.setPassword("12345678");
        Single<User> resp = userRepository.doLogin(user);

        TestObserver<User> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T010_DeleteImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        BaseResponseRepositoryImpl baseResponseRepository = new BaseResponseRepositoryImpl(userMapper,scheduler,service);
        Single<BaseResponse> resp = baseResponseRepository.doDeleteUser("512");

        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T011_UpdateUserImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        BaseResponseRepositoryImpl baseResponseRepository = new BaseResponseRepositoryImpl(userMapper,scheduler,service);
        User user = new User();
        Single<BaseResponse> resp = baseResponseRepository.doUpdateUser("512", user);

        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T012_AddUserImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        BaseResponseRepositoryImpl baseResponseRepository = new BaseResponseRepositoryImpl(userMapper,scheduler,service);
        User user = new User();
        Single<BaseResponse> resp = baseResponseRepository.doAddUser(user);

        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T013_GetUserListUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userMapper,scheduler,service);

        GetUserListUseCase userUseCase = new GetUserListUseCase(userRepository);

        Single<UserResp> resp = userUseCase.execute();
        TestObserver<UserResp> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());

    }

    @Test
    public void T014_GetUserUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userMapper,scheduler,service);

        GetUserUseCase getUserUseCase = new GetUserUseCase(userRepository);

        Single<User> resp = getUserUseCase.execute("512");
        TestObserver<User> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("512", testObserver.values().get(0).getUserId());
    }

    @Test
    public void T015_LoginUserUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userMapper,scheduler,service);
        User user = new User();
        user.setUserId("512");
        user.setPassword("12345678");
        LoginUseCase loginUseCase = new LoginUseCase(userRepository);

        Single<User> resp = loginUseCase.execute(user);
        TestObserver<User> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("512", testObserver.values().get(0).getUserId());
    }

    @Test
    public void T016_DeleteUserUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        BaseResponseRepositoryImpl userRepository = new BaseResponseRepositoryImpl(userMapper,scheduler,service);
        DeleteUserUseCase deleteUseCase = new DeleteUserUseCase(userRepository);

        Single<BaseResponse> resp =deleteUseCase.execute("512");
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }

    @Test
    public void T017_DeleteUserUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        BaseResponseRepositoryImpl userRepository = new BaseResponseRepositoryImpl(userMapper,scheduler,service);
        UpdateUserUseCase updateUseCase = new UpdateUserUseCase(userRepository);
        User user = new User();
        user.setUserId("512");
        Single<BaseResponse> resp =updateUseCase.execute(user);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }

    @Test
    public void T018_AddUserUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        BaseResponseRepositoryImpl userRepository = new BaseResponseRepositoryImpl(userMapper,scheduler,service);
        AddUserUseCase addUseCase = new AddUserUseCase(userRepository);
        User user = new User();
        Single<BaseResponse> resp =addUseCase.execute(user);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }
}