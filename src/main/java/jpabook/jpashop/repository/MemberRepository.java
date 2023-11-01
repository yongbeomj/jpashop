package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository 에서 기본적인 CRUD 기능 제공 (상상할 수 있는 거의 모든 것들 제공)
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
}
