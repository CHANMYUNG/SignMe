package com.nooheat.manager;

import com.nooheat.support.Routing;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.io.UnsupportedEncodingException;

/**
 * Created by NooHeat on 17/08/2017.
 */
public class JWTManager {
    private static byte[] key;

    static {
        try {
            key = "signme-server-secret".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            key = null;
        }
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

    public static boolean verify(RoutingContext context) {
        HttpServerRequest req = context.request();

        String token = null;
        if (req.getHeader("x-access-token") == null) {
            if (req.getParam("token") == null) {
                return false;
            } else token = req.getParam("token");
        } else token = req.getHeader("x-access-token");

        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
            //OK, we can trust this JWT
        } catch (SignatureException e) {
            return false;
            //don't trust the JWT!
        }
    }
}
