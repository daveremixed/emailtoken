package com.gonzobeans.emailtoken.repository;

import com.gonzobeans.emailtoken.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    List<Token> findBySecretHashAndToken(String secretHash, String token);
    List<Token> findBySecretHashAndEmailAddress(String secretHash, String emailAddress);
    List<Token> findBySecretHashAndUsageId(String secretHash, String usageId);
}
