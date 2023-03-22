package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // 근데 이러면 다른 인스턴스의 사용과 같다
    //그래서 아래와 같은
    //DI 방식
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    //한글로 변경 가능
    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hello");
        //when
        Long saveId = memberService.join(member);
        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_가입_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");
        //shift+F6으로 refactor
        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        //ctl+alt+v 로 바로 변수로 빼기
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //여기서 try - catch 문으로도 가능하나 더 좋은 방법이 있다
        assertThat(e.getMessage()).isEqualTo("이미 있는 회원입니다.");
        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}