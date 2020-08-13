package lhdt.svc.hello.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lhdt.svc.hello.domain.SubType0;

public interface SampleRepository extends CrudRepository<SubType0, Long> {
    boolean existsByHelloName(String Name);
    SubType0 findByHelloName(String Name);
    List<SubType0> findAllByHelloName(String Name);
}