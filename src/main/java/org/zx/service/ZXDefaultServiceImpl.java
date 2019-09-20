package org.zx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.zx.query.ZXPage;
import org.zx.repository.ZXDefaultRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author zhouxin
 * @since 2019/6/5
 */
public class ZXDefaultServiceImpl<T> implements ZXDefaultService<T> {

    @Autowired
    protected ZXDefaultRepository<T> baseRepository;

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return baseRepository.findOne(spec);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return baseRepository.findAll(spec);
    }

    @Override
    public ZXPage<T> findAll(Specification<T> spec, Pageable pageable) {
        Page<T> page = baseRepository.findAll(spec, pageable);
        ZXPage<T> zxPage = ZXPage.of(page);
        return zxPage;
    }

    @Override
    public long count(Specification<T> spec) {
        return baseRepository.count(spec);
    }

    @Override
    public Optional<T> saveOrUpdate(T t) {
        return Optional.ofNullable(baseRepository.saveAndFlush(t));
    }

    @Override
    public Optional<T> findById(Long id) {
        return baseRepository.findById(id);
    }
}
