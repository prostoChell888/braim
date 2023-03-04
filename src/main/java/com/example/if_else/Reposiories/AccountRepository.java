package com.example.if_else.Reposiories;

import com.example.if_else.Models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    @Override
    Optional<Account> findById(Integer id);


    Optional<Account> findByEmail(String email);

    @Query(nativeQuery=true,
            value="SELECT * " +
                    "FROM accounts " +
                   "WHERE (:firstName is null or LOWER(first_name) LIKE CONCAT ('%',  :firstName. ,'%')) " +
                    "AND (:lastName is null or LOWER(last_name) LIKE CONCAT ('%',:lastName,'%' )) " +
                    "AND (:email is null or LOWER(email) LIKE  CONCAT('%', :email, '%' )) " +
                    " order by(id) limit :limit offset :offset")
    List<Account> findAccByParams(@Param("firstName") String firstName,
                                  @Param("lastName") String lastName,
                                  @Param("email") String email,
                                  @Param("limit") int limit,
                                  @Param("offset") int offset);
}