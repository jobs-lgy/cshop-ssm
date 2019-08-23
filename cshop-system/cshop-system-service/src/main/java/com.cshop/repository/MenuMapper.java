package com.cshop.repository;

import com.cshop.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuMapper extends BaseRepository<Menu, Long> {

    @Query(value = "SELECT * FROM Menu WHERE id IN ( SELECT menu_id FROM tb_role_menu WHERE role_id IN ( SELECT role_id FROM tb_admin_role WHERE admin_id IN  (SELECT id FROM tb_admin WHERE login_name=#{loginName} ) ) )", nativeQuery = true)
    public List<Menu> findMenuListByLoginName(@Param("loginName") String loginName);

}
