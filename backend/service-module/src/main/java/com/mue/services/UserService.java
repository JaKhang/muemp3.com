package com.mue.services;

import com.mue.entities.PlayList;
import com.mue.entities.User;
import com.mue.payload.request.ActionRequest;
import com.mue.payload.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getAuthenticatedUser();

;

    void saveTracks(List<UUID> ids);

    void removeTracks(List<UUID> ids);

    void followAlbums(List<UUID> ids);

    void unfollowAlbums(List<UUID> ids);

    void followArtists(List<UUID> ids);

    void unfollowArtists(List<UUID> ids);

    void followPlaylists(List<UUID> ids);

    void unfollowPlaylists(List<UUID> ids);

}
