package com.hung.auction.dao2;

import java.util.List;

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

public interface AnimalDAO {

    //////////////Table Per Hierarchy /////////////////////////////////////////////////////////

    public void save(SingleTableCat cat);
    public void save(SingleTableDog dog);

    public void delete(SingleTableCat cat);
    public void delete(SingleTableDog dog);

    /*
Hibernate:
    select
        singletabl0_.SINGLE_TABLE_ANIMAL_ID as SINGLE2_101_,
        singletabl0_.SINGLE_TABLE_ANIMAL_NAME as SINGLE3_101_,
        singletabl0_.owner_OWNER_ID as owner4_101_,
        singletabl0_.SINGLE_TABLE_ANIMAL_TYPE as SINGLE1_101_
    from
        SINGLE_TABLE_ANIMAL singletabl0_
     */
    public List<SingleTableAnimal> findSingleTableAnimals();

    ////////////// Joined Strategy (Table Per Subclass (table will be created per each abstrct/non-abstract class))

    public void save(JoinedCat cat);
    public void save(JoinedDog dog);

    public void delete(JoinedCat cat);
    public void delete(JoinedDog dog);

    /*
Hibernate:
    select
        joinedanim0_.JOINED_ANIMAL_ID as JOINED1_98_,
        joinedanim0_.JOINED_ANIMAL_NAME as JOINED2_98_,
        joinedanim0_.owner_OWNER_ID as owner3_98_,
        case
            when joinedanim0_1_.JOINED_ANIMAL_ID is not null then 1
            when joinedanim0_2_.JOINED_DOG_ID is not null then 2
            when joinedanim0_.JOINED_ANIMAL_ID is not null then 0
        end as clazz_
    from
        JOINED_ANIMAL joinedanim0_
    left outer join
        JOINED_CAT joinedanim0_1_
            on joinedanim0_.JOINED_ANIMAL_ID=joinedanim0_1_.JOINED_ANIMAL_ID
    left outer join
        JOINED_DOG joinedanim0_2_
            on joinedanim0_.JOINED_ANIMAL_ID=joinedanim0_2_.JOINED_DOG_ID
     */
    public List<JoinedAnimal> findJoinedAnimals();

    ////////////// Table Per Class with Implicit (indirect, not trivial) Polymorphism

    public void save(ImplicitPolymorphicCat cat);
    public void save(ImplicitPolymorphicDog dog);

    public void delete(ImplicitPolymorphicCat cat);
    public void delete(ImplicitPolymorphicDog dog);

    /*
Hibernate:
    select
        implicitpo0_.IMPLICIT_POLYMORPHIC_CAT_ID as IMPLICIT1_96_,
        implicitpo0_.IMPLICIT_POLYMORPHIC_CAT_NAME as IMPLICIT2_96_,
        implicitpo0_.owner_OWNER_ID as owner3_96_
    from
        IMPLICIT_POLYMORPHIC_CAT implicitpo0_
     */
    public List<ImplicitPolymorphicCat> findImplicitPolymorphicCats();

    /*
Hibernate:
    select
        implicitpo0_.IMPLICIT_POLYMORPHIC_DOG_ID as IMPLICIT1_97_,
        implicitpo0_.IMPLICIT_POLYMORPHIC_DOG_NAME as IMPLICIT2_97_,
        implicitpo0_.owner_OWNER_ID as owner3_97_
    from
        IMPLICIT_POLYMORPHIC_DOG implicitpo0_
     */
    public List<ImplicitPolymorphicDog> findImplicitPolymorphicDogs();

    ////////////// Table Per Class with Union ////////////////////////////////////////////////////

    public void save(TablePerClassCat cat);
    public void save(TablePerClassDog dog);

    public void delete(TablePerClassCat cat);
    public void delete(TablePerClassDog dog);

    /*
Hibernate:
    select
        tablepercl0_.TABLE_PER_CLASS_ANIMAL_ID as TABLE1_102_,
        tablepercl0_.TABLE_PER_CLASS_ANIMAL_NAME as TABLE2_102_,
        tablepercl0_.owner_OWNER_ID as owner3_102_,
        tablepercl0_.clazz_ as clazz_
    from
        ( select
            TABLE_PER_CLASS_ANIMAL_NAME,
            owner_OWNER_ID,
            TABLE_PER_CLASS_ANIMAL_ID,
            1 as clazz_
        from
            TABLE_PER_CLASS_CAT
        union
        all select
            TABLE_PER_CLASS_ANIMAL_NAME,
            owner_OWNER_ID,
            TABLE_PER_CLASS_ANIMAL_ID,
            2 as clazz_
        from
            TABLE_PER_CLASS_DOG
    ) tablepercl0_
     */
    public List<TablePerClassAnimal> findTablePerClassAnimals();

}