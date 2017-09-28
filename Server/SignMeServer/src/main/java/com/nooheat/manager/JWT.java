package com.nooheat.manager;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by NooHeat on 17/08/2017.
 */
public class JWT {

    private String token;
    private String uid;
    private String name;
    private boolean isAdmin;
    private static byte[] key;

    static {
        try {
            key = "signme-server-secret".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            key = null;
        }
    }

    private JWT(String token) {
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        this.uid = (String) jws.getBody().get("uid");
        this.name = (String) jws.getBody().get("name");
        this.isAdmin = (boolean) jws.getBody().get("isAdmin");

    }

    public static String createToken(String uid, String name, boolean isAdmin) {


        Claims claims = new DefaultClaims();
        claims.put("signme.com", true);
        claims.put("uid", uid);
        claims.put("name", name);
        claims.put("isAdmin", isAdmin);

        String token = Jwts.builder().setSubject("userInfo").setIssuer("nooheat.com").setClaims(claims).signWith(SignatureAlgorithm.HS512, key).compact();

        return token;
    }

    public static JWT verify(RoutingContext context) {

        Cookie cookie = context.getCookie("signme-x-access-token");
        String header = context.request().getHeader("signme-x-access-token");
        if (cookie == null && header == null) return null;

        String token = cookie != null ? cookie.getValue() : header.replace("\"", "");

        if (token.trim().equals("")) return null;

        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return new JWT(token);
            //OK, we can trust this JWT
        } catch (SignatureException e) {
            e.printStackTrace();
            return null;
            //don't trust the JWT!
        }
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
