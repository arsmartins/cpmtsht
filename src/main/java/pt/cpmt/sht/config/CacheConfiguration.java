package pt.cpmt.sht.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(pt.cpmt.sht.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Localizacao.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Coordenadas.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Estabelecimento.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Estabelecimento.class.getName() + ".funcionarios", jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Risco.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Funcionario.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Funcionario.class.getName() + ".postoTrabalhos", jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.PostoTrabalho.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.PostoTrabalho.class.getName() + ".riscos", jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Empresa.class.getName(), jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Empresa.class.getName() + ".estabelecimentos", jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.Empresa.class.getName() + ".funcionarios", jcacheConfiguration);
            cm.createCache(pt.cpmt.sht.domain.TipoRisco.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
