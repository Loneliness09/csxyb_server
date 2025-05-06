import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.dao.impl.UserDaoImpl;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.service.UserService;
import com.iflytek.csxyb.service.impl.UserServiceImpl;
import com.iflytek.csxyb.servlet.base.ServletBase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UserTest {
    UserDao userDao = new UserDaoImpl();
    UserService userService = new UserServiceImpl();
    @Test
    public void loginTest() {
        String loginText = "twq";
        String password = "123";
        User user = new User(loginText, password);
        user = userDao.loginByText(user);
        System.out.println(user);
    }

    @Test
    public void insertTest() {
        String userName = "nnn";
        String loginText = "hahaha";
        String password = "123";

        User user = new User(userName, loginText, password);
        int a = userDao.insert(user);
        System.out.println(a);
    }

    @Test
    public void updateTest() {
        String loginText = "hahaha";
        String password = "123";
        User user = userService.loginByText(loginText, password);
        user.setPhoneNumber("123456");
        int n = userService.updateUser(user, user);
        System.out.println(n);
    }

    @Test
    public void deleteTest() {
        String loginText = "hahaha";
        String password = "123";
        User user = userService.loginByText(loginText, password);
        int n = userService.unRegister(user, user);
        System.out.println(n);
    }

    @Test
    public void test() {
        Map<String, Object> resData = new HashMap<String, Object>();
        ServletBase.reqFail(resData);
        System.out.println(resData);
    }

}
