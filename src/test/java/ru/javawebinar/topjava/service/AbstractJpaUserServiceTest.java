package ru.javawebinar.topjava.service;

import org.junit.Assume;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Set;

public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    protected JpaUtil jpaUtil;

    @Override
    public void setup() {
        super.setup();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
