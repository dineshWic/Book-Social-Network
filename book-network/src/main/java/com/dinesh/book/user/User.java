package com.dinesh.book.user;

import com.dinesh.book.book.Book;
import com.dinesh.book.history.BookTransactionHistory;
import com.dinesh.book.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    @NotNull(message = "First Name should not be Null")
    @NotEmpty(message = "First Name should not be Empty")
//    @Size(min = 2, message = "First Name should be more than three characters")
    private String firstname;

    @Column(name = "last_name")
    @NotNull(message = "Last Name should not be Null")
    @NotEmpty(message = "Last Name should not be Empty")
//    @Size(min = 2, message = "Last Name should be more than three characters")
    private String lastname;

    @Column(name = "date_of_birth")
//    @NotNull(message = "Date of Birth should not be Null")
//    @NotEmpty(message = "Date of Birth should not be Empty")
    private LocalDate dateOfBirth;

    @Column(unique = true)
    @Email
    @NotNull(message = "Email should not be Null")
    @NotEmpty(message = "Email should not be Empty")
    private String email;

    @Column(name = "password")
    @NotNull(message = "Password should not be Null")
    @NotEmpty(message = "Password should not be Empty")
    @Size(min = 6, message = "Password should be more than six characters")
    private String password;

    @Column(name = "accouth_locked")
    private boolean accountLocked;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @OneToMany(mappedBy = "user")
    private List<BookTransactionHistory> histories;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;


    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.roles
                .stream()
                .map(r-> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String fullName() {
        return getFirstname() + " " + getLastname();
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}
