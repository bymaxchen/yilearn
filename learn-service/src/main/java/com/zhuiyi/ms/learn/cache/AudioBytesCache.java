package com.zhuiyi.ms.learn.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class AudioBytesCache {
    @Cacheable(value = "audioBytes", key = "{#sessionId, #type}")
    public byte[] getBytes(String sessionId, Integer type) {
        return new byte[]{};
    }

    @CachePut(value = "audioBytes", key = "{#sessionId, #type}")
    public byte[] saveBytes(String sessionId,  Integer type,  byte[] bytes) {
        return bytes;
    }

    @CacheEvict(value = "audioBytes", key = "{#sessionId, #type}")
    public void emptyBytes(String sessionId,  Integer type) {}

    @Cacheable(value = "audioBytesOfDfs", key = "{#sessionId}")
    public byte[] getBytesOfDfs(String sessionId) {
        return new byte[]{};
    }

    @CachePut(value = "audioBytesOfDfs", key = "{#sessionId}")
    public byte[] saveBytesOfDfs(String sessionId,  byte[] bytes) {
        return bytes;
    }

    @CacheEvict(value = "audioBytesOfDfs", key = "{#sessionId}")
    public void emptyBytesOfDfs(String sessionId) {}
}
