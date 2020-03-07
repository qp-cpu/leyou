package com.leyou.auth.test;


import com.leyou.pojo.UserInfo;
import com.leyou.utils.JwtUtils;
import com.leyou.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "E:\\springcloud\\leyou\\rsa\\rsarsa.pub";
    private static final String priKeyPath = "E:\\springcloud\\leyou\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU4MzEzMzYwMH0.hIQvp1zSzBzmqnKkAMrN394qqRwQ522Ml7HiK_Sq74RnfbQCN4TY7vVl-CT04j0gBiO3mdoAvCDh23dP_ldQ2lrM9Pd4M7JQ_hTWhITxAhMcAtfNRluiRZW9p6qqEw6HfcZbDNMgySk9_7k7OVsZhjittaqY37KPKpHvdbNuUbE";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}