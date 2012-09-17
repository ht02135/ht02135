package com.hung.auction.dao2;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hung.auction.domain2.ImplicitPolymorphicCat;
import com.hung.auction.domain2.ImplicitPolymorphicDog;
import com.hung.auction.domain2.JoinedAnimal;
import com.hung.auction.domain2.JoinedCat;
import com.hung.auction.domain2.JoinedDog;
import com.hung.auction.domain2.SingleTableAnimal;
import com.hung.auction.domain2.SingleTableCat;
import com.hung.auction.domain2.SingleTableDog;
import com.hung.auction.domain2.TablePerClassAnimal;
import com.hung.auction.domain2.TablePerClassCat;
import com.hung.auction.domain2.TablePerClassDog;

public class HibernateAnimalDAO extends HibernateDaoSupport implements AnimalDAO {

    //////////////Table Per Hierarchy /////////////////////////////////////////////////////////
    @Override
    public void save(SingleTableCat cat) {
        getHibernateTemplate().save(cat);
    }

    @Override
    public void save(SingleTableDog dog) {
        getHibernateTemplate().save(dog);
    }

    public void delete(SingleTableCat cat) {
        getHibernateTemplate().delete(cat);
    }
    public void delete(SingleTableDog dog) {
        getHibernateTemplate().delete(dog);
    }

    @Override
    public List<SingleTableAnimal> findSingleTableAnimals() {
        return (List<SingleTableAnimal>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from SingleTableAnimal sta");
                return (List<SingleTableAnimal>) query.list();
            }
        });
    }

    //////////////Joined Strategy (Table Per Subclass (table will be created per each abstrct/non-abstract class))
    @Override
    public void save(JoinedCat cat) {
        getHibernateTemplate().save(cat);
    }

    @Override
    public void save(JoinedDog dog) {
        getHibernateTemplate().save(dog);
    }

    public void delete(JoinedCat cat) {
        getHibernateTemplate().delete(cat);
    }
    public void delete(JoinedDog dog) {
        getHibernateTemplate().delete(dog);
    }

    @Override
    public List<JoinedAnimal> findJoinedAnimals() {
        return (List<JoinedAnimal>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from JoinedAnimal ja");
                return (List<JoinedAnimal>) query.list();
            }
        });
    }

    //////////////Table Per Class with Implicit (indirect, not trivial) Polymorphism
    @Override
    public void save(ImplicitPolymorphicCat cat) {
        getHibernateTemplate().save(cat);
    }

    @Override
    public void save(ImplicitPolymorphicDog dog) {
        getHibernateTemplate().save(dog);
    }

    public void delete(ImplicitPolymorphicCat cat) {
        getHibernateTemplate().delete(cat);
    }
    public void delete(ImplicitPolymorphicDog dog) {
        getHibernateTemplate().delete(dog);
    }

    @Override
    public List<ImplicitPolymorphicCat> findImplicitPolymorphicCats() {
        return (List<ImplicitPolymorphicCat>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from ImplicitPolymorphicCat ipc");
                return (List<ImplicitPolymorphicCat>) query.list();
            }
        });
    }

    @Override
    public List<ImplicitPolymorphicDog> findImplicitPolymorphicDogs() {
        return (List<ImplicitPolymorphicDog>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from ImplicitPolymorphicDog ipd");
                return (List<ImplicitPolymorphicDog>) query.list();
            }
        });
    }

    //////////////Table Per Class with Union ////////////////////////////////////////////////////
    @Override
    public void save(TablePerClassCat cat) {
        getHibernateTemplate().save(cat);
    }

    @Override
    public void save(TablePerClassDog dog) {
        getHibernateTemplate().save(dog);
    }

    public void delete(TablePerClassCat cat) {
        getHibernateTemplate().delete(cat);
    }
    public void delete(TablePerClassDog dog) {
        getHibernateTemplate().delete(dog);
    }

    @Override
    public List<TablePerClassAnimal> findTablePerClassAnimals() {
        return (List<TablePerClassAnimal>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from TablePerClassAnimal tpca");
                return (List<TablePerClassAnimal>) query.list();
            }
        });
    }
}