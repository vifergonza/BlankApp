package com.vfg.repository;

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
 * Entidad correspondiente a la tabla de usuarios
 * 
 * @author vifergo
 * @since v0.1
 */
@Entity
@Table(name="USER")
public class User implements UserDetails {

    private static final long serialVersionUID = -390340789103567235L;

    @Id
    @GeneratedValue 
    @Column(name="ID_USER")
    private Long idUser;
    
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    
    /**
     * Tenemos que cargarlo como EAGER porque al ser usado por spring
     * security necesitamos que los roles se carguen directamente.
     */
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="USER_ROL",
        joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID_USER"),
        inverseJoinColumns=@JoinColumn(name="ROL_ID", referencedColumnName="ID_ROL"))
    private Collection<RolAuthority> authorities;
    
    public User(){
    	super();
    	this.accountNonExpired=false;
    	this.accountNonLocked=false;
    	this.enabled=false;
    }
    
    public User(String username, String password, Collection<RolAuthority> authorities){
    	this.username=username;
    	this.password=password;
    	this.authorities=authorities;
    	this.accountNonExpired=false;
    	this.accountNonLocked=false;
    	this.enabled=false;
    }
    
    public String getUsername() {
		return username;
	}
    
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	
	public void setAccountNonExpired(boolean acountNonExpired) {
		this.accountNonExpired = acountNonExpired;
	}
	
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

}
