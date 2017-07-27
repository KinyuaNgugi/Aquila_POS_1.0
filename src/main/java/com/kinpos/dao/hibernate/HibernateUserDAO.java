package com.kinpos.dao.hibernate;

import com.kinpos.dao.UserDAO;
import com.kinpos.models.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by kinyua on 6/19/15.
 */
public class HibernateUserDAO implements UserDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<UserEntity> getAllMyUsers() {
        Session session=sessionFactory.openSession();
        List<UserEntity> userEntityList=session.createCriteria(UserEntity.class).list();
        session.close();
        return userEntityList;
    }

    @Override
    public UserEntity getMyUser(Integer myUserEntityId) {
        Session session=sessionFactory.openSession();
        UserEntity userEntity=(UserEntity) session.get(UserEntity.class, myUserEntityId);
        session.close();
        return userEntity;
    }

    @Override
    public void purgeMyUser(UserEntity userEntity) {
        sessionFactory.getCurrentSession().delete(userEntity);
    }

    @Override
    public UserEntity saveMyUser(UserEntity userEntity) {
        sessionFactory.getCurrentSession().save(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity updateMyUser(UserEntity userEntity) {
        sessionFactory.getCurrentSession().update(userEntity);
        return userEntity;
    }

    @Override
    public List<UserEntity> getMyUserByUserName(String userName) {
        Session session=sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserEntity.class, userName);
        criteria.add(Restrictions.eq("username", userName));
        List list = criteria.list();
        session.close();
        return list;
    }
}
