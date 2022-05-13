package edu.escuelaing.niubank.security;

import edu.escuelaing.niubank.controller.auth.TokenDto;
import edu.escuelaing.niubank.data.User;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;

public class Tokenizer {

    public TokenDto generateToken(String identificador)  {
        Calendar expirationDate = Calendar.getInstance();
        JwtClaims claims = new JwtClaims();
        claims.setSubject(identificador);
        System.out.println(identificador+" generar token");
        claims.setClaim(Data.CONTEXT_NAME, identificador);
        //claims.setClaim(Data.CONTEXT_PASSWORD, user.getPassword());
        claims.setExpirationTimeMinutesInTheFuture(Data.TOKEN_DURATION);
        Key key = new HmacKey(Data.KEY.getBytes(StandardCharsets.UTF_8));
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(key);
        jws.setDoKeyValidation(false);
        String token = "";
        try {
            token = jws.getCompactSerialization();
        }catch (JoseException e){
            e.printStackTrace();
        }
        return new TokenDto(token, expirationDate.getTime());
    }

}
