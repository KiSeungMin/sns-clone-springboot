package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.member.follow.Follow;
import afoc.snsclonespringboot.member.follow.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    /*------------------------------------------------------*/
    // Basic Member Functions

    @Override
    public Boolean join(Member member) throws IllegalStateException{

        if(memberRepository.findMemberByMemberEmail(member.getEmail()).isPresent()){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        memberRepository.save(member);

        return true;
    }

    @Override
    public Optional<Member> findMemberById(Long memberId){
        return memberRepository.findMemberByMemberId(memberId);
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findMemberByMemberEmail(email);
    }

    @Override
    public Boolean updateMember(Member member) {
        Optional<Member> foundMember = findMemberById(member.getId());
        if (foundMember.isEmpty()) {
            return false;
        } else {
            String email1 = foundMember.get().getEmail();
            String email2 = member.getEmail();
            if (!Objects.equals(email1, email2)){
                return false;
            } else {
                memberRepository.updateMember(member);
                return true;
            }
        }
    }

    @Override
    public Boolean deleteMemberById(Long id) {
        return memberRepository.deleteMemberByMemberId(id);
    }

    // UserDetailsService 인터페이스의 메소드 오버라이딩

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        Optional<Member> retMember = memberRepository.findMemberByMemberEmail(email);

        if(retMember.isEmpty()){
            throw new UsernameNotFoundException(email);
        }

        Member member = retMember.get();

        // UserDetail을 구현하고 있는 User 객체 반환
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    /*------------------------------------------------------*/
    // Follow Functions

    @Override
    public Boolean followIsPresent(Long followerId, Long followeeId){
        return followRepository.findFollow(followerId, followeeId).isPresent();
    }

    @Override
    public Boolean follow(Long followerId, Long followeeId) {

        if (Objects.equals(followerId, followeeId)){
            return false;
        }

        Optional<Follow> findFollow = followRepository.findFollow(followerId, followeeId);
        if(findFollow.isPresent()){
            unfollow(followerId, followeeId);
            return false;
        } else {
            Follow follow = Follow.builder()
                    .followerId(followerId)
                    .followeeId(followeeId)
                    .build();
            Optional<Follow> res = followRepository.save(follow);
            return res.isPresent();
        }
    }

    @Override
    public Boolean unfollow(Long followerId, Long followeeId) {
        return followRepository.deleteFollow(followerId, followeeId);
    }

    @Override
    public List<Long> findFollowers(Long followeeId) {
        return followRepository.findFollowersByFolloweeId(followeeId);
    }

    @Override
    public List<Long> findFollowees(Long followerId) {
        return followRepository.findFolloweesByFollowerId(followerId);
    }

    /*------------------------------------------------------*/
    // Authentication

    @Override
    public Optional<Member> getAuthenticationMember(){

        // 인증된 객체의 정보를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {

            String email = "";

            // getName 메서드를 통해 member의 email을 가져온다. (SecurityConfig 파일에서 username parameter를 email로 설정해서 그런듯)
            email = authentication.getName();
            return findMemberByEmail(email);

        } else{
           return Optional.empty();
        }
    }
}
