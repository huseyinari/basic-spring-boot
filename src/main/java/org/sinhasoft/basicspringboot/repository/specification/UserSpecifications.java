package org.sinhasoft.basicspringboot.repository.specification;

import org.sinhasoft.basicspringboot.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class UserSpecifications {

    public Specification<User> nameEquals(String name) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if (StringUtils.hasText(name))
                    predicate = criteriaBuilder.equal(root.get("name"), name);
                return predicate;
            }
        };
    }
    public Specification<User> surnameLike(String surname) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if (StringUtils.hasText(surname))
                    predicate = criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
                return predicate;
            }
        };
    }
    public Specification<User> emailEqualsIgnoreCase(String email) {
        return (root, criteriaQuery, criteriaBuilder) -> {
                Predicate predicate = null;
                if (StringUtils.hasText(email))
                    predicate = criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), email);
                return predicate;
        };
    }
    public Specification<User> ageGreaterThan(Integer age) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = null;
            if (age != null && age > 0)
                predicate = criteriaBuilder.gt(root.get("age"), age);
            return predicate;
        };
    }

    public Specification<User> customSpesification(SearchCriteria searchCriteria) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (searchCriteria.getValue() == null || searchCriteria.getValue() == "")
                return null;

            if (searchCriteria.getOperation().equalsIgnoreCase(">")) {
                return builder.greaterThanOrEqualTo(
                        root.<String> get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            }
            else if (searchCriteria.getOperation().equalsIgnoreCase("<")) {
                return builder.lessThanOrEqualTo(
                        root.<String> get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            }
            else if (searchCriteria.getOperation().equalsIgnoreCase("=")) {
                if (root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                    return builder.like(
                            root.<String>get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
                } else {
                    return builder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
                }
            }
            return null;
        };
    }
}
