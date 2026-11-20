package com.yoterra.hippo.jpa.repositories;

import com.yoterra.hippo.jpa.entities.RequestAccess;
import com.yoterra.hippo.jpa.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public interface RequestAccessRepository extends JpaRepository<RequestAccess,Long> {

    public Page<RequestAccess> findByAdmin(User admin, Pageable pageable);
}
