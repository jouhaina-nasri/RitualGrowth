package com.yoterra.hippo.forms;

import java.util.Objects;

public enum UserRoleType {
    CONSULTANT(Aliases.CONSULTANT),
    MANAGER(Aliases.MANAGER),
    EXECUTIVE(Aliases.EXECUTIVE),
    ASSOCIATE(Aliases.ASSOCIATE),
    OPERATOR(Aliases.OPERATOR);


    private static final UserRoleType [] VALS = values();
    private final String alias;

    private UserRoleType(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public static UserRoleType fromAlias(String alias) {
        for (UserRoleType ct : VALS) {
            if(Objects.equals(ct.alias, alias))
                return ct;
        }
        return null;
    }

    public static class Aliases {
        public static final String CONSULTANT = "consultant";
        public static final String MANAGER = "manager";
        public static final String EXECUTIVE = "executive";
        public static final String ASSOCIATE = "associate";
        public static final String OPERATOR = "operator";
    }
}
