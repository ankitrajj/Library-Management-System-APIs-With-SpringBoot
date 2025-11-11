package in.ankit.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.ankit.dto.Genre;
import in.ankit.entity.GenreEntity;
import in.ankit.exceptions.IdNotFoundException;
import in.ankit.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GenreService(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    public List<Genre> getAllGenres() {
        Iterable<GenreEntity> genreEntities = genreRepository.findAll();
        List<GenreEntity> genreEntityList = new ArrayList<>();
        genreEntities.forEach(genreEntityList::add);
        return genreEntityList.stream().map(genre -> modelMapper.map(genre, Genre.class)).toList();
    }

    public Genre createGenre(Genre genre) {
        GenreEntity genreEntity= modelMapper.map(genre, GenreEntity.class);
        GenreEntity savedGenre = genreRepository.save(genreEntity);
        return modelMapper.map(savedGenre, Genre.class);
    }


    public void updateGenre(GenreEntity genre) {
        genreRepository.save(genre);
    }

    public Genre findGenreById(Long id){
        GenreEntity foundGenre = genreRepository.findById(id).orElseThrow(() -> new IdNotFoundException("genre", id));
        return modelMapper.map(foundGenre, Genre.class);
    }

    public void deleteGenre(Long id){
        GenreEntity foundGenre = genreRepository.findById(id).orElseThrow(() -> new IdNotFoundException("genre", id));
        genreRepository.deleteById(foundGenre.getId());
    }

}
