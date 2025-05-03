package com.kianchart.kianchart.restriction;

import com.kianchart.kianchart.entity.UserEntity;
import com.kianchart.kianchart.model.UserModel;
import com.kianchart.kianchart.utils.JPARestriction;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.sql.Restriction;
import org.hibernate.sql.RestrictionRenderingContext;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserRestriction implements Specification<UserEntity> {
    private final UserModel.Search userModelSearch;

    public UserRestriction(UserModel.Search userModelSearch) {
        this.userModelSearch = userModelSearch;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (userModelSearch.getFullname() != null && StringUtils.isNotBlank(userModelSearch.getFullname())) {
            predicates.add(criteriaBuilder.like(root.get("fullname"), "%" + userModelSearch.getFullname() + "%"));
        }
        if (userModelSearch.getEmail() != null && StringUtils.isNotBlank(userModelSearch.getEmail())) {
            predicates.add(criteriaBuilder.equal(root.get("email"), userModelSearch.getEmail()));
        }
        if (userModelSearch.getIsActive() != null && userModelSearch.getIsActive()) {
            predicates.add(criteriaBuilder.equal(root.get("isActive"), true));
        }
        if (userModelSearch.getGender() != null && StringUtils.isNotBlank(userModelSearch.getGender())) {
            predicates.add(criteriaBuilder.equal(root.get("gender"), userModelSearch.getGender()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

//public class UserRestriction implements JPARestriction {
//
//    private final UserModel.Search userModelSearch;
//
//    public UserRestriction(UserModel.Search userModelSearch) {
//        this.userModelSearch = userModelSearch;
//    }
//
//    @Override
//    public Specification countSpec(CriteriaBuilder var1, CriteriaQuery var2, Root var3) {
//        return null;
//    }
//
//    @Override
//    public Specification listSpec(CriteriaBuilder var1, CriteriaQuery var2, Root var3) {
//        return this::applyFilter;
//    }
//
//    private Predicate applyFilter(Root<?> rootEntity, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        var predicateList = new ArrayList<Predicate>();
//
//        if (userModelSearch.getFullname() != null && StringUtils.isNotBlank(userModelSearch.getFullname())) {
//            predicateList.add(criteriaBuilder.like(rootEntity.get("fullname"), "%" + userModelSearch.getFullname() + "%"));
//        }
//        if (userModelSearch.getEmail() != null && StringUtils.isNotBlank(userModelSearch.getEmail())) {
//            predicateList.add(criteriaBuilder.equal(rootEntity.get("email"), userModelSearch.getEmail()));
//        }
//        if (userModelSearch.getIsActive() != null && userModelSearch.getIsActive()) {
//            predicateList.add(criteriaBuilder.equal(rootEntity.get("isActive"), true));
//        }
//        if (userModelSearch.getGender() != null && StringUtils.isNotBlank(userModelSearch.getGender())) {
//            predicateList.add(criteriaBuilder.equal(rootEntity.get("gender"), userModelSearch.getGender()));
//        }
//        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
//    }
//}
