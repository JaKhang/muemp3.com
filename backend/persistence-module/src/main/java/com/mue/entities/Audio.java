package com.mue.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.UUID;

@Entity
@Table(name = "Audios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audio extends AbstractEntity {
    @Id
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "audio_file_id")
    private FileMetadata src;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Track track;


    @Override
    public String getName() {
        return track.getName();
    }
}
