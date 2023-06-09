package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Payment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByRental_User_Id(Long userId, Pageable pageRequest);
}
