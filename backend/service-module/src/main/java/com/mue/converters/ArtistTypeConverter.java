package com.mue.converters;



import com.mue.entities.ArtistType;
import com.mue.payload.response.ArtistTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class ArtistTypeConverter implements LineConverter<ArtistTypeResponse, ArtistType> {

    @Override
    public ArtistTypeResponse convertToLine(ArtistType artist) {
        ArtistTypeResponse artistTypeResponse = new ArtistTypeResponse();
        artistTypeResponse.setName(artist.getName());
        artistTypeResponse.setId(artist.getId());
        return artistTypeResponse;
    }
}
