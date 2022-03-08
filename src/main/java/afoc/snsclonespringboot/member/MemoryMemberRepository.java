package afoc.snsclonespringboot.member;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private static final Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    /*------------------------------------------------------*/
    // Member CRUD

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findMemberByMemberId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findMemberByMemberEmail(String email) {
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }

    @Override
<<<<<<< HEAD
    public Boolean updateMember(Member member) {
        Member returnValue = store.get(member.getId());
        if (returnValue != null){
            store.put(member.getId(), member);
            return true;
        } else {
            return false;
        }
=======
    public Optional<Member> findMemberByBoardId(Long boardId){
        return null;
    }

    @Override
    public Optional<Member> updateMemberByMemberId(Long memberId) {
        return Optional.empty();
>>>>>>> 7ff9faa0104b90e71d2b438e918eb6552e8698f0
    }

    @Override
    public Boolean deleteMemberByMemberId(Long memberId) {
        Member returnValue = store.remove(memberId);
        return returnValue != null;
    }

    /*------------------------------------------------------*/
    // Follow

    /*------------------------------------------------------*/
    // Extras

    @Override
    public void clear(){
        store.clear();
        sequence = 0L;
    }
}
