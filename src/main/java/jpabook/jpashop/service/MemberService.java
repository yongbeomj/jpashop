package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @Transactional(readOnly = true) 옵션 적용 시 조회 모드에서 성능 최적화
// 해당 서비스의 경우 조회 메서드가 많으므로 readOnly = true 옵션을 전체 클래스에 부여하고 수정 필요한 부분만 false 옵션을 별도 적용
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 변수만 생성자 만듦
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    // 데이터 변경은 기본적으로 트랜잭션 안에서 이루어짐
    // 데이터 변경이 있는 곳에는 readOnly = true 옵션 넣으면 절대 안됨. 넣을 경우 수정 불가
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 회원 수정
    // public Member update(){} 로 반환하는 것 보다 void나 id만 반환하는 것이 더 낫다
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
