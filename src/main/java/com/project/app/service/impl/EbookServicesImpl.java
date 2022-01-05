package com.project.app.service.impl;

import com.project.app.dto.EbookAuthorDTO;
import com.project.app.dto.EbookDTO;
import com.project.app.dto.ebook.Item;
import com.project.app.dto.ebook.Root;
import com.project.app.entity.Ebook;
import com.project.app.entity.EbookAuthor;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.EbookRepository;
import com.project.app.request.EbookAPI;
import com.project.app.service.EbookAuthorService;
import com.project.app.service.EbookService;
import com.project.app.specification.EbookAuthorSpecification;
import com.project.app.specification.EbookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EbookServicesImpl implements EbookService {

    @Autowired
    EbookRepository repository;

    @Autowired
    EbookAuthorService ebookAuthorService;

    @Override
    public Ebook saveEbookToDB(EbookAPI ebookAPI) {
        // Create ebook
        Ebook ebook = new Ebook(
                ebookAPI.getEbookCode(),
                ebookAPI.getTitle(),
                ebookAPI.getPublishedDate(),
                ebookAPI.getPublisher(),
                ebookAPI.getDescription(),
                ebookAPI.getImageLinks(),
                ebookAPI.getWebReaderLink()
        );
        repository.save(ebook);

        //Get authors
        Set<EbookAuthor> authorSet = new HashSet<>();
        for (String strAuthor : ebookAPI.getAuthors()) {
            EbookAuthor author = new EbookAuthor(); //a1
            author.setName(strAuthor); //n1
            ebookAuthorService.saveAuthor(author); //1

            System.out.println(author);
//            author.getEbooks().add(ebook);

            authorSet.add(author);
        }

//        ebook.getAuthors().add(list);

        ebook.setAuthors(authorSet);
        return ebook;
    }

    @Override
    public Page<Ebook> getSavedEbooks(Pageable pageable) {

        return repository.findAll(pageable);
    }

    @Override
    public Page<Ebook> getSavedEbookByTitle(EbookDTO ebookDTO, Pageable pageable) {
        Specification<Ebook> ebookSpecification = EbookSpecification.getSpecification(ebookDTO);
        return repository.findAll(ebookSpecification, pageable);
    }

    @Override
    public Page<EbookAuthor> getSavedEbookByAuthor(EbookAuthorDTO ebookAuthorDTO, Pageable pageable) {
        Specification<EbookAuthor> ebookSpecification = EbookAuthorSpecification.getSpecification(ebookAuthorDTO);
        return ebookAuthorService.filter(ebookSpecification, pageable);
    }

    @Override
    public Ebook getSavedEbookById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("NotFoundException: Ebook with id " + id + " not found")
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
