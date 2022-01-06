package com.project.app.service.impl;

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
import com.project.app.specification.EbookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EbookServicesImpl implements EbookService {

    @Autowired
    EbookRepository repository;

    @Autowired
    EbookAuthorService ebookAuthorService;

    @Override
    public Ebook saveEbookToDB(EbookAPI ebookAPI) {
        //EBOOK API == EBOOK REQUEST
        //CREATE EBOOK MASUKIN DATANYA DARI EBOOK API
        Ebook ebook = new Ebook(
                ebookAPI.getEbookCode(),
                ebookAPI.getTitle(),
                ebookAPI.getPublishedDate(),
                ebookAPI.getPublisher(),
                ebookAPI.getDescription(),
                ebookAPI.getImageLinks(),
                ebookAPI.getWebReaderLink()
        );

        //SAVE EBOOK KE DB (KONDISI AUTHOR KOSONG MASIH)
        Ebook ebook1 = repository.save(ebook);

        //KARENA DARI API AUTHOR AK BENTUKNYA STRING[NAMA] JADI AKU LAKUKAN PERULANGAN UNTUK DAPATIN AUTHOR (STRING) DIDALAM ARRAY
        List<EbookAuthor> authorSet = new ArrayList<>();
        for (String strAuthor : ebookAPI.getAuthors()) {

            //CREATE AUTHOR ISINYA HANYA NAMA AUTHORNYA
            //KARENA AUTHOR DI TABLE AKU FIELDNY CUMA NAMA
            EbookAuthor author = new EbookAuthor(strAuthor);

            //SAVE KE DATABASE AUTHOR
            EbookAuthor ebookAuthor = ebookAuthorService.saveAuthor(author);

            //EBOOK YG SUDAH DISIMPAN DI DB TADI (KAN BELUM ADA LIST AUTHOR==0)
            //JADI AK GET AUTHORS(YANG KONDISINYA NULL), TAMBAHKAN AUTHORNY
            ebook1.getAuthors().add(ebookAuthor);

            //author.getEbooks().add(ebook1);
            //authorSet.add(author);
        }

        //TRS SDH DPT AUTHOR EBOOK, UPDATE EBOOK1
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
