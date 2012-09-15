package com.hung.auction.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;



@Entity
@Table(name="DOMAIN")
@org.hibernate.annotations.Table(appliesTo="DOMAIN", indexes={@Index(name="domainIndex", columnNames={"DOMAIN_NAME", "DOMAIN_DESCRIPTION"})})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SqlResultSetMappings({
    // entity-type-result
    @SqlResultSetMapping(name="domainByClass",entities={ @EntityResult(entityClass=Domain.class) }),
    @SqlResultSetMapping(name="domainByFields", entities={ @EntityResult(entityClass=Domain.class, fields={
        @FieldResult(name="name",column="DOMAIN_NAME"),
        @FieldResult(name="description",column="DOMAIN_DESCRIPTION"),
        @FieldResult(name="parentDomain",column="PARENT_DOMAIN_NAME")
    }) }),
    @SqlResultSetMapping(name="domainByAliasFields", entities={ @EntityResult(entityClass=Domain.class, fields={
        @FieldResult(name="name",column="DOMAIN_NAME2"),
        @FieldResult(name="description",column="DOMAIN_DESCRIPTION2"),
        @FieldResult(name="parentDomain",column="PARENT_DOMAIN_NAME2")
    }) }),

    // scalar-type-results
    @SqlResultSetMapping(name="domainNameOnly",columns={ @ColumnResult(name="DOMAIN_NAME") }),
    @SqlResultSetMapping(name="parentNameOnly",columns={ @ColumnResult(name="PARENT_DOMAIN_NAME") }),

    // mix scalar-type & entity-type result
    @SqlResultSetMapping(name="mixedResult",
        entities={ @EntityResult(entityClass=Domain.class, fields={
            @FieldResult(name="name",column="DOMAIN_NAME"),
            @FieldResult(name="description",column="DOMAIN_DESCRIPTION"),
            @FieldResult(name="parentDomain",column="PARENT_DOMAIN_NAME")}) },
        columns={ @ColumnResult(name="DOMAIN_NAME2") }),
})
@NamedNativeQueries({
    // MYSQL store procedures
    // @NamedNativeQuery(name="getNamesByPatternStoreProcedure", query="CALL SELECT_NAME_BY_PATTERN(:pattern)", resultSetMapping="domainNameOnly"),
    // @NamedNativeQuery(name="getDomainsByPatternStoreProcedure", query="CALL SELECT_DOMAIN_BY_PATTERN(:pattern)", resultSetMapping="domainByClass"),

    // ORACLE - problem with calling ORACLE store procedures, so use HQL instead
    @NamedNativeQuery(name="getNamesByPatternStoreProcedure", query="SELECT DOMAIN_NAME FROM DOMAIN WHERE DOMAIN_NAME LIKE :pattern", resultSetMapping="domainNameOnly"),
    @NamedNativeQuery(name="getDomainsByPatternStoreProcedure", query="SELECT * FROM DOMAIN WHERE DOMAIN_NAME LIKE :pattern", resultSetMapping="domainByClass"),

    // SQL
    @NamedNativeQuery(name="getNamesByPatternSQL", query="SELECT DOMAIN_NAME FROM DOMAIN WHERE DOMAIN_NAME LIKE :pattern", resultSetMapping="domainNameOnly"),
    @NamedNativeQuery(name="getParentNamesByPatternSQL", query="SELECT PARENT_DOMAIN_NAME FROM DOMAIN WHERE DOMAIN_NAME LIKE :pattern", resultSetMapping="parentNameOnly"),
    @NamedNativeQuery(name="getDomainsByPatternSQL", query="SELECT * FROM DOMAIN WHERE DOMAIN_NAME LIKE :pattern", resultSetMapping="domainByClass"),
    @NamedNativeQuery(name="getDomainsByFieldsPatternSQL", query = "SELECT DOMAIN_NAME, DOMAIN_DESCRIPTION, PARENT_DOMAIN_NAME FROM DOMAIN WHERE DOMAIN_NAME LIKE :pattern", resultSetMapping = "domainByFields"),
    @NamedNativeQuery(name="getDomainsByAliasFieldsPatternSQL", query = "SELECT DOMAIN_NAME AS DOMAIN_NAME2, DOMAIN_DESCRIPTION AS DOMAIN_DESCRIPTION2, PARENT_DOMAIN_NAME AS PARENT_DOMAIN_NAME2 FROM DOMAIN WHERE DOMAIN_NAME LIKE :pattern", resultSetMapping = "domainByAliasFields"),
    @NamedNativeQuery(name="getMixedResult", query = "SELECT d.DOMAIN_NAME, d.DOMAIN_DESCRIPTION, d.PARENT_DOMAIN_NAME, d.DOMAIN_NAME AS DOMAIN_NAME2 FROM DOMAIN d WHERE d.DOMAIN_NAME LIKE :pattern", resultSetMapping = "mixedResult")
})
@NamedQueries({
    @NamedQuery(name = "getDomainsByPatternHQL", query = "FROM Domain d WHERE d.name LIKE :pattern")
})
@XmlRootElement(name="Domain")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class Domain implements Comparable, Serializable {
    private static Logger log = Logger.getLogger(Domain.class);

    // ---------- Class variables -------------------------------------------------------------------
    public static final String ROOT_NAME = "root";
    public static final String SUBROOT_NAME = "subroot";

    public static final Comparator DESCRIPTION_COMPARATOR = new DomainDescriptionComparator();
    public static final Comparator PARENT_NAME_COMPARATOR = new DomainParentNameComparator();

    // ---------- Columns -------------------------------------------------------------------
    @Id
    @Column(name="DOMAIN_NAME", nullable=false)
    @XmlAttribute(name="name")
    private String name;

    @Column(name="DOMAIN_DESCRIPTION")
    @XmlElement(name="description")
    private String description;

    // ---------- Relationships -------------------------------------------------------------------
    // Self-Join relationship mapping - start
    @ManyToOne
    @JoinColumn(name="PARENT_DOMAIN_NAME")	// @JoinColumn indicate owner of relationship
    @XmlAnyElement(lax=true)
    private Domain parentDomain = null;

    @OneToMany(mappedBy="parentDomain", cascade=CascadeType.ALL /*, fetch=FetchType.LAZY */)	// owned by parentDomain
    @XmlElementWrapper(name="subDomains")
    @XmlAnyElement(lax = true)
    // @OrderBy("name")
    private List<Domain> subDomains = null;
    // Self-Join relationship mapping - end

    @OneToMany(mappedBy="userDomain", cascade=CascadeType.ALL)
    @XmlElementWrapper(name="users")
    @XmlAnyElement(lax = true)
    private List<DomainUser> users = null;

    @OneToMany(mappedBy="settingDomain", cascade=CascadeType.ALL)
    private List<StringDomainSetting> settings = null;

    // ---------- Constructors -------------------------------------------------------------------
    public Domain() {}

    public Domain(String name) {
        setName(name);
    }

    public Domain(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public Domain(String name, Domain parentDomain) {
        setName(name);
        setParentDomain(parentDomain);
    }

    public Domain(String name, String description, Domain parentDomain) {
        setName(name);
        setDescription(description);
        setParentDomain(parentDomain);
    }

    // ---------- get/set -------------------------------------------------------------------
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Domain getParentDomain() { return parentDomain; }
    public void setParentDomain(Domain parentDomain) { this.parentDomain = parentDomain; }

    public List<Domain> getSubDomains() { return subDomains; }
    public void setSubDomains(List<Domain> subDomains) { this.subDomains = subDomains; }

    public List<DomainUser> getUsers() { return users; }
    public void setUsers(List<DomainUser> users) { this.users = users; }

    // ------------- Comparable interface ---------------------------------------------------
    public int compareTo(Object o) {
        Domain domain = (Domain) o;
        return this.name.compareTo(domain.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;	// null check
        }
        if (this == obj) {
            return true;
        }

        // Some people would prefer 'if (!(o instanceof ClassName)) ...'
        if (getClass() != obj.getClass()) {
            return false;
        }

        // field comparison
        Domain otherObj = (Domain) obj;
        return new EqualsBuilder()
            .append(this.name, otherObj.getName())	// name is the key field
            .isEquals();
    }

    @Override
    public int hashCode(){
        int hasCode = new HashCodeBuilder()
            .append(this.name)	// name is the key field
            .toHashCode();
        return hasCode;
    }

    public String toString() {
        return "[name="+name+",description="+description+",parentDomain="+parentDomain+"]";
    }

    // ------ nested class : comparator ----------------------------------------------------------

    // description
    private static class DomainDescriptionComparator implements Comparator, Serializable {
        private static Logger log = Logger.getLogger(DomainDescriptionComparator.class);

        @Override
        public int compare(Object o1, Object o2) {
            Domain domain1 = (Domain) o1;
            Domain domain2 = (Domain) o2;
            String description1 = domain1.getDescription();
            String description2 = domain2.getDescription();
            return description1.compareTo(description2);
        }
    }

    // parentDomain name
    private static class DomainParentNameComparator implements Comparator, Serializable {
        private static Logger log = Logger.getLogger(DomainParentNameComparator.class);

        @Override
        public int compare(Object o1, Object o2) {
            Domain domain1 = (Domain) o1;
            Domain domain2 = (Domain) o2;
            String parentName1 = (domain1.getParentDomain() != null) ? domain1.getParentDomain().getName() : "";
            String parentName2 = (domain2.getParentDomain() != null) ? domain2.getParentDomain().getName() : "";
            return parentName1.compareTo(parentName2);
        }
    }
}