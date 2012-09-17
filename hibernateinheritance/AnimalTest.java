package com.hung.auction.dao2;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

public class AnimalTest extends TestCase {

    private static Logger log = Logger.getLogger(AnimalTest.class);

    private ApplicationContext applicationContext = null;
    private AnimalDAO animalDAO = null;

    protected void setUp() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext-test-dao.xml");
        animalDAO = (AnimalDAO) applicationContext.getBean("animalDAO");
    }

    public void testAnimal() {

        /*
        animalDAO.save(new SingleTableCat("SingleTableCat1"));
        animalDAO.save(new SingleTableCat("SingleTableCat2"));
        animalDAO.save(new SingleTableDog("SingleTableDog1"));
        animalDAO.save(new SingleTableDog("SingleTableDog2"));

        animalDAO.save(new JoinedCat("JoinedCat1"));
        animalDAO.save(new JoinedCat("JoinedCat2"));
        animalDAO.save(new JoinedDog("JoinedDog1"));
        animalDAO.save(new JoinedDog("JoinedDog2"));

        animalDAO.save(new ImplicitPolymorphicCat("ImplicitPolymorphicCat1"));
        animalDAO.save(new ImplicitPolymorphicCat("ImplicitPolymorphicCat2"));
        animalDAO.save(new ImplicitPolymorphicDog("ImplicitPolymorphicDog1"));
        animalDAO.save(new ImplicitPolymorphicDog("ImplicitPolymorphicDog2"));

        animalDAO.save(new TablePerClassCat("TablePerClassCat1"));
        animalDAO.save(new TablePerClassCat("TablePerClassCat2"));
        animalDAO.save(new TablePerClassDog("TablePerClassDog1"));
        animalDAO.save(new TablePerClassDog("TablePerClassDog2"));
        */

        //////////////Table Per Hierarchy /////////////////////////////////////////////////////////

        List<SingleTableAnimal> singleTableAnimals = animalDAO.findSingleTableAnimals();
        log.info("testAnimal: >>>>>> singleTableAnimals="+singleTableAnimals+" <<<<<<<");

        ////////////// Joined Strategy (Table Per Subclass (table will be created per each abstrct/non-abstract class))

        List<JoinedAnimal> joinedAnimals = animalDAO.findJoinedAnimals();
        log.info("testAnimal: >>>>>> joinedAnimals="+joinedAnimals+" <<<<<<<");

        ////////////// Table Per Class with Implicit (indirect, not trivial) Polymorphism

        List<ImplicitPolymorphicCat> implicitPolymorphicCats = animalDAO.findImplicitPolymorphicCats();
        List<ImplicitPolymorphicDog> implicitPolymorphicDogs = animalDAO.findImplicitPolymorphicDogs();

        log.info("testAnimal: >>>>>> implicitPolymorphicCats="+implicitPolymorphicCats+" <<<<<<<");
        log.info("testAnimal: >>>>>> implicitPolymorphicDogs="+implicitPolymorphicDogs+" <<<<<<<");

        ////////////// Table Per Class with Union ////////////////////////////////////////////////////

        List<TablePerClassAnimal> tablePerClassAnimals = animalDAO.findTablePerClassAnimals();
        log.info("testAnimal: >>>>>> tablePerClassAnimals="+tablePerClassAnimals+" <<<<<<<");
    }
}
