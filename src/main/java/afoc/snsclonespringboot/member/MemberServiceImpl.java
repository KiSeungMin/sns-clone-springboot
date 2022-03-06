package afoc.snsclonespringboot.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public boolean join(Member member){
        memberRepository.save(member);
        return true;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findMemberByMemberEmail(email);
    }

    @Override
    public Optional<Member> findMemberById(Long memberId){
        return memberRepository.findMemberByMemberId(memberId);
    }

}
