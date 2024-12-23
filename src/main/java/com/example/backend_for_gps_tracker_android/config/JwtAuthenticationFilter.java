package com.example.backend_for_gps_tracker_android.config;

import com.example.backend_for_gps_tracker_android.service.impl.JwtService;
import com.example.backend_for_gps_tracker_android.service.impl.UserServiceImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import java.io.IOException;


/**
 * Фильтр аутентификации на основе JWT, который проверяет наличие токена
 * в заголовках запросов и устанавливает контекст безопасности для
 * аутентифицированного пользователя.
 *
 * <p>Этот фильтр наследуется от {@link OncePerRequestFilter}, что
 * гарантирует, что он будет вызван один раз для каждого запроса.
 *
 * <p>Токен ожидается в заголовке Authorization с префиксом "Bearer ".
 * Если токен присутствует и валиден, пользователь аутентифицируется
 * и добавляется в контекст безопасности.
 *
 * <p>Обрабатывает следующие действия:
 * <ul>
 *     <li>Извлечение токена из заголовка запроса.</li>
 *     <li>Валидация токена и извлечение имени пользователя.</li>
 *     <li>Загрузка деталей пользователя и установка аутентификации
 *     в {@link SecurityContextHolder}.</li>
 * </ul>
 *
 * <p>Использует {@link JwtService} для валидации токена и извлечения
 * имени пользователя, а также {@link UserServiceImpl} для загрузки
 * деталей пользователя.
 */

@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserServiceImpl userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Получаем токен из заголовка
        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Обрезаем префикс и получаем имя пользователя из токена
        var jwt = authHeader.substring(BEARER_PREFIX.length());
        var username = jwtService.extractUserName(jwt);

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService
                    .userDetailsService()
                    .loadUserByUsername(username);

            // Если токен валиден, то аутентифицируем пользователя
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}