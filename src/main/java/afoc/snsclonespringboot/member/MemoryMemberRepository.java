package afoc.snsclonespringboot.member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {

        member.setId(++sequence);

        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findByMemberId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByMemberEmail(String email) {
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Optional<Member> updateByMemberId(Long memberId) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteByMemberId(Long memberId) {
        Member returnValue = store.remove(memberId);

        return returnValue != null;
    }
}
