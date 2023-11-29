package com.ja.muemp3.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "player_counters")
@Getter
@Setter
public class PlayerCounter extends Auditable{

    @Id
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Song song;

    private Integer counter;

}
