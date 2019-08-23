package com.cshop.repository;

import com.cshop.entity.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceMapper extends BaseRepository<Resource, Long> {

    @Query(value = "SELECT res_key FROM tb_resource WHERE id IN ( SELECT resource_id FROM tb_role_resource WHERE role_id IN ( SELECT role_id FROM tb_admin_role WHERE admin_id IN  (SELECT id FROM tb_admin WHERE login_name=#{loginName} ) ) )", nativeQuery = true)
    public List<String> findResKeyByLoginName(@Param("loginName") String loginName);


}
