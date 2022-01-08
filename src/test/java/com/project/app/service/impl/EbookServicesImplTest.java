package com.project.app.service.impl;

import com.project.app.dto.EbookDTO;
import com.project.app.dto.ebook.*;
import com.project.app.entity.Ebook;
import com.project.app.entity.EbookAuthor;
import com.project.app.repository.EbookRepository;
import com.project.app.request.EbookAPI;
import com.project.app.service.EbookAuthorService;
import com.project.app.specification.EbookSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EbookServicesImplTest {

    @InjectMocks
    EbookServicesImpl service;

    @Mock
    EbookRepository repository;

    @Mock
    EbookAuthorService ebookAuthorService;

    @Test
    public void whenEbookSavedSuccessfully_thenReturnEbook(){
        EbookAuthor ebookAuthor = mock(EbookAuthor.class);
        List<EbookAuthor> authorList = new ArrayList<>();
        authorList.add(ebookAuthor);

        Ebook ebook = new Ebook("E01", "API01", "How to use adobe photoshop?",
                authorList, "1993", "erlangga",
                "description", "imageLink",
                "webReaderLink");

        given(repository.save(any(Ebook.class))).willReturn(ebook);

        List<String> strings = new ArrayList<>();
        strings.add("jones");

        EbookAPI api = new EbookAPI(
                "API01", "How to use adobe photoshop?",
                strings, "1993", "erlangga",
                "description", "imageLink",
                "webReaderLink");

        Ebook ebook1 = service.saveEbookToDB(api);

        assertEquals(ebook, ebook1);
    }

    @Test
    public void whenDoSpecification_thenReturnEbookPage(){
        EbookDTO ebookDTO = mock(EbookDTO.class);
        Pageable pageable = PageRequest.of(0,5);

        Specification<Ebook> specification = EbookSpecification.getSpecification(ebookDTO);

        Page<Ebook> all = repository.findAll(specification, pageable);

        Page<Ebook> ebooks = service.getSavedEbooks(ebookDTO, pageable);

        assertThat(ebooks).isEqualTo(all);
    }

    @Test
    public void whenGetEbookByIdSuccess_thenReturnEbook(){
        EbookAuthor ebookAuthor = mock(EbookAuthor.class);
        List<EbookAuthor> authorList = new ArrayList<>();
        authorList.add(ebookAuthor);

        Ebook ebook = new Ebook("E01","API01", "How to use adobe photoshop?",
                authorList, "1993", "erlangga",
                "description", "imageLink",
                "webReaderLink");

        when(repository.findById(any(String.class))).thenReturn(Optional.of(ebook));

        //check
        Ebook actual = service.getSavedEbookById(ebook.getEbookId());

        assertEquals(ebook, actual);
    }

    @Test
    public void whenDeleteEbookSuccess_thenVerifyReturnSuccess(){

        EbookAuthor ebookAuthor = mock(EbookAuthor.class);
        List<EbookAuthor> authorList = new ArrayList<>();
        authorList.add(ebookAuthor);

        Ebook ebook = new Ebook("E01","API01", "How to use adobe photoshop?",
                authorList, "1993", "erlangga",
                "description", "imageLink",
                "webReaderLink");

        when(repository.findById(any(String.class))).thenReturn(Optional.of(ebook));

        service.deleteSavedEbook(ebook.getEbookId());

        //verify the repository.deleteById has been reached by calling service.delete(..).
        verify(repository, times(1)).deleteById(ebook.getEbookId());

    }

    @Test
    public void whenMapEbookByCodeFromAPI_thenMapTheModel(){
        AccessInfo accessInfo = mock(AccessInfo.class);
        ImageLinks imageLinks = mock(ImageLinks.class);

        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setImageLinks(imageLinks);

        Item item = new Item("I01", volumeInfo, accessInfo);

        EbookAPI ebookAPI = new EbookAPI(
                item.getId(),
                item.getVolumeInfo().title,
                item.getVolumeInfo().authors,
                item.getVolumeInfo().publishedDate,
                item.getVolumeInfo().publisher,
                item.getVolumeInfo().description,
                item.getVolumeInfo().imageLinks.thumbnail,
                item.getAccessInfo().webReaderLink
        );

        EbookAPI actual = service.mapEbookAPIByCode(item);

        assertEquals(ebookAPI.getEbookCode(), actual.getEbookCode());
    }

    @Test
    public void whenMapListEbook_thenReturnList(){
        AccessInfo accessInfo = mock(AccessInfo.class);
        ImageLinks imageLinks = mock(ImageLinks.class);

        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setImageLinks(imageLinks);

        Item item = new Item("I01", volumeInfo, accessInfo);

        List<Item> items = new ArrayList<>();

        items.add(item);

        Root root = new Root(items);

        List<EbookAPI> api = service.mapListEbookAPI(root);

        assertThat(api).hasSize(1);

    }

}