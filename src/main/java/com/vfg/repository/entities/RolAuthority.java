package com.vfg.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * Entidad correspondiente a los roles que manejara la aplicacion.
 *
 * @author vifergo
 * @since v0.1
 *
 */
@Entity
@Table(name = "ROL")
public class RolAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 3904494309622185194L;

    /**
     * Prioridad minima.
     */
    public static final long PRIORITY_MIN = 0L;

    /**
     * Prioridad intermedia 50.
     */
    public static final long PRIORITY_50 = 50L;

    /**
     * Prioridad alta 90.
     */
    public static final long PRIORITY_90 = 90L;

    /**
     * Prioridad maxima.
     */
    public static final long PRIORITY_MAX = 100L;
    /**
     * Indentificador del Rol, primary key.
     */
    @Id
    @Column(name = "ID_ROL")
    private String authority;

    /**
     * Descripcion corta del rol.
     */
    private String description;

    /**
     * Prioridad.
     */
    @Column(nullable = false)
    private Long priority;

    /**
     * Constructor por defecto.
     */
    public RolAuthority() {
        super();
    }

    /**
     * Constructor.
     *
     * @param paramAuthority identificador del rol.
     * @param paramDescription descripcion del rol.
     * @param paramPriority prioridad del rol.
     */
    public RolAuthority(final String paramAuthority, final String paramDescription, final Long paramPriority) {
        super();
        this.authority = paramAuthority;
        this.description = paramDescription;
        this.priority = paramPriority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getAuthority() {
        return authority;
    }

    /**
     * @param paramAuthority identificador del rol.
     */
    public final void setAuthority(final String paramAuthority) {
        this.authority = paramAuthority;
    }

    /**
     * @return Long con la prioridad.
     */
    public final Long getPriority() {
        return priority;
    }

    /**
     * @param paramPriority prioridad del rol.
     */
    public final void setPriority(final Long paramPriority) {
        this.priority = paramPriority;
    }

    /**
     * @return String la descripcion del rol.
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param paramDescription la descripcion del rol.
     */
    public final void setDescription(final String paramDescription) {
        this.description = paramDescription;
    }

}
