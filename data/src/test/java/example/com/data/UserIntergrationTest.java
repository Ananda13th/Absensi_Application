package example.com.data;

import org.junit.Assert;
import org.junit.Test;
import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.net.ServiceGenerator;
import example.com.data.repository.UserRepositoryImpl;
import example.com.domain.model.BaseResponse;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.ResetPasswordReq;
import example.com.domain.model.ResetPasswordRespList;
import example.com.domain.model.User;
import example.com.domain.model.UserList;
import example.com.domain.usecase.user.AddUserUseCase;
import example.com.domain.usecase.user.CheckInUseCase;
import example.com.domain.usecase.user.DeletePasswordRequestUseCase;
import example.com.domain.usecase.user.DeleteUserUseCase;
import example.com.domain.usecase.user.GetResetPasswordListUseCase;
import example.com.domain.usecase.user.GetUserListUseCase;
import example.com.domain.usecase.user.GetUserUseCase;
import example.com.domain.usecase.user.LoginUseCase;
import example.com.domain.usecase.user.RequestResetPasswordUseCase;
import example.com.domain.usecase.user.UpdatePasswordUseCase;
import example.com.domain.usecase.user.UpdateUserUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class UserIntergrationTest {

    @Test
    public void t001_getUserListUseCaseTest() {
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
    public void t002_getUserUseCaseTest() {
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
    public void t003_loginUserUseCaseTest() {
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
    public void t004_deleteUserUseCaseTest() {
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
    public void t005_deleteUserUseCaseTest() {
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
    public void t006_addUserUseCaseTest() {
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
    public void t007_checkStateUseCaseTest() {
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

    @Test
    public void t008_requestPassword() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
        RequestResetPasswordUseCase requestResetPasswordUseCase = new RequestResetPasswordUseCase(userRepository);
        ResetPasswordReq resetPasswordReq = new ResetPasswordReq();
        Single<BaseResponse> resp = requestResetPasswordUseCase.execute(resetPasswordReq);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());

    }

    @Test
    public void t009_getResetPasswordList() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl(new UserEntityMapper(),Schedulers.io(), ServiceGenerator.getService());
        GetResetPasswordListUseCase resetPasswordListUseCase = new GetResetPasswordListUseCase(userRepository);
        Single<ResetPasswordRespList> resp = resetPasswordListUseCase.execute();
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertNotNull(resp);
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }

    @Test
    public void t010_updatePassword() {
        UserEntityMapper userMapper = new UserEntityMapper();
        Scheduler scheduler = Schedulers.io();
        Service service = ServiceGenerator.getService();
        UserRepositoryImpl userRepository = new  UserRepositoryImpl(userMapper,scheduler,service);
       UpdatePasswordUseCase updatePasswordUseCase = new UpdatePasswordUseCase(userRepository);
        ResetPasswordReq resetPasswordReq = new ResetPasswordReq();
        resetPasswordReq.setUserId("512");
        Single<BaseResponse> resp = updatePasswordUseCase.execute(resetPasswordReq);
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }

    @Test
    public void t011_deletePasswordRequest() {
        UserRepositoryImpl userRepository = new UserRepositoryImpl(new UserEntityMapper(),Schedulers.io(), ServiceGenerator.getService());
        DeletePasswordRequestUseCase deletePasswordRequestUseCase = new DeletePasswordRequestUseCase(userRepository);
        Single<BaseResponse> resp = deletePasswordRequestUseCase.execute("512");
        TestObserver<BaseResponse> testObserver = new TestObserver<>();
        resp.subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        Assert.assertEquals("00", testObserver.values().get(0).getErrorCode());
    }


}