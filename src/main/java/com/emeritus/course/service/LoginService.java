package com.emeritus.course.service;

import com.emeritus.course.exception.AccessRestrictedException;
import com.emeritus.course.exception.DataNotFoundException;
import com.emeritus.course.model.EmeritusUser;
import com.emeritus.course.model.login.AccessResponse;
import com.emeritus.course.util.Roles;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    @Autowired
    UserService userService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public static final String BEARER = "Bearer ";

    public AccessResponse getAccessToken(String userName) {
        String token = getToken(userName);

        AccessResponse accessResponse = new AccessResponse();
        accessResponse.setUserName(userName);
        accessResponse.setAccessToken(token);
        return accessResponse;
    }

    private String getToken(String username) {
        List<GrantedAuthority> grantedAuthorities = getGrants(username);

        String token = Jwts
                .builder()
                .setId("emeritusJwts")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return String.join("", BEARER, token);
    }

    private List<GrantedAuthority> getGrants(String username) {
        EmeritusUser emeritusUser = userService.getUser(username);
        if(emeritusUser == null){
            throw new DataNotFoundException(EmeritusUser.class.getSimpleName(), username);
        }
        return AuthorityUtils
                        .commaSeparatedStringToAuthorityList(emeritusUser.getRole());
    }

    public EmeritusUser register(EmeritusUser emeritusUser) {
        if(!Roles.SYSTEM_ADMIN.getRole().equalsIgnoreCase(emeritusUser.getRole())){
            throw new AccessRestrictedException("Register User", emeritusUser.getRole());
        }
        return userService.saveUser(emeritusUser);
    }
}
