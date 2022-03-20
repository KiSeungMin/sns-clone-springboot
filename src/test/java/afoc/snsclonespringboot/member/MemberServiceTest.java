package afoc.snsclonespringboot.member;

import afoc.snsclonespringboot.board.Board;
import afoc.snsclonespringboot.member.follow.FollowRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FollowRepository followRepository;

    @AfterEach
    public void afterEach(){
        memberRepository.clear();
        followRepository.clear();
    }

    /*------------------------------------------------------*/
    // Boolean join(Member member)

    @Test
    void joinSingleTest() {
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        boolean isSuccess1 = memberService.join(member1);

        // successful join
        assertThat(isSuccess1).isTrue();
    }

    @Test
    void joinDoubleTest() {
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        Member member2 = Member.builder()
                .username("test2")
                .password("2345")
                .email("test2@test.com")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);

        // successful join
        assertThat(isSuccess1).isTrue();
        assertThat(isSuccess2).isTrue();
    }

    @Test
    void joinDuplicateTest1() {
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        Member member2 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test2@test.com")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);

        // successful join
        assertThat(isSuccess1).isTrue();
        assertThat(isSuccess2).isTrue();
    }

    @Test
    void joinDuplicateTest2() {
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        Member member2 = Member.builder()
                .username("test2")
                .password("2345")
                .email("test1@test.com")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);

        // successful join
        assertThat(isSuccess1).isTrue();
        // join failure
        assertThat(isSuccess2).isFalse();
    }

    /*------------------------------------------------------*/
    // Optional<Member> findMemberByEmail(String email)

    @Test
    void findMemberByEmailSingleTest(){
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        Optional<Member> foundMember1ByEmail = memberService.findMemberByEmail("test1@test.com");

        // successful join
        assertThat(isSuccess1).isTrue();

        // Not null
        assertThat(foundMember1ByEmail).isPresent();

        // must same value
        assertThat(member1).isEqualTo(foundMember1ByEmail.get());
    }

    @Test
    void findMemberByEmailDoubleTest(){
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        Member member2 = Member.builder()
                .username("test2")
                .password("2345")
                .email("test2@test.com")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);
        Optional<Member> foundMember1ByEmail = memberService.findMemberByEmail("test1@test.com");
        Optional<Member> foundMember2ByEmail = memberService.findMemberByEmail("test2@test.com");

        // successful join
        assertThat(isSuccess1).isTrue();
        assertThat(isSuccess2).isTrue();

        // Not null
        assertThat(foundMember1ByEmail).isPresent();
        assertThat(foundMember2ByEmail).isPresent();

        // must same value
        assertThat(member1).isEqualTo(foundMember1ByEmail.get());
        assertThat(member2).isEqualTo(foundMember2ByEmail.get());
    }

    /*------------------------------------------------------*/
    // Optional<Member> findMemberById(Long id)

    @Test
    void findMemberByIdSingleTest(){
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        boolean isSuccess1 = memberService.join(member1);

        Optional<Member> foundMember1ById = memberService.findMemberById(member1.getId());

        // successful join
        assertThat(isSuccess1).isTrue();

        // Not null
        assertThat(foundMember1ById).isPresent();

        // must same value
        assertThat(member1).isEqualTo(foundMember1ById.get());
    }

    @Test
    void findMemberByIdDoubleTest() {
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        Member member2 = Member.builder()
                .username("test2")
                .password("2345")
                .email("test2@test.com")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);
        Optional<Member> foundMember1ById = memberService.findMemberById(member1.getId());
        Optional<Member> foundMember2ById = memberService.findMemberById(member2.getId());

        // successful join
        assertThat(isSuccess1).isTrue();
        assertThat(isSuccess2).isTrue();

        // Not null
        assertThat(foundMember1ById).isPresent();
        assertThat(foundMember2ById).isPresent();

        // must same value
        assertThat(member1).isEqualTo(foundMember1ById.get());
        assertThat(member2).isEqualTo(foundMember2ById.get());
    }

    /*------------------------------------------------------*/
    // Boolean updateMember(Member member)

    @Test
    public void updateMemberSimple(){
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        // join
        boolean isSuccess1 = memberService.join(member1);
        assertThat(isSuccess1).isTrue(); // successful join

        // find member to be updated
        Optional<Member> foundMember1ById = memberService.findMemberById(member1.getId());
        assertThat(foundMember1ById).isPresent(); // Not null

        // set new value
        Member foundMember1 = foundMember1ById.get();
        Member updateMember = Member.builder()
                    .username("test2")
                    .password("2345")
                    .email(foundMember1.getEmail())
                    .build();
        updateMember.setId(foundMember1.getId());

        // update
        boolean isSuccess2 = memberService.updateMember(updateMember);
        assertThat(isSuccess2).isTrue(); // successful update

        // find updated member
        Optional<Member> foundUpdatedMember1ById = memberService.findMemberById(updateMember.getId());
        assertThat(foundUpdatedMember1ById).isPresent(); // Not null

        // check updated value
        assertThat(updateMember).isEqualTo(foundUpdatedMember1ById.get());
    }

    @Test
    public void updateMemberEmailCheck(){
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        // join
        boolean isSuccess1 = memberService.join(member1);
        assertThat(isSuccess1).isTrue(); // successful join

        // find member to be updated
        Optional<Member> foundMember1ById = memberService.findMemberById(member1.getId());
        assertThat(foundMember1ById).isPresent(); // Not null

        // set new value
        Member foundMember1 = foundMember1ById.get();
        Member updateMember = Member.builder()
                .username(foundMember1.getUsername())
                .password(foundMember1.getPassword())
                .email("test2@test.com")
                .build();
        updateMember.setId(foundMember1.getId());

        // update
        boolean isSuccess2 = memberService.updateMember(updateMember);
        assertThat(isSuccess2).isFalse(); // update failure

        // find updated member
        Optional<Member> foundUpdatedMember1ById = memberService.findMemberById(updateMember.getId());
        assertThat(foundUpdatedMember1ById).isPresent(); // Not null

        // check not update value
        assertThat(member1).isEqualTo(foundUpdatedMember1ById.get());
    }

    /*------------------------------------------------------*/
    // Boolean deleteMemberById(Long id);

    @Test
    public void deleteMemberById(){
        Member member1 = Member.builder()
                .username("test1")
                .password("1234")
                .email("test1@test.com")
                .build();

        // join
        boolean isSuccess1 = memberService.join(member1);
        assertThat(isSuccess1).isTrue(); // successful join

        // delete
        boolean isSuccess2 = memberService.deleteMemberById(member1.getId());
        assertThat(isSuccess2).isTrue(); // successful delete

        // check
        Optional<Member> memberByEmail = memberService.findMemberByEmail(member1.getEmail());
        Optional<Member> memberById = memberService.findMemberById(member1.getId());
        assertThat(memberByEmail).isEmpty();
        assertThat(memberById).isEmpty();
    }

    /*------------------------------------------------------*/
    // Boolean follow(Long followerId, Long followeeId);

    @Test
    public void followSingleTest(){
        Member member1 = Member.builder()
                .email("test1@test.com")
                .password("1234")
                .username("testMember1")
                .build();

        Member member2 = Member.builder()
                .email("test2@test.com")
                .password("2345")
                .username("testMember2")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);
        assertThat(isSuccess1).isTrue(); // successful join
        assertThat(isSuccess2).isTrue(); // successful join

        Boolean followSuccess = memberService.follow(member1.getId(), member2.getId());
        assertThat(followSuccess).isTrue(); // member1 -> member2 follow success
    }

    @Test
    public void followManyTest(){
        List<Member> memberList = new ArrayList<Member>();

        for (int i=1;i<11;i++){
            Member member = Member.builder()
                    .email("test" + i + "@test.com")
                    .password("1234")
                    .username("testMember" + i)
                    .build();
            memberList.add(member);
            boolean isSuccess = memberService.join(member);
            assertThat(isSuccess).isTrue();
        }

        for (int i=1;i<11;i++){
            for (int j=1;j<11;j++){
                Member member1 = memberList.get(i-1);
                Member member2 = memberList.get(j-1);
                Boolean followSuccess = memberService.follow(member1.getId(), member2.getId());
                if (i==j){
                    assertThat(followSuccess).isFalse(); // self-follow is not allowed
                } else {
                    assertThat(followSuccess).isTrue(); // member1 -> member2 follow success
                }
            }
        }
    }

    @Test
    public void followMultipleTest(){
        Member member1 = Member.builder()
                .email("test1@test.com")
                .password("1234")
                .username("testMember1")
                .build();

        Member member2 = Member.builder()
                .email("test2@test.com")
                .password("2345")
                .username("testMember2")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);
        assertThat(isSuccess1).isTrue(); // successful join
        assertThat(isSuccess2).isTrue(); // successful join

        for (int i=0;i<30;i++){
            Boolean followSuccess = memberService.follow(member1.getId(), member2.getId());
            if (i==0){
                assertThat(followSuccess).isTrue(); // member1 -> member2 follow success
            } else {
                assertThat(followSuccess).isFalse(); // follow is only one
            }
        }
    }

    /*------------------------------------------------------*/
    // Boolean unfollow(Long followerId, Long followeeId);

    @Test
    public void unfollowSingleTest(){
        Member member1 = Member.builder()
                .email("test1@test.com")
                .password("1234")
                .username("testMember1")
                .build();

        Member member2 = Member.builder()
                .email("test2@test.com")
                .password("2345")
                .username("testMember2")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);
        assertThat(isSuccess1).isTrue(); // successful join
        assertThat(isSuccess2).isTrue(); // successful join

        Boolean followSuccess = memberService.follow(member1.getId(), member2.getId());
        assertThat(followSuccess).isTrue(); // member1 -> member2 follow success

        Boolean unfollowSuccess = memberService.unfollow(member1.getId(), member2.getId());
        assertThat(unfollowSuccess).isTrue(); // member1 -> member2 unfollow success
    }

    @Test
    public void unfollowManyTest(){
        List<Member> memberList = new ArrayList<Member>();

        for (int i=1;i<11;i++){
            Member member = Member.builder()
                    .email("test" + i + "@test.com")
                    .password("1234")
                    .username("testMember" + i)
                    .build();
            memberList.add(member);
            boolean isSuccess = memberService.join(member);
            assertThat(isSuccess).isTrue();
        }

        // follow
        for (int i=1;i<11;i++){
            for (int j=1;j<11;j++){
                Member member1 = memberList.get(i-1);
                Member member2 = memberList.get(j-1);
                Boolean followSuccess = memberService.follow(member1.getId(), member2.getId());
                if (i==j){
                    assertThat(followSuccess).isFalse(); // self-follow is forbidden
                } else {
                    assertThat(followSuccess).isTrue(); // member1 -> member2 follow success
                }
            }
        }

        // unfollow
        for (int i=1;i<11;i++){
            for (int j=1;j<11;j++){
                Member member1 = memberList.get(i-1);
                Member member2 = memberList.get(j-1);
                Boolean unfollowSuccess = memberService.unfollow(member1.getId(), member2.getId());
                if (i==j){
                    assertThat(unfollowSuccess).isFalse(); // self-unfollow is forbidden
                } else {
                    assertThat(unfollowSuccess).isTrue(); // member1 -> member2 unfollow success
                }
            }
        }
    }

    @Test
    public void unfollowMultipleTest(){
        Member member1 = Member.builder()
                .email("test1@test.com")
                .password("1234")
                .username("testMember1")
                .build();

        Member member2 = Member.builder()
                .email("test2@test.com")
                .password("2345")
                .username("testMember2")
                .build();

        boolean isSuccess1 = memberService.join(member1);
        boolean isSuccess2 = memberService.join(member2);
        assertThat(isSuccess1).isTrue(); // successful join
        assertThat(isSuccess2).isTrue(); // successful join

        Boolean followSuccess = memberService.follow(member1.getId(), member2.getId());
        assertThat(followSuccess).isTrue(); // member1 -> member2 follow success

        for (int i=0;i<30;i++){
            Boolean unFollowSuccess = memberService.unfollow(member1.getId(), member2.getId());
            if (i==0){
                assertThat(unFollowSuccess).isTrue(); // member1 -> member2 unfollow success
            } else {
                assertThat(unFollowSuccess).isFalse(); // unfollow is only one
            }
        }
    }

    /*------------------------------------------------------*/
    // List<Long> findFollowers(Long followeeId);

    @Test
    void findFollowersTest() {
        List<Member> memberList = new ArrayList<Member>();

        for (int i=1;i<11;i++){
            Member member = Member.builder()
                    .email("test" + i + "@test.com")
                    .password("1234")
                    .username("testMember" + i)
                    .build();
            memberList.add(member);
            boolean isSuccess = memberService.join(member);
            assertThat(isSuccess).isTrue();
        }

        // follow
        for (int i=1;i<11;i++){
            for (int j=i+1;j<11;j++){
                Member member1 = memberList.get(i-1);
                Member member2 = memberList.get(j-1);
                Boolean followSuccess = memberService.follow(member1.getId(), member2.getId());
                assertThat(followSuccess).isTrue(); // member1 -> member2 follow success
            }
        }

        // followers
        for (int i=1;i<11;i++){
            List<Long> followers = memberService.findFollowers(memberList.get(i-1).getId());
            assertThat(followers).hasSize(i-1);
        }

        // unfollow
        for (int i=1;i<11;i++){
            for (int j=i+1;j<11;j++){
                Member member1 = memberList.get(i-1);
                Member member2 = memberList.get(j-1);
                Boolean unfollowSuccess = memberService.unfollow(member1.getId(), member2.getId());
                assertThat(unfollowSuccess).isTrue(); // member1 -> member2 unfollow success
            }
        }

        // followers
        for (int i=1;i<11;i++){
            List<Long> followers = memberService.findFollowers(memberList.get(i-1).getId());
            assertThat(followers).hasSize(0);
        }
    }

    /*------------------------------------------------------*/
    // List<Long> findFollowees(Long followeeId);

    @Test
    void findFolloweesTest() {
        List<Member> memberList = new ArrayList<Member>();

        for (int i=1;i<11;i++){
            Member member = Member.builder()
                    .email("test" + i + "@test.com")
                    .password("1234")
                    .username("testMember" + i)
                    .build();
            memberList.add(member);
            boolean isSuccess = memberService.join(member);
            assertThat(isSuccess).isTrue();
        }

        // follow
        for (int i=1;i<11;i++){
            for (int j=i+1;j<11;j++){
                Member member1 = memberList.get(i-1);
                Member member2 = memberList.get(j-1);
                Boolean followSuccess = memberService.follow(member1.getId(), member2.getId());
                assertThat(followSuccess).isTrue(); // member1 -> member2 follow success
            }
        }

        // followers
        for (int i=1;i<11;i++){
            List<Long> followers = memberService.findFollowees(memberList.get(i-1).getId());
            assertThat(followers).hasSize(10-i);
        }

        // unfollow
        for (int i=1;i<11;i++){
            for (int j=i+1;j<11;j++){
                Member member1 = memberList.get(i-1);
                Member member2 = memberList.get(j-1);
                Boolean unfollowSuccess = memberService.unfollow(member1.getId(), member2.getId());
                assertThat(unfollowSuccess).isTrue(); // member1 -> member2 unfollow success
            }
        }

        // followers
        for (int i=1;i<11;i++){
            List<Long> followers = memberService.findFollowees(memberList.get(i-1).getId());
            assertThat(followers).hasSize(0);
        }
    }
}