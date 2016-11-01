package cz.fai.blog.manager;

import cz.fai.blog.dto.AuthorDto;
import java.util.List;

/**
 *
 * @author Frantisek Spacek
 */
public interface AuthorManager {
    
    List<AuthorDto> getAuthors();
    
    AuthorDto createAuthor(AuthorDto authorDto);

    AuthorDto findAuthorByEmailAndPassword(String email, String pass);
}
