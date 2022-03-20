package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.member.follow.Follow;
import afoc.snsclonespringboot.member.follow.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    /*------------------------------------------------------*/
    // Basic Member Functions

    @Override
    public Boolean join(Member member){
        try{
            Optional<Member> foundMember = findMemberByEmail(member.getEmail());
            if (foundMember.isPresent()) {
                return false;
            } else {
                memberRepository.save(member);
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
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

    /*------------------------------------------------------*/
    // Follow Functions

    @Override
    public Boolean follow(Long followerId, Long followeeId) {
        if (Objects.equals(followerId, followeeId)){
            return false;
        }

        Optional<Follow> findFollow = followRepository.findFollow(followerId, followeeId);
        if(findFollow.isPresent()){
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
}
