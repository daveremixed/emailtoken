package com.gonzobeans.emailtoken.service;

import com.gonzobeans.emailtoken.model.Token;
import com.gonzobeans.emailtoken.repository.TokenRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// I have set up this class so it can handle requests for many tokens, or a single token

@Service
public class TokenService {

    // email regex validation from http://emailregex.com/ -- RFC5322
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)"
        + "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\"
        + "x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2["
        + "0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-"
        + "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    // compile this pattern for reuse
    private static final Pattern EMAIL_REGEX_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public List<String> findInvalidEmails(Set<String> emailAddresses) {
        List<String> invalidEmails = new ArrayList<>();
        emailAddresses.forEach(s -> {
            if (!validateEmail(s)) {
                invalidEmails.add(s);
            }
        });
        return invalidEmails;
    }

    public boolean validateEmail(String emailAddress) {
        return EMAIL_REGEX_PATTERN.matcher(emailAddress).matches();
    }

    public Optional<Token> registerToken(String applicationSecret, String tokenId, String emailAddress) {
        List<Token> tokenList = createTokens(applicationSecret, tokenId, Collections.singleton(emailAddress));
        if (!tokenList.isEmpty()) {
            tokenRepository.save(tokenList.get(0));
            return Optional.of(tokenList.get(0));
        }
        return Optional.empty();
    }

    public Optional<Token> checkForToken(String applicationSecret, String token) {
        final String hash = getHash(applicationSecret);
        List<Token> tokenList = tokenRepository.findBySecretHashAndToken(hash, token);
        if (tokenList != null && !tokenList.isEmpty()) {
            return Optional.of(tokenList.get(0));
        }
        return Optional.empty();
    }

    // Pretty much the same code as above, but the syntax with Optionals is tricky so I cut my losses.
    public Optional<List<Token>> registerTokens(String applicationSecret, String tokenId, Set<String> emailAddresses) {
        List<Token> tokenList = createTokens(applicationSecret, tokenId, emailAddresses);
        if (!tokenList.isEmpty()) {
            //persist
            return Optional.of(tokenList);
        }
        return Optional.empty();
    }

    // This method does not have any validation because validation happens prior to its use
    // Uses Apache Codec Library to generate the Hash
    private List<Token> createTokens(String applicationSecret, String tokenId, Set<String> emailAddresses) {
        final String hash = getHash(applicationSecret);

        return emailAddresses.stream()
                .filter(Objects::nonNull)
                .map(emailAddress -> Token.Builder()
                .withSecretHash(hash)
                .withUsageId(tokenId)
                .withEmailAddress(emailAddress)
                .withToken(createTokenData())
                .build()).collect(Collectors.toList());
    }

    private String createTokenData() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    private String getHash(String data) {
        return DigestUtils.sha1Hex(data);
    }

}
