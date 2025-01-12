package vn.khanhduc.backendservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_has_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupHasUser extends AbstractEntity<Integer> {

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
