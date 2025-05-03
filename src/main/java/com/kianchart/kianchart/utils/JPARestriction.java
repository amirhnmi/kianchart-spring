package com.kianchart.kianchart.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public interface JPARestriction {
    Specification countSpec(CriteriaBuilder var1, CriteriaQuery var2, Root var3);

    Specification listSpec(CriteriaBuilder var1, CriteriaQuery var2, Root var3);

    default boolean distinct() {
        return false;
    }
}
