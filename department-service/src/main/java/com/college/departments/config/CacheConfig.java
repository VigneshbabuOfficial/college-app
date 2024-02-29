package com.college.departments.config;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	@Value("${spring.cache.host}")
	private String host;
	@Value("${spring.cache.port}")
	private Integer port;

	Logger log = Logger.getLogger(CacheConfig.class);

//	@Autowired
//	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private CustomRedisDeserializer customRedisDeserializer;

	@Bean
	JedisConnectionFactory redisConnectionFactory() {

		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);

		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().build();

		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);

		/*
		 * JedisClientConfiguration.JedisClientConfigurationBuilder
		 * jedisClientConfiguration = JedisClientConfiguration .builder();
		 * jedisClientConfiguration.connectTimeout(Duration.ofMillis(10)); // Set the
		 * connection timeout to 10 seconds jedisClientConfiguration.usePooling();
		 */

		/*
		 * JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
		 * redisConnectionFactory.setHostName(host);
		 * redisConnectionFactory.setPort(port); return redisConnectionFactory;
		 */
	}

	@Bean
	RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

		/*
		 * RedisTemplate<String, Object> template = new RedisTemplate<>();
		 * template.setConnectionFactory(rcf); template.setKeySerializer(new
		 * StringRedisSerializer()); template.setValueSerializer(new
		 * JsonRedisSerializer());
		 */

		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	/*
	 * class JsonRedisSerializer implements RedisSerializer<Object> {
	 * 
	 * private final ObjectMapper om;
	 * 
	 * public JsonRedisSerializer() { this.om = new
	 * ObjectMapper().enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY); }
	 * 
	 * @Override public byte[] serialize(Object t) throws SerializationException {
	 * try { return om.writeValueAsBytes(t); } catch (JsonProcessingException e) {
	 * throw new SerializationException(e.getMessage(), e); } }
	 * 
	 * @Override public Object deserialize(byte[] bytes) throws
	 * SerializationException {
	 * 
	 * if (bytes == null) { return null; }
	 * 
	 * try { return om.readValue(bytes, Object.class); } catch (Exception e) {
	 * log.warn(e.getMessage(), e); } return null; }
	 * 
	 * }
	 */

	/*
	 * @Bean CacheManager cacheManager(RedisConnectionFactory
	 * redisConnectionFactory) {
	 * 
	 * RedisCacheConfiguration defaultCacheConfig =
	 * RedisCacheConfiguration.defaultCacheConfig()
	 * .entryTtl(Duration.ofMinutes(1)); // Set default expiration time to 10
	 * minutes return
	 * RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
	 * defaultCacheConfig).fromConnectionFactory(redisConnectionFactory)..build();
	 * 
	 * RedisSerializationContext.SerializationPair<Object> jsonSerializer =
	 * RedisSerializationContext.SerializationPair
	 * .fromSerializer(customRedisDeserializer);
	 * 
	 * RedisCacheManager cacheManager = RedisCacheManager.RedisCacheManagerBuilder
	 * .fromConnectionFactory(redisConnectionFactory).serializationPair(
	 * jsonSerializer).build();
	 * 
	 * // return cacheManager;
	 * 
	 * }
	 */

	/*
	 * @Bean KeyGenerator customKeyGenerator() { return new KeyGenerator() {
	 * 
	 * @Override public Object generate(Object o, Method method, Object... objects)
	 * { StringBuilder sb = new StringBuilder(); sb.append(o.getClass().getName());
	 * sb.append(method.getName()); for (Object obj : objects) {
	 * sb.append(obj.toString()); } return sb.toString(); } }; }
	 */

}
