package com.vfg.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * Entidad correspondiente a los roles que manejara la aplicacion
 * 
 * @author vifergo
 * @since v0.1
 * 
 */
@Entity
@Table(name="ROL")
public class RolAuthority implements GrantedAuthority{

    private static final long serialVersionUID = 3904494309622185194L;

    @Id
    @Column(name="ID_ROL")
    private String authority;

    private String description;
    
    private Long priority;
    
    public RolAuthority() {
    	super();
    }
    
    public RolAuthority(String authority, String description, Long priority) {
    	super();
    	this.authority=authority;
    	this.description=description;
    	this.priority=priority;
    }
    
	@Override
    public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
	    this.authority = authority;
    }

	public Long getPriority() {
	    return priority;
    }

	public void setPriority(Long priority) {
	    this.priority = priority;
    }

	public String getDescription() {
	    return description;
    }

	public void setDescription(String description) {
	    this.description = description;
    }

}
