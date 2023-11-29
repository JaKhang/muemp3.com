package com.ja.muemp3.entities;

import com.ja.muemp3.entities.constants.StorageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Audios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audio extends Auditable{
    @Id
    private UUID id;
    private String resourceId;
    private Long size;
    private String name;
    private StorageType provider;


    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Song song;
    


}
