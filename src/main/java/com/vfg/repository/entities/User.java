package com.vfg.repository.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entidad correspondiente a la tabla de usuarios.
 *
 * @author vifergo
 * @since v0.1
 */
@Entity
@Table(name = "USER")
public class User implements UserDetails {

    private static final long serialVersionUID = -390340789103567235L;

    /**
     * Identificador del usuario, clave primaria autogenerada.
     */
    @Id
    @GeneratedValue
    @Column(name = "ID_USER")
    private Long idUser;

    /**
     * Nombre de usuario. Valor unico no nulo.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Contraseña de usuario. No nulo y encriptado.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Indicador de cuenta expirada.
     */
    private boolean accountNonExpired;

    /**
     * Indicador de cuenta bloqueada.
     */
    private boolean accountNonLocked;

    /**
     * Indicador de credenciales expiradas.
     */
    private boolean credentialsNonExpired;

    /**
     * Indicador de cuenta bloqueada.
     */
    private boolean enabled;

    /**
     * Tenemos que cargarlo como EAGER porque al ser usado por spring security
     * necesitamos que los roles se carguen directamente.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROL",
                joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID_USER"),
                inverseJoinColumns = @JoinColumn(name = "ROL_ID", referencedColumnName = "ID_ROL"))
    private Collection<RolAuthority> authorities;

    /**
     * Constructor por defecto.
     */
    public User() {
        super();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.enabled = false;
    }

    /**
     * Constructor con campos minimos.
     * @param paramUsername nombre de usuario.
     * @param paramPassword contraseña de usuario.
     * @param paramAuthorities roles del usuario.
     */
    public User(final String paramUsername,
                final String paramPassword,
                final Collection<RolAuthority> paramAuthorities) {
        this.username = paramUsername;
        this.password = paramPassword;
        this.authorities = paramAuthorities;
        this.accountNonExpired = false;
        this.accountNonLocked = false;
        this.enabled = false;
    }

    /**
     * @return identificador del usuario.
     */
    public final Long getIdUser() {
        return idUser;
    }

    /**
     * @param paramIdUser identificador del usuario.
     */
    public final void setIdUser(final Long paramIdUser) {
        this.idUser = paramIdUser;
    }

    /**
     * @return El nombre de usuario.
     */
    public final String getUsername() {
        return username;
    }

    /**
     * @param paramUsername el nombre de usuario.
     */
    public final void setUsername(final String paramUsername) {
        this.username = paramUsername;
    }

    /**
     * @return la contraseña encriptada del usuario.
     */
    public final String getPassword() {
        return password;
    }

    /**
     * @param paramPassword la contraseña encriptada del usuario
     */
    public final void setPassword(final String paramPassword) {
        this.password = paramPassword;
    }

    /**
     * @return true si la cuenta no expiro.
     */
    public final boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * @param isAcountNonExpired true si la cuenta no expiro
     */
    public final void setAccountNonExpired(final boolean isAcountNonExpired) {
        this.accountNonExpired = isAcountNonExpired;
    }

    /**
     * @return true si la cuenta no esta bloqueada.
     */
    public final boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * @param isAccountNonLocked true si la cuenta no esta bloqueda.
     */
    public final void setAccountNonLocked(final boolean isAccountNonLocked) {
        this.accountNonLocked = isAccountNonLocked;
    }

    /**
     * @return true si los credenciales no han expirados.
     */
    public final boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * @param isCredentialsNonExpired true si los credenciales no han expirado.
     */
    public final void setCredentialsNonExpired(final boolean isCredentialsNonExpired) {
        this.credentialsNonExpired = isCredentialsNonExpired;
    }

    /**
     * @return true si la cuenta esta habilitada.
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * @param isEnabled true si la cuenta esta habilitada.
     */
    public final void setEnabled(final boolean isEnabled) {
        this.enabled = isEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
