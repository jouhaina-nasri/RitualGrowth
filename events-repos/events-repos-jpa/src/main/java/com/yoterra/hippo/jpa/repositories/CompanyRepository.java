package com.yoterra.hippo.jpa.repositories;

import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.*;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long>, JpaSpecificationExecutor<Company> {
    public Page<Company> findByOwner(User owner, Pageable pageable);
    public Boolean existsByWebsite(String website);

    public Optional<Company> findByWebsite(String website);
}
