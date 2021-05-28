package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용의 메서드를 위해 성능 최적화를 위한 readonly 설정
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional  // 읽기전용 메서드가 아니므로 다시 걸어둠
                    // 클래스 어노테이션 보다 우선권이 높음
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 예외
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findmembers = memberRepository.findByName(member.getName());
        if(!findmembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 특정 회원 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
