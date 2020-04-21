package example.com.data;

import org.junit.Test;
import org.mockito.Mockito;

import example.com.data.entity.BaseResponseEntity;
import example.com.data.entity.CheckInReqEntity;
import example.com.data.entity.UserEntity;
import example.com.data.entity.UserListEntity;
import example.com.data.entity.mapper.UserEntityMapper;
import example.com.data.net.Service;
import example.com.data.repository.UserRepositoryImpl;
import example.com.domain.model.CheckInReq;
import example.com.domain.model.User;
import example.com.domain.repository.UserRepository;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserUnitTest {
    @Test
    public void t001_doGetListUserShouldHitCorrectService() {
        //create Mock
        Service service = Mockito.mock(Service.class);

        //define Mock Behaviour
        Mockito.when(service.getUserList()).thenReturn(Single.just(new UserListEntity()));

        //create UserImplementationRepository
        UserRepository userRepository = new UserRepositoryImpl(new UserEntityMapper(), Schedulers.io(), service);

        //Call Method In Repository
        userRepository.doGetListUser();

        //Verify If Correct Service Is Hit
        Mockito.verify(service, Mockito.times(1)).getUserList();
    }

    @Test
    public void t002_doGeUserShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        Mockito.when(service.getUser("512")).thenReturn(Single.just(new UserEntity()));

        UserRepository userRepository = new UserRepositoryImpl(new UserEntityMapper(), Schedulers.io(), service);

        userRepository.doGetUser("512");

        Mockito.verify(service, Mockito.times(1)).getUser("512");
    }

    @Test
    public void t003_doLoginShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        User user = new User();

        Mockito.when(service.loginUser(new UserEntity())).thenReturn(Single.just(new UserEntity()));

        UserRepository userRepository = new UserRepositoryImpl(new UserEntityMapper(), Schedulers.io(), service);

        userRepository.doLogin(user);

        Mockito.verify(service, Mockito.times(1)).loginUser(new UserEntity());
    }

    @Test
    public void t004_doAddUseShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        UserEntity userEntity = new UserEntity();
        User user = new User();

        Mockito.when(service.addUser(userEntity)).thenReturn(Single.just(new BaseResponseEntity()));

        UserRepository userRepository = new UserRepositoryImpl(new UserEntityMapper(), Schedulers.io(), service);

        userRepository.doAddUser(user);

        Mockito.verify(service, Mockito.times(1)).addUser(userEntity);
    }

    @Test
    public void t005_doDeleteUserShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        Mockito.when(service.deleteUser("512")).thenReturn(Single.just(new BaseResponseEntity()));

        UserRepository userRepository = new UserRepositoryImpl(new UserEntityMapper(), Schedulers.io(), service);

        userRepository.doDeleteUser("512");

        Mockito.verify(service, Mockito.times(1)).deleteUser("512");
    }

    @Test
    public void t006_doDeleteUserShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        UserEntity userEntity = new UserEntity();
        User user = new User();

        Mockito.when(service.updateUser(userEntity.getUserId(), userEntity)).thenReturn(Single.just(new BaseResponseEntity()));

        UserRepository userRepository = new UserRepositoryImpl(new UserEntityMapper(), Schedulers.io(), service);

        userRepository.doUpdateUser(user.getUserId(), user);

        Mockito.verify(service, Mockito.times(1)).updateUser(userEntity.getUserId(), userEntity);
    }

    @Test
    public void t007_doCheckUserShouldHitCorrectService() {
        Service service = Mockito.mock(Service.class);

        CheckInReq checkInReq = new CheckInReq();
        CheckInReqEntity checkInReqEntity = new CheckInReqEntity();

        Mockito.when(service.checkUser(checkInReqEntity)).thenReturn(Single.just(new BaseResponseEntity()));

        UserRepository userRepository = new UserRepositoryImpl(new UserEntityMapper(), Schedulers.io(), service);

        userRepository.doCheckUser(checkInReq);

        Mockito.verify(service, Mockito.times(1)).checkUser(checkInReqEntity);
    }




}