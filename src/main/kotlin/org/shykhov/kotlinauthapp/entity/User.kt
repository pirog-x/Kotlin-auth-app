package org.shykhov.kotlinauthapp.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "_user")
@NoArgsConstructor
data class User (
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    val id: Int,

    @NotBlank(message = "Firstname cannot be blank.")
    @Column(name = "first_name")
    val firstName: String,

    @NotBlank(message = "Lastname cannot be blank.")
    @Column(name = "last_name")
    val lastName: String,

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Column(name = "email")
    val email: String,

    @NotBlank
    @Min(8, message = "Password must be at least 8 characters.")
    @Column(name = "password")
    val passwd: String,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val tokens: List<Token>?

): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return passwd
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}