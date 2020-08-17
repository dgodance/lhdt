package lhdt.svc.hello.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lhdt.svc.hello.domain.SubType0;
import lhdt.svc.hello.persistence.SampleRepository;
import lhdt.svc.hello.service.SampleService;

@Service
public class SampleServiceImpl extends SampleService {
    @Autowired
    SampleRepository studyRepository;

    @Override
    public SubType0 save(SubType0 vo) {
        return this.studyRepository.save(vo);
    }

    @Override
    public List<SubType0> findAll() {
        ArrayList<SubType0> result = new ArrayList<SubType0>();
        this.studyRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<SubType0> findAllById(Long id) {
        ArrayList<SubType0> result = new ArrayList<SubType0>();
        this.studyRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Page<SubType0> findAllByPage(Pageable pageable) {
        return this.studyRepository.findAll(pageable);
    }

    @Override
    public boolean existVoByUk(SubType0 vo) {
        return this.studyRepository.existsByHelloName(vo.getHelloName());
    }

    @Override
    public SubType0 findByUk(SubType0 vo) {
        return this.studyRepository.findByHelloName(vo.getHelloName());
    }

    @Override
    public List<SubType0> findAllByUk(SubType0 vo) {
        return this.studyRepository.findAllByHelloName(vo.getHelloName());
    }

    @Override
    public void deleteByVo(SubType0 vo) {
        this.studyRepository.delete(vo);
    }
}
