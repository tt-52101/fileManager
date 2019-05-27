/**
 * 
 */
package cn.cs.fileManager.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.cs.fileManager.dto.FmUserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

/**
 * @author al89
 *
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtTokenUtil {
	 /**
     * header名称
     */
    private String tokenHeader;

    /**
     * token前缀
     */
    private String tokenPrefix;

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private Long expiration;

    /**
     * 选择记住后过期时间
     */
    private Long rememberExpiration;

    /**
     * 生成token
     *
     * @param userDTO,setsubject用的是loginName
     * @return
     */
    public String createToken(FmUserDTO userDTO) {
        Long time = userDTO.getRemember() ? this.rememberExpiration : this.expiration;
        Map<String, Object> map = new HashMap<>(1);
        map.put("user", userDTO);
        return Jwts.builder()
                .setClaims(map)
                .setSubject(userDTO.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public String getUserName(String token) {
        return generateToken(token).getSubject();
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public Claims generateToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取userDTO
     *
     * @param token
     * @return
     */
    public FmUserDTO getUserDTO(String token) {
        Claims claims = generateToken(token);
        Map<String, String> map = claims.get("user", Map.class);
        FmUserDTO userDTO = JSON.parseObject(JSON.toJSONString(map), FmUserDTO.class);
        return userDTO;
    }
}
