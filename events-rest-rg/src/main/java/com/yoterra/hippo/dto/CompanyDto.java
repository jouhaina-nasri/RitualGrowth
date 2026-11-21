package com.yoterra.hippo.dto;

import com.yoterra.hippo.jpa.entities.Company;
import com.yoterra.hippo.jpa.entities.Privilege;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.utils.Opt;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CompanyDto {
    private long id;
    private String name;
    private String website;
    private UserDto owner;

    public static CompanyDto of (Company company) {
        return Opt.get(company, CompanyDto::new);
    }

    public CompanyDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.website = company.getWebsite();
        this.owner = UserDto.of(company.getOwner());
    }


}
