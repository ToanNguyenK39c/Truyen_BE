package vn.com.truyen.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import vn.com.truyen.service.AuthorService;
import vn.com.truyen.service.dto.AuthorDTO;
import vn.com.truyen.service.mess.AuthorMess;
import vn.com.truyen.service.mess.TruyenMess;
import vn.com.truyen.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.com.truyen.domain.Author}.
 */
@RestController
@RequestMapping("/api")
public class AuthorResource {

	private final Logger log = LoggerFactory.getLogger(AuthorResource.class);

	private static final String ENTITY_NAME = "truyenAuthor";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final AuthorService authorService;

	public AuthorResource(AuthorService authorService) {
		this.authorService = authorService;
	}

	/**
	 * {@code POST  /authors} : Create a new author.
	 *
	 * @param authorDTO the authorDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new authorDTO, or with status {@code 400 (Bad Request)} if
	 *         the author has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/authors")
	public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) throws URISyntaxException {
		log.debug("REST request to save Author : {}", authorDTO);
		if (authorDTO.getId() != null) {
			throw new BadRequestAlertException("A new author cannot already have an ID", ENTITY_NAME, "idexists");
		}
		AuthorDTO result = authorService.save(authorDTO);
		return ResponseEntity
				.created(new URI("/api/authors/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /authors} : Updates an existing author.
	 *
	 * @param authorDTO the authorDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated authorDTO, or with status {@code 400 (Bad Request)} if
	 *         the authorDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the authorDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/authors")
	public ResponseEntity<AuthorDTO> updateAuthor(@Valid @RequestBody AuthorDTO authorDTO) throws URISyntaxException {
		log.debug("REST request to update Author : {}", authorDTO);
		if (authorDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AuthorDTO result = authorService.save(authorDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authorDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /authors} : get all the authors.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of authors in body.
	 */
	@GetMapping("/authors")
	public ResponseEntity<AuthorMess> getAllAuthors(
			@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, 
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "ASC") String sortType, 
			@RequestParam(defaultValue = "name") String sortBy) {
		log.debug("REST request to get Authors by {}: ", name);

		AuthorMess authorMess = authorService.getAllAuthors(pageNo, pageSize, name, sortType, sortBy);

		return new ResponseEntity<AuthorMess>(authorMess, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * {@code GET  /authors/:id} : get the "id" author.
	 *
	 * @param id the id of the authorDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the authorDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/authors/{id}")
	public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {
		log.debug("REST request to get Author : {}", id);
		Optional<AuthorDTO> authorDTO = authorService.findOne(id);
		return ResponseUtil.wrapOrNotFound(authorDTO);
	}

	/**
	 * {@code GET  /authors/:id} : get all truyen the "id" author.
	 *
	 * @param id the id of the truyenMess to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the truyenMess, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/authors/{id}/truyens")
	public ResponseEntity<TruyenMess> getAllTruyenByAuthorId(
			@PathVariable Long id,
			@RequestParam(defaultValue = "1") Integer pageNo, 
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "") String name, 
			@RequestParam(defaultValue = "name") String sortBy) {
		log.debug("REST request to get all truyen by Author id : {}", id);

		TruyenMess truyenMess = authorService.getAllTruyenOfAuthorId(pageNo, pageSize, id, name, sortBy);

		return new ResponseEntity<TruyenMess>(truyenMess, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * {@code DELETE  /authors/:id} : delete the "id" author.
	 *
	 * @param id the id of the authorDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		log.debug("REST request to delete Author : {}", id);
		authorService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
