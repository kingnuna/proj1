package com.example.demo.seller;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.member.Member;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	ArrayList<Product> findByNameLike(String name);
}
