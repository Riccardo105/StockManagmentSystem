package org.example.controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.example.ProductType;
import org.example.config.GuiceConfig;
import org.example.config.ObjectCreationException;
import org.example.model.DAO.products.*;
import org.example.model.DTO.products.*;
import org.example.model.Service.ProductSearchService;
import org.example.model.Service.products.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashBoardController {

    private final ProductSearchService productSearchService;
    private final Injector injector = Guice.createInjector(new GuiceConfig());

    @Inject
    public DashBoardController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    public ArrayList<ProductDTO> handleProductSearch(ProductType.Category category, ProductType subCategory, String searchText) {
        ArrayList<ProductDTO> results = new ArrayList<>();
        try {
            switch (category) {
                case BOOK: {
                    results = productSearchService.QueryBooks(subCategory, searchText);
                    break;
                }
                case MUSIC: {
                    results = productSearchService.QueryMusic(subCategory, searchText);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error during product search: " + e.getMessage());
        }
        return results;
    }

    /**
     *
     * @param productType given by "create new" menu in DashboardView
     * @param FormData gathered in the Product form
     * @return ErrorMap, this is the map of any error that surface from the service. map is also return when returned empty (no errors)
     */
    public Map<String, String> handleCreateProduct(ProductType productType, Map<String, Object> FormData) {
        Map<String, String> errorMap = new HashMap<>();

        switch (productType) {
            case ProductType.AudioBook:
                AudioBookService audioBookService = new AudioBookService();
                try{
                    AudioBookDTO audioBookDTO = audioBookService.createService(FormData);
                    AudioBookDAO audioBookDAO = injector.getInstance(AudioBookDAO.class);
                    audioBookDAO.create(audioBookDTO);
                    return errorMap;
                } catch (ObjectCreationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
            case ProductType.Ebook:
                EbookService ebookService = new EbookService();
                try{
                    EBookDTO eBookDTO = ebookService.createService(FormData);
                    EBookDAO eBookDAO = injector.getInstance(EBookDAO.class);
                    eBookDAO.create(eBookDTO);
                    return errorMap;
                } catch (ObjectCreationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation : " + e.getMessage());

                }
                break;
            case ProductType.PaperBook:
                PaperBookService PaperBookService = new PaperBookService();
                try{
                    PaperBookDTO paperBookDTO = PaperBookService.createService(FormData);
                    PaperBookDAO paperBookDAO = injector.getInstance(PaperBookDAO.class);
                    paperBookDAO.create(paperBookDTO);
                    return errorMap;
                } catch (ObjectCreationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
            case ProductType.Cd:
                CdService CdService = new CdService();
                try{
                    CdDTO CdDTO = CdService.createService(FormData);
                    CdDAO cdDAO = injector.getInstance(CdDAO.class);
                    cdDAO.create(CdDTO);
                    return errorMap;
                } catch (ObjectCreationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
            case ProductType.Digital:
                DigitalService DigitalService = new DigitalService();
                try{
                    DigitalDTO DigitalDTO = DigitalService.createService(FormData);
                    DigitalDAO digitalDAO = injector.getInstance(DigitalDAO.class);
                    digitalDAO.create(DigitalDTO);
                    return errorMap;
                } catch (ObjectCreationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
            case ProductType.Vinyl:
                VinylService vinylService = new VinylService();
                try{
                    VinylDTO vinylDTO = vinylService.createService(FormData);
                    VinylDAO vinylDAO = injector.getInstance(VinylDAO.class);
                    vinylDAO.create(vinylDTO);
                    return errorMap;
                } catch (ObjectCreationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
        }
        return errorMap;
    }
}
