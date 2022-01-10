package com.project.app.service;


import com.project.app.dto.EbookDTO;
import com.project.app.dto.ebook.Item;
import com.project.app.dto.ebook.Root;
import com.project.app.entity.Ebook;
import com.project.app.request.EbookAPI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EbookService {

    List<EbookAPI> mapListEbookAPI(Root rootDTO);

    EbookAPI mapEbookAPIByCode(Item itemDTO);

    Ebook saveEbookToDB(EbookAPI ebook);

    Page<Ebook> getSavedEbooks(EbookDTO ebookDTO, Pageable pageable);

    Ebook getSavedEbookById(String id);

    void deleteSavedEbook(String id);


}
