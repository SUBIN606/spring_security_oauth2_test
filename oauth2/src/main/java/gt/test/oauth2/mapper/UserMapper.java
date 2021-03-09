package gt.test.oauth2.mapper;

import gt.test.oauth2.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    User findByName(String username);

}
