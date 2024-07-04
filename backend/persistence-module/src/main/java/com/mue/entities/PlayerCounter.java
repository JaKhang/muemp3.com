package com.mue.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "player_counters")
@Getter
@Setter
public class PlayerCounter extends AbstractEntity {

    @Id
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Track track;

    private Integer counter;

    @Override
    public String getName() {
        return track.getName();
    }
}
