package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "CUSTOMER", schema = TaxServiceConstants.DB_SCHEMA_NAME)
public class Customer extends SalesManagerEntity<Long, Customer> implements Auditable {
    private static final long serialVersionUID = -6966934116557219193L;

    @Id
    @Column(name = "CUSTOMER_ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
            pkColumnValue = "CUSTOMER_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<CustomerAttribute> attributes = new HashSet<CustomerAttribute>();

    @Column(name="CUSTOMER_GENDER", length=1, nullable=true)
    @Enumerated(value = EnumType.STRING)
    private CustomerGender gender;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CUSTOMER_DOB")
    private Date dateOfBirth;

    @Email
    @NotEmpty
    @Column(name="CUSTOMER_EMAIL_ADDRESS", length=96, nullable=false)
    private String emailAddress;

    @Column(name="CUSTOMER_NICK", length=96)
    private String nick;

    @Column(name="CUSTOMER_COMPANY", length=100)
    private String company;


    @Column(name="CUSTOMER_PASSWORD", length=60)
    private String password;


    @Column(name="CUSTOMER_ANONYMOUS")
    private boolean anonymous;

    @Column(name = "REVIEW_AVG")
    private BigDecimal customerReviewAvg;

    @Column(name = "REVIEW_COUNT")
    private Integer customerReviewCount;

    @Column(name="PROVIDER")
    private String provider;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Language.class)
    @JoinColumn(name = "LANGUAGE_ID", nullable=false)
    private Language defaultLanguage;



    @OneToMany(mappedBy = "customer", targetEntity = ProductReview.class)
    private List<ProductReview> reviews = new ArrayList<ProductReview>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MERCHANT_ID", nullable=false)
    private MerchantStore merchantStore;


    @Embedded
    private Delivery delivery = null;

    @Valid
    @Embedded
    private Billing billing = null;


    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "CUSTOMER_GROUP", schema= TaxServiceConstants.DB_SCHEMA_NAME, joinColumns = {
            @JoinColumn(name = "CUSTOMER_ID", nullable = false, updatable = false) }
            ,
            inverseJoinColumns = { @JoinColumn(name = "GROUP_ID",
                    nullable = false, updatable = false) }
    )
    @Cascade({
            org.hibernate.annotations.CascadeType.DETACH,
            org.hibernate.annotations.CascadeType.LOCK,
            org.hibernate.annotations.CascadeType.REFRESH,
            org.hibernate.annotations.CascadeType.REPLICATE

    })
    private List<Group> groups = new ArrayList<Group>();

    @Transient
    private String showCustomerStateList;

    @Transient
    private String showBillingStateList;

    @Transient
    private String showDeliveryStateList;


    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Date getDateOfBirth() {
        return CloneUtils.clone(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = CloneUtils.clone(dateOfBirth);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }


    public List<ProductReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ProductReview> reviews) {
        this.reviews = reviews;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }
    public String getShowCustomerStateList() {
        return showCustomerStateList;
    }

    public void setShowCustomerStateList(String showCustomerStateList) {
        this.showCustomerStateList = showCustomerStateList;
    }

    public String getShowBillingStateList() {
        return showBillingStateList;
    }

    public void setShowBillingStateList(String showBillingStateList) {
        this.showBillingStateList = showBillingStateList;
    }

    public String getShowDeliveryStateList() {
        return showDeliveryStateList;
    }

    public void setShowDeliveryStateList(String showDeliveryStateList) {
        this.showDeliveryStateList = showDeliveryStateList;
    }

    public Language getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(Language defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public void setAttributes(Set<CustomerAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<CustomerAttribute> getAttributes() {
        return attributes;
    }

    public void setGender(CustomerGender gender) {
        this.gender = gender;
    }

    public CustomerGender getGender() {
        return gender;
    }

    public BigDecimal getCustomerReviewAvg() {
        return customerReviewAvg;
    }

    public void setCustomerReviewAvg(BigDecimal customerReviewAvg) {
        this.customerReviewAvg = customerReviewAvg;
    }

    public Integer getCustomerReviewCount() {
        return customerReviewCount;
    }

    public void setCustomerReviewCount(Integer customerReviewCount) {
        this.customerReviewCount = customerReviewCount;
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}

