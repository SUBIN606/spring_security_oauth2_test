package gt.test.oauth2.service;

import gt.test.oauth2.mapper.UserMapper;
import gt.test.oauth2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findByPk(String username) {
        return userMapper.findByName(username);
    }

}
