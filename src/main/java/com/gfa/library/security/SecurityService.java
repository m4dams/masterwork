package com.gfa.library.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.library.exceptions.InternalServerException;
import com.gfa.library.users.models.dtos.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SecurityService {

    public static final int TOKEN_EXPIRATION_MINUTES = 30;

    @Value("${jwt.key}")
    private String key;

    public static String objToJson(Object obj) throws JsonProcessingException {
      return new ObjectMapper().writeValueAsString(obj);
    }

    public static String createKeyString() {
      SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
      return Encoders.BASE64.encode(key.getEncoded());
    }

    public static boolean isValidJwsToken(String jwsToken) {
      Pattern pattern = Pattern.compile("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$");
      Matcher matcher = pattern.matcher(jwsToken);
      return matcher.find();
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String generateToken(Object data) throws InternalServerException {
      Map<String, Object> claims = new HashMap<>();
      if (data instanceof UserDto) {
        UserDto user = (UserDto) data;
        claims.put("userId", user.getId());
        claims.put("accessLevel", user.getAccessLevel());
        return createToken(claims, user.getUsername());
      }
      try {
        return createToken(claims, objToJson(data));
      } catch (JsonProcessingException e) {
        throw new InternalServerException("Unable to create JWS token.");
      }
    }

    public Object getObjectFromJws(String jws, Object obj) {
      Jws<Claims> parseJws = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(jws);

      try {
        return jsonToObj(parseJws.getBody().getSubject(), obj);
      } catch (JsonProcessingException e) {
        throw new InternalServerException("Unable to cast JWS to " + obj.getClass().getSimpleName());
      }
    }

    public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private String createToken(Map<String, Object> claims, String subject) {
      SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
      long nowInMillis = System.currentTimeMillis();
      long expirationDurationInMillis = 1000 * 60 * TOKEN_EXPIRATION_MINUTES;
      return Jwts.builder().setClaims(claims)
          .setSubject(subject)
          .setIssuedAt(new Date(nowInMillis))
          .setExpiration(new Date(nowInMillis + expirationDurationInMillis))
          .signWith(secretKey)
          .compact();
    }

    private Object jsonToObj(String json, Object obj) throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, obj.getClass());
    }
}
