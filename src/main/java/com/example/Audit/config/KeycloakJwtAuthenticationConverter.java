package com.example.Audit.config;

import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken>, InitializingBean {

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private List<String> clientIds;

    @Override
    public void afterPropertiesSet() throws IllegalArgumentException {
        if (CollectionUtils.isEmpty(clientIds)) {
            throw new IllegalArgumentException("The property value app.keycloak.jwt.role.locations is mandatory.");
        }
    }

    @Override
    public AbstractAuthenticationToken convert(@NonNull final Jwt source) {
        return new JwtAuthenticationToken(source, Stream.concat(new JwtGrantedAuthoritiesConverter().convert(source)
                        .stream(), extractResourceRoles(source).stream())
                .collect(Collectors.toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt) {
        final var resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
        final var resourceRoles = new ArrayList<>();

        clientIds.forEach(id -> {
            if (resourceAccess.containsKey(id)) {
                final Map<String, List<String>> resource = (Map<String, List<String>>) resourceAccess.get(id);
                resourceRoles.addAll(resource.get("roles"));
            }
        });
        return resourceRoles.isEmpty() ? new HashSet<>()
                : resourceRoles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toSet());
    }
}
