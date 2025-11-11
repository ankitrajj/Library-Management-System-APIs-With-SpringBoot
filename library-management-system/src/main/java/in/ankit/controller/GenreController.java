package in.ankit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import in.ankit.dto.Genre;
import in.ankit.service.GenreService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre) {
        Genre savedGenre = genreService.createGenre(genre);
        return ResponseEntity
                .created(URI.create(ServletUriComponentsBuilder.fromCurrentRequest().build().toString() + savedGenre.getId()))
                .body(savedGenre);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id){

        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<Genre> findGenreByID(@PathVariable Long id) {

        Genre genre = genreService.findGenreById(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

}
