package com.example.backend_for_gps_tracker_android.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "roles")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя роли.
     * <p>
     * Обязательное поле, должно быть уникальным в таблице ролей.
     * </p>
     */
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    /**
     * Связанные пользователи.
     * <p>
     * Связь "многие ко многим" с сущностью {@link User}.
     * Указывает на пользователей, которые имеют данную роль.
     * Это поле игнорируется при сериализации в формате JSON.
     * </p>
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;

    /**
     * Возвращает имя роли в формате, используемом Spring Security.
     * <p>
     * Метод также логирует информацию о вызове.
     * </p>
     *
     * @return строка, представляющая авторитет роли.
     */
    @Override
    public String getAuthority() {
        log.info("[#getAuthority()] is calling for role: {}", this.roleName);
        return this.roleName.toUpperCase();
    }
}