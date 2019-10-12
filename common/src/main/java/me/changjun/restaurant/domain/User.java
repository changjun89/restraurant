package me.changjun.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    private String password;

    private int level = 1;

    public boolean isAdmin() {
        return level > 3;
    }

    public User updateInfo(String email, String name, int level) {
        this.email = email;
        this.name = name;
        this.level = level;
        return this;
    }

    public User deActive() {
        this.level = 0;
        return this;
    }

    public boolean isActive() {
        return level > 0;
    }

}
