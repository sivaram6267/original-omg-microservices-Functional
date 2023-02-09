package com.lancesoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.Authorities;

public interface AuthoritiesRepo extends JpaRepository<Authorities, Integer> {
	Authorities findByRole(String role);
}
