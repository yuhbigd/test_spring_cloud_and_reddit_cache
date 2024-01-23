package com.example.testloadbalancer;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class Service2 {
    @Autowired
    private Repo repo;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void save(String path) {
        var a = new ABCTEST();
        a.setPath(path);
        repo.save(a);
        redisTemplate.opsForValue().set(path + "---))", a);
        // if (1 == 1) {
        //     throw new RuntimeCryptoException();
        // }
    }
}
