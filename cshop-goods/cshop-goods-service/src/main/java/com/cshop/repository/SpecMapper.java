package com.cshop.repository;

import com.cshop.entity.Spec;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SpecMapper extends BaseRepository<Spec, Long> {
}
