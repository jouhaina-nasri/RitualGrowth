package com.yoterra.hippo.jpa.repositories;

import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.UserCompany;
import com.yoterra.hippo.jpa.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserCompanyRepository extends JpaRepository<UserCompany,Long> {

    @Query(value = "SELECT c.company from UserCompany c WHERE c.user = :active")
    public Page<Company> getCompaniesByUser(@Param("active")User active, Pageable pageable);

    public Optional<UserCompany> findByUserAndCompany(User user, Company company);

    public void deleteByUserAndCompany(User user, Company company);
}
