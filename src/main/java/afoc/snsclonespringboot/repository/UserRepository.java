package afoc.snsclonespringboot.repository;

import afoc.snsclonespringboot.domain.User;

import java.util.Optional;

public interface UserRepository {
    // 등록
    User save(User user);

    // User id 이용해 유저 조회
    Optional<User> findByUserId(Long userId);

    // User id 이용해 수정
    Optional<User> updateByUserId(Long userId);

    // User id 이용해 삭제
    // return 값 성공/실패
    Boolean deleteByUserId(Long userId);
}
