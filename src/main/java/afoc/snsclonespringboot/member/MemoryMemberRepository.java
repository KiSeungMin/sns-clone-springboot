package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<Board> findBoardListByMemberId(Long memberId){
        return null;
    }

    @Override
    public Boolean updateMember(Member member) {
        Member returnValue = store.get(member.getId());
        if (returnValue != null){
            store.put(member.getId(), member);
            return true;
        } else {
            return false;
        }
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
