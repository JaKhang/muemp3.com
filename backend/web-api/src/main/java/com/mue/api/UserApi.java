package com.mue.api;

import com.mue.core.domain.ApiQuery;
import com.mue.core.domain.QueryRequest;
import com.mue.core.domain.RestBody;
import com.mue.core.exception.UnauthorizedException;
import com.mue.entities.PlayList;
import com.mue.entities.User;
import com.mue.payload.request.ActionRequest;
import com.mue.payload.response.*;
import com.mue.security.annotaton.IsAuthenticated;
import com.mue.security.domain.PrincipleResponse;
import com.mue.security.domain.UserPrincipal;
import com.mue.security.services.AuthService;
import com.mue.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;
    private final AuthService authService;
    private final TrackService trackService;
    private final PlaylistService playlistService;
    private final AlbumService albumService;
    private final ArtistService artistService;

    @GetMapping()
    @IsAuthenticated
    public ResponseEntity<RestBody<?>> getPrinciple(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal principal) {
            System.out.println(principal);
            PrincipleResponse principle = authService.getPrinciple(principal.getId());
            return ResponseEntity.ok(new RestBody<>(principle));
        } else {
            throw new UnauthorizedException();
        }

    }

    @PutMapping("/tracks")
    @IsAuthenticated
    public ResponseEntity<?> saveTrack(@RequestBody ActionRequest action) {
        userService.saveTracks(action.getIds());
        return ResponseEntity.ok(new RestBody<>("Successfully saved tracks", null));
    }

    @DeleteMapping("/tracks")
    @IsAuthenticated
    public ResponseEntity<?> removeTrack(@RequestParam List<UUID> ids) {
        userService.removeTracks(ids);
        return ResponseEntity.ok(new RestBody<>("Successfully removed tracks", null));
    }

    @GetMapping("/tracks")
    @IsAuthenticated
    public ResponseEntity<?> findAllTracks(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        InfiniteListResponse<TrackLineResponse> tracks = trackService.findAllByUserId(user.getId(), pageable);
        return ResponseEntity.ok(new RestBody<>(tracks));
    }

    @PutMapping("/albums")
    @IsAuthenticated
    public ResponseEntity<?> saveAlbum(@RequestBody ActionRequest action) {
        userService.followAlbums(action.getIds());
        return ResponseEntity.ok(new RestBody<>("Successfully followed albums", null));
    }

    @DeleteMapping("/albums")
    @IsAuthenticated
    public ResponseEntity<?> removeAlbum(@RequestParam List<UUID> ids) {
        userService.unfollowAlbums(ids);
        return ResponseEntity.ok(new RestBody<>("Successfully unfollowed albums", null));
    }

    @GetMapping("/albums")
    @IsAuthenticated
    public ResponseEntity<?> findAllAlbum(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        InfiniteListResponse<AlbumLineResponse> albums = albumService.findAllByUserId(user.getId(), pageable);
        return ResponseEntity.ok(new RestBody<>(albums));
    }


    @PutMapping("/playlists")
    @IsAuthenticated
    public ResponseEntity<?> savePlaylist(@RequestBody ActionRequest action) {
        userService.followPlaylists(action.getIds());
        return ResponseEntity.ok(new RestBody<>("Successfully followed playlists", null));
    }

    @DeleteMapping("/playlists")
    @IsAuthenticated
    public ResponseEntity<?> removePlaylist(@RequestParam List<UUID> ids) {
        userService.unfollowPlaylists(ids);
        return ResponseEntity.ok(new RestBody<>("Successfully unfollowed playlists", null));
    }

    @GetMapping("/playlists")
    @IsAuthenticated
    public ResponseEntity<?> findAllPlaylist(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        InfiniteListResponse<PlaylistResponse> playlists = playlistService.findAllByUserId(user.getId(), pageable);
        return ResponseEntity.ok(new RestBody<>(playlists));
    }

    @PutMapping("/artists")
    @IsAuthenticated
    public ResponseEntity<?> saveArtist(@RequestBody ActionRequest action) {
        userService.followArtists(action.getIds());
        return ResponseEntity.ok(new RestBody<>("Successfully followed artists", null));
    }

    @DeleteMapping("/artists")
    @IsAuthenticated
    public ResponseEntity<?> removeArtist(@RequestParam List<UUID> ids) {
        userService.unfollowArtists(ids);
        return ResponseEntity.ok(new RestBody<>("Successfully unfollowed artists", null));
    }

    @GetMapping("/artists")
    @IsAuthenticated
    public ResponseEntity<?> findAllArist(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        InfiniteListResponse<ArtistLineResponse> artists = artistService.findAllByUserId(user.getId(), pageable);
        return ResponseEntity.ok(new RestBody<>(artists));
    }

    @GetMapping("/playlists/owners")
    @IsAuthenticated
    public ResponseEntity<?> findAllArist(Pageable pageable,
                                          @RequestParam(defaultValue = "") String query) {
        List<ApiQuery> apiQueries = QueryRequest.from(query);
        User user = userService.getAuthenticatedUser();
        InfiniteListResponse<PlaylistResponse> playlists = playlistService.findAllByOwnerId(user.getId(), apiQueries, pageable);
        return ResponseEntity.ok(new RestBody<>(playlists));
    }


}
