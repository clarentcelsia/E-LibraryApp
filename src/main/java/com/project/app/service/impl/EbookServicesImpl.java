package com.project.app.service.impl;

import com.project.app.dto.EbookDTO;
import com.project.app.dto.ebook.Item;
import com.project.app.dto.ebook.Root;
import com.project.app.entity.Ebook;
import com.project.app.entity.EbookAuthor;
import com.project.app.entity.User;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.EbookRepository;
import com.project.app.request.EbookAPI;
import com.project.app.service.EbookAuthorService;
import com.project.app.service.EbookService;
import com.project.app.service.UserService;
import com.project.app.specification.EbookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.project.app.utils.Utility.RESPONSE_NOT_FOUND;

@Service
@Transactional
public class EbookServicesImpl implements EbookService {

    @Autowired
    EbookRepository repository;

    @Autowired
    EbookAuthorService ebookAuthorService;

    @Autowired
    UserService userService;

    @Override
    public Ebook saveEbookToDB(EbookAPI ebookAPI) {
        Ebook ebook = new Ebook(
                ebookAPI.getEbookCode(),
                ebookAPI.getTitle(),
                ebookAPI.getPublishedDate(),
                ebookAPI.getPublisher(),
                ebookAPI.getDescription(),
                ebookAPI.getImageLinks(),
                ebookAPI.getWebReaderLink()
        );

        Ebook ebook1 = repository.save(ebook);

        for (String strAuthor : ebookAPI.getAuthors()) {

            EbookAuthor author = new EbookAuthor(strAuthor);

            EbookAuthor ebookAuthor = ebookAuthorService.saveAuthor(author);

            ebook1.getAuthors().add(ebookAuthor);
        }

        for(User user : ebookAPI.getUser()){
            User service = userService.getById(user.getId());
            ebook1.getUser().add(service);
        }

        Ebook save = repository.save(ebook1);
        return save;
    }

    @Override
    public Page<Ebook> getSavedEbooks(EbookDTO ebookDTO, Pageable pageable) {
        Specification<Ebook> ebookSpecification = EbookSpecification.getSpecification(ebookDTO);
        return repository.findAll(ebookSpecification, pageable);
    }

    @Override
    public Ebook getSavedEbookById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(RESPONSE_NOT_FOUND, id))
        );
    }

    @Override
    public void deleteSavedEbook(String id) {
        getSavedEbookById(id);
        repository.deleteById(id);
    }

    @Override
    public EbookAPI mapEbookAPIByCode(Item ebooks) {
        EbookAPI ebook = new EbookAPI(
                ebooks.getId(),
                ebooks.getVolumeInfo().title,
                ebooks.getVolumeInfo().authors,
                ebooks.getVolumeInfo().publishedDate,
                ebooks.getVolumeInfo().publisher,
                ebooks.getVolumeInfo().description,
                ebooks.getVolumeInfo().imageLinks.thumbnail,
                ebooks.getAccessInfo().webReaderLink
        );
        return ebook;
    }

    @Override
    public List<EbookAPI> mapListEbookAPI(Root rootDTO) {
        List<EbookAPI> apiLists = new ArrayList<>();
        for (Item item : rootDTO.getItems()) {
            EbookAPI ebook = mapEbookAPIByCode(item);
            apiLists.add(ebook);
        }
        return apiLists;
    }


}
