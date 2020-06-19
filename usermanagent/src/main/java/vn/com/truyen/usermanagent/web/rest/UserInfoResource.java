package vn.com.truyen.usermanagent.web.rest;

import vn.com.truyen.usermanagent.service.UserInfoService;
import vn.com.truyen.usermanagent.web.rest.errors.BadRequestAlertException;
import vn.com.truyen.usermanagent.service.dto.UserInfoDTO;
import vn.com.truyen.usermanagent.service.dto.UserInfoCriteria;
import vn.com.truyen.usermanagent.service.UserInfoQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link vn.com.truyen.usermanagent.domain.UserInfo}.
 */
@RestController
@RequestMapping("/api")
public class UserInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserInfoResource.class);

    private static final String ENTITY_NAME = "usermanagentUserInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserInfoService userInfoService;

    private final UserInfoQueryService userInfoQueryService;

    public UserInfoResource(UserInfoService userInfoService, UserInfoQueryService userInfoQueryService) {
        this.userInfoService = userInfoService;
        this.userInfoQueryService = userInfoQueryService;
    }

    /**
     * {@code POST  /user-infos} : Create a new userInfo.
     *
     * @param userInfoDTO the userInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userInfoDTO, or with status {@code 400 (Bad Request)} if the userInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-infos")
    public ResponseEntity<UserInfoDTO> createUserInfo(@Valid @RequestBody UserInfoDTO userInfoDTO) throws URISyntaxException {
        log.debug("REST request to save UserInfo : {}", userInfoDTO);
        if (userInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new userInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserInfoDTO result = userInfoService.save(userInfoDTO);
        return ResponseEntity.created(new URI("/api/user-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-infos} : Updates an existing userInfo.
     *
     * @param userInfoDTO the userInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInfoDTO,
     * or with status {@code 400 (Bad Request)} if the userInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-infos")
    public ResponseEntity<UserInfoDTO> updateUserInfo(@Valid @RequestBody UserInfoDTO userInfoDTO) throws URISyntaxException {
        log.debug("REST request to update UserInfo : {}", userInfoDTO);
        if (userInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserInfoDTO result = userInfoService.save(userInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-infos} : get all the userInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userInfos in body.
     */
    @GetMapping("/user-infos")
    public ResponseEntity<List<UserInfoDTO>> getAllUserInfos(UserInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserInfos by criteria: {}", criteria);
        Page<UserInfoDTO> page = userInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-infos/count} : count all the userInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-infos/count")
    public ResponseEntity<Long> countUserInfos(UserInfoCriteria criteria) {
        log.debug("REST request to count UserInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(userInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-infos/:id} : get the "id" userInfo.
     *
     * @param id the id of the userInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-infos/{id}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long id) {
        log.debug("REST request to get UserInfo : {}", id);
        Optional<UserInfoDTO> userInfoDTO = userInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userInfoDTO);
    }

    /**
     * {@code DELETE  /user-infos/:id} : delete the "id" userInfo.
     *
     * @param id the id of the userInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-infos/{id}")
    public ResponseEntity<Void> deleteUserInfo(@PathVariable Long id) {
        log.debug("REST request to delete UserInfo : {}", id);
        userInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}