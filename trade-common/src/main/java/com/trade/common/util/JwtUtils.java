package com.trade.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * <p>JWT (JSON Web Token) 工具类</p>
 * <p>用于生成、解析和验证JWT</p>
 *
 * @author creator
 * @since 2024-01-01
 */
@Slf4j
@Component // 使其可以被Spring管理，方便注入配置
public class JwtUtils {

    /**
     * JWT 密钥，从配置文件读取，必须足够复杂以保证安全
     * 建议长度至少为256位 (32个ASCII字符)
     */
    @Value("${jwt.secret:defaultSecretKey_must_be_at_least_32_characters_long_for_HS256}")
    private String secret;

    /**
     * JWT 过期时间（毫秒），从配置文件读取，默认为1小时
     */
    @Value("${jwt.expiration:3600000}")
    private long expiration;

    private SecretKey secretKey;

    /**
     * 初始化密钥
     */
    private SecretKey getSecretKey() {
        if (secretKey == null) {
            // 优化：确保密钥长度符合HS256要求，如果不足则进行提示或使用默认安全密钥
            if (!StringUtils.hasText(secret) || secret.length() < 32) {
                log.warn("JWT secret key is not configured or too short (must be at least 32 chars for HS256). Using a default secure key. THIS IS NOT RECOMMENDED FOR PRODUCTION!");
                // Keys.secretKeyFor(SignatureAlgorithm.HS256) 会生成一个安全的随机密钥
                this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            } else {
                this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
            }
        }
        return secretKey;
    }

    /**
     * 生成JWT Token
     *
     * @param subject    主题，通常是用户ID或其他唯一标识
     * @param claims     自定义声明 (payload)
     * @return 生成的JWT字符串
     */
    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims) // 设置自定义声明
                .setSubject(subject) // 设置主题
                .setId(UUID.randomUUID().toString()) // JWT的唯一身份标识
                .setIssuedAt(now) // 设置签发时间
                .setExpiration(expiryDate) // 设置过期时间
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // 设置签名算法和密钥
                .compact();
    }

    /**
     * 生成JWT Token，仅包含 subject
     *
     * @param subject 主题，通常是用户ID或其他唯一标识
     * @return 生成的JWT字符串
     */
    public String generateToken(String subject) {
        return generateToken(subject, null);
    }

    /**
     * 从JWT Token中获取 Claims (Payload)
     *
     * @param token JWT字符串
     * @return Claims 对象
     * @throws ExpiredJwtException      如果JWT已过期
     * @throws UnsupportedJwtException  如果JWT格式不受支持
     * @throws MalformedJwtException    如果JWT格式错误
     * @throws SignatureException       如果签名验证失败
     * @throws IllegalArgumentException 如果token为空或无效
     */
    private Claims getClaimsFromToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new IllegalArgumentException("JWT token cannot be null or empty.");
        }
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从JWT Token中获取主题 (Subject)
     *
     * @param token JWT字符串
     * @return 主题
     */
    public String getSubjectFromToken(String token) {
        try {
            return getClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            log.debug("Failed to get subject from token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从JWT Token中获取指定的 Claim 值
     *
     * @param token    JWT字符串
     * @param claimKey Claim的键
     * @param type     Claim值的类型
     * @param <T>      Claim值的泛型
     * @return Claim值，如果不存在或类型不匹配则返回null
     */
    public <T> T getClaimFromToken(String token, String claimKey, Class<T> type) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.get(claimKey, type);
        } catch (Exception e) {
            log.debug("Failed to get claim '{}' from token: {}", claimKey, e.getMessage());
            return null;
        }
    }

    /**
     * 验证JWT Token是否有效
     *
     * @param token JWT字符串
     * @return 如果token有效返回true，否则返回false
     */
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.warn("Expired JWT token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return false;
    }

    /**
     * 判断JWT Token是否已过期
     *
     * @param token JWT字符串
     * @return 如果已过期返回true，否则返回false。如果token无效也返回true。
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = getClaimsFromToken(token).getExpiration();
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // 明确已过期
        } catch (Exception e) {
            return true; // 其他解析错误，视为无效或已过期
        }
    }

    /**
     * 刷新JWT Token的过期时间
     * <p>
     * 注意：这实际上是重新签发了一个新的Token，包含了原有Token的claims和subject。
     * </p>
     *
     * @param token 旧的JWT字符串
     * @return 新的JWT字符串，如果旧token无效则返回null
     */
    public String refreshToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        try {
            Claims claims = getClaimsFromToken(token);
            // 检查是否允许刷新 (例如，可以在claims中设置一个特定的刷新标记或时间窗口)
            // 此处简单实现为只要未过期即可刷新
            if (claims.getExpiration().after(new Date())) {
                return generateToken(claims.getSubject(), claims);
            }
        } catch (ExpiredJwtException ex) {
            log.warn("Cannot refresh an already expired JWT token: {}", ex.getMessage());
        } catch (Exception e) {
            log.error("Error refreshing JWT token: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取JWT的过期时间
     *
     * @return 过期时间（毫秒）
     */
    public long getExpiration() {
        return expiration;
    }
}