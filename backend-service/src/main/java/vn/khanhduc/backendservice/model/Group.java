package vn.khanhduc.backendservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group extends AbstractEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
