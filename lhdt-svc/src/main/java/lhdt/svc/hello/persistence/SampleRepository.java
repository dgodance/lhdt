package lhdt.svc.hello.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.hello.domain.SubType0;

public interface SampleRepository extends JpaRepository<SubType0, Long> {
    boolean existsByHelloName(String Name);
    SubType0 findByHelloName(String Name);
    List<SubType0> findAllByHelloName(String Name);
}