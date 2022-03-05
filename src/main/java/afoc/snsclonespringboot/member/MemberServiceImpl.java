package afoc.snsclonespringboot.member;

public class MemberServiceImpl {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(Member member){
        memberRepository.save(member);
    }

    public Member findMemberById(Long memberId){
        return memberRepository.findMemberByMemberId(memberId).get();
    }

}
