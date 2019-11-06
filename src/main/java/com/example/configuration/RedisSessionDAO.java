package com.example.configuration;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.configuration.shiro.ShiroRedisCache;
import com.example.util.SerializeUtils;

public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    /**
     * shiro-redis的session对象前缀
     */
    private ShiroRedisCache  shiroRedisCache;
    
    

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "spring-boot-test:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    private void saveSession(Session session) throws UnknownSessionException{
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }

        byte[] key = getByteKey(session.getId());
        byte[] value = SerializeUtils.serialize(session);
        shiroRedisCache.put(key, value);
    }

    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        shiroRedisCache.remove(this.getByteKey(session.getId()));

    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();

        Set<byte[]> keys = shiroRedisCache.keys();
        if(keys != null && keys.size()>0){
            for(byte[] key:keys){
                Session s = (Session)shiroRedisCache.get(key);
                sessions.add(s);
            }
        }

        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("session id is null");
            return null;
        }

        Session s = (Session)SerializeUtils.deserialize(shiroRedisCache.get(this.getByteKey(sessionId))==null?null:(byte[])shiroRedisCache.get(this.getByteKey(sessionId)));
        return s;
    }

    /**
     * 获得byte[]型的key
     */
    private byte[] getByteKey(Serializable sessionId){
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }

   

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

	public ShiroRedisCache getShiroRedisCache() {
		return shiroRedisCache;
	}

	public void setShiroRedisCache(ShiroRedisCache shiroRedisCache) {
		this.shiroRedisCache = shiroRedisCache;
	}
}