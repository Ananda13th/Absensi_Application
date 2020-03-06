package example.com.domain;

import org.junit.Assert;
import org.junit.Test;
import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.UserRepositoryImpl;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.User;
import example.com.domain.model.UserList;
import example.com.domain.usecase.user.AddUserUseCase;
import example.com.domain.usecase.user.CheckInUseCase;
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
    public void T001_GetUserListImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userMapper,scheduler,service);
        Single<UserList> resp = userRepository.doGetListUser();

        TestObserver<UserList> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T002_GetUserImplementationTest() {
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
    public void T003_LoginImplementationTest() {
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
    public void T004_DeleteImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
        Single<BaseResponse> resp = userRepository.doDeleteUser("512");

        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T005_UpdateUserImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
        User user = new User();
        Single<BaseResponse> resp = userRepository.doUpdateUser("512", user);

        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T006_AddUserImplementationTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
        User user = new User();
        Single<BaseResponse> resp = userRepository.doAddUser(user);

        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void T007_GetUserListUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userMapper,scheduler,service);

        GetUserListUseCase userUseCase = new GetUserListUseCase(userRepository);

        Single<UserList> resp = userUseCase.execute();
        TestObserver<UserList> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());

    }

    @Test
    public void T008_GetUserUseCaseTest() {
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
    public void T009_LoginUserUseCaseTest() {
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
    public void T010_DeleteUserUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
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
    public void T011_DeleteUserUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
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
    public void T012_AddUserUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
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

    @Test
    public void T013_CheckStateUseCaseTest() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
        CheckInUseCase checkInUseCase = new CheckInUseCase(userRepository);
        CheckInReq check = new CheckInReq();
        Single<BaseResponse> resp =checkInUseCase.execute(check);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }




}