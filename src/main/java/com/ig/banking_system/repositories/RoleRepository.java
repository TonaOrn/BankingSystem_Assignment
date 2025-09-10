package com.ig.banking_system.repositories;

import com.ig.banking_system.model.Permission;
import com.ig.banking_system.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = """
    SELECT DISTINCT p.*
    FROM permission p
             INNER JOIN role_permission rp ON rp.permission_id = p.id
             INNER JOIN user_role ur ON ur.role_id = rp.role_id
    WHERE ur.user_id = :userId
    """, nativeQuery = true)
    public List<Permission> getRolePermissionByUser(Long userId);
}
