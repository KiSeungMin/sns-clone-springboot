package afoc.snsclonespringboot.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Boolean join(Member member){
        Optional<Member> foundMember = findMemberByEmail(member.getEmail());
        if (foundMember.isPresent()) {
            return false;
        } else {
            memberRepository.save(member);
            return true;
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
}
