package io.javac.minoss.minosscommon.plugin;

import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * JWT 协议加密和解析
 *
 * @author pencilso
 * @date 2020/1/23 8:07 下午
 */
@Slf4j
@Validated
public class JwtPlugin {

    @Value("${jwt.secret}")
    private String secret;

    /**
     * key -> user id
     */
    private final String CLAIN_KEY_ID = "id";
    /**
     * key -> user salt
     */
    private final String CLATIN_KEY_SALT = "salt";
    /**
     * key -> login time
     */
    private final String CLAIN_KEY_TIME = "time";


    /**
     * generate new token
     *
     * @param id   data id
     * @param salt salt
     * @return
     */
    public Optional<String> generateToken(@NotNull Long id, @NotEmpty String salt) {
        String token = null;
        try {
            Map<String, Object> body = new HashMap<>();
            body.put(CLAIN_KEY_ID, id);
            body.put(CLATIN_KEY_SALT, salt);
            body.put(CLAIN_KEY_TIME, System.currentTimeMillis());
            token = Jwts.builder()
                    .setClaims(body)
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
            log.debug("jwt generateToken success id: [{}] salt: [{}]", id, salt);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("jwt generateToken fail id: [{}]  salt: [{}]", id, salt, ex);
        }
        return Optional.ofNullable(token);

    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public Optional<JwtAuthModel> getOauthEntity(@NotEmpty String token) {
        JwtAuthModel jwtAuthModel = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            jwtAuthModel = new JwtAuthModel()
                    .setId((Long) claims.get(CLAIN_KEY_ID))
                    .setSalt((String) claims.get(CLATIN_KEY_SALT))
                    .setLoginTime((Long) claims.get(CLAIN_KEY_TIME));
        } catch (Exception ex) {
            log.warn("jwt getOauthEntity fail token: [{}]", token, ex);
        }
        return Optional.ofNullable(jwtAuthModel);
    }


}