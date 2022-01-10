package com.project.app.repository;

import com.project.app.entity.UserTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTransactionDetailRepository extends JpaRepository<UserTransactionDetail, String> {
}
