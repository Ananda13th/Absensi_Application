package example.com.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import example.com.data.entity.UserEntity;
import example.com.data.entity.UserListEntity;
import example.com.data.entity.mapper.UserEntityMapper;
import example.com.domain.model.User;
import example.com.domain.model.UserList;


public class UserMapperUnitTest {
    @Test
    public void t001_userRespToDomainShouldReturnCorrectData() {

        UserEntityMapper userEntityMapper = new UserEntityMapper();
        UserListEntity userListEntity = new UserListEntity();
        UserEntity userEntity = new UserEntity();
        List<UserEntity> userEntityList = new ArrayList<>();

        userEntity.setName("Budi");
        userEntity.setPassword("123456");
        userEntity.setRole("member");
        userEntity.setUserId("512");

        userEntityList.add(userEntity);

        userListEntity.setUserList(userEntityList);
        userListEntity.setErrorCode("00");
        userListEntity.setErrorMessage("Sukses");

        UserList userList = userEntityMapper.userRespToDomain(userListEntity);

        Assert.assertNotNull(userList);
        Assert.assertEquals(userEntityList.size(), userList.getUserList().size());
        for (User user:userList.getUserList()) {
            Assert.assertEquals("Budi", user.getName());
            Assert.assertEquals("512", user.getUserId());
            Assert.assertEquals("member", user.getRole());
            Assert.assertEquals("123456", user.getPassword());
        }
    }
}