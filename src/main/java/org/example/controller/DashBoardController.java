package org.example.controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.example.ProductType;
import org.example.config.DbConnection;
import org.example.config.GuiceConfig;
import org.example.config.ObjectValidationException;
import org.example.model.DAO.products.*;
import org.example.model.DTO.products.*;
import org.example.model.Service.ObjectFileWriter;
import org.example.model.Service.ProductSearchService;
import org.example.model.Service.products.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.*;

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
     * @param productType given by "create new" menu in DashboardView
     * @param FormData    gathered in the Product form
     * @return ErrorMap, this is the map of any error that surface from the service. map is also return when returned empty (no errors)
     */
    public Map<String, String> handleCreateProduct(ProductType productType, Map<String, Object> FormData) {
        Map<String, String> errorMap = new HashMap<>();

        switch (productType) {
            case ProductType.AudioBook:
                AudioBookService audioBookService = new AudioBookService();
                try {
                    AudioBookDTO audioBookDTO = audioBookService.createService(FormData);
                    AudioBookDAO audioBookDAO = injector.getInstance(AudioBookDAO.class);
                    audioBookDAO.create(audioBookDTO);
                    return errorMap;
                } catch (ObjectValidationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
            case ProductType.Ebook:
                EbookService ebookService = new EbookService();
                try {
                    EBookDTO eBookDTO = ebookService.createService(FormData);
                    EBookDAO eBookDAO = injector.getInstance(EBookDAO.class);
                    eBookDAO.create(eBookDTO);
                    return errorMap;
                } catch (ObjectValidationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation : " + e.getMessage());

                }
                break;
            case ProductType.PaperBook:
                PaperBookService PaperBookService = new PaperBookService();
                try {
                    PaperBookDTO paperBookDTO = PaperBookService.createService(FormData);
                    PaperBookDAO paperBookDAO = injector.getInstance(PaperBookDAO.class);
                    paperBookDAO.create(paperBookDTO);
                    return errorMap;
                } catch (ObjectValidationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
            case ProductType.Cd:
                CdService CdService = new CdService();
                try {
                    CdDTO CdDTO = CdService.createService(FormData);
                    CdDAO cdDAO = injector.getInstance(CdDAO.class);
                    cdDAO.create(CdDTO);
                    return errorMap;
                } catch (ObjectValidationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
            case ProductType.Digital:
                DigitalService DigitalService = new DigitalService();
                try {
                    DigitalDTO DigitalDTO = DigitalService.createService(FormData);
                    DigitalDAO digitalDAO = injector.getInstance(DigitalDAO.class);
                    digitalDAO.create(DigitalDTO);
                    return errorMap;
                } catch (ObjectValidationException e) {
                    errorMap = e.getErrorMap();
                } catch (RuntimeException e) {
                    System.err.println("Error during product creation: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Error during product creation: " + e.getMessage());
                }
                break;
            case ProductType.Vinyl:
                VinylService vinylService = new VinylService();
                try {
                    VinylDTO vinylDTO = vinylService.createService(FormData);
                    VinylDAO vinylDAO = injector.getInstance(VinylDAO.class);
                    vinylDAO.create(vinylDTO);
                    return errorMap;
                } catch (ObjectValidationException e) {
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

    /**
     * @param productsToUpdate list of products to update from DashboardView
     * @return the Set of invalidated products Ids (by service layer)
     */
    public Set<Integer> handleUpdateProduct(List<ProductDTO> productsToUpdate) {
        Set<Integer> invalidProductsIds = new HashSet<>();

        for (ProductDTO productDTO : productsToUpdate) {
            if (productDTO instanceof EBookDTO) {
                EbookService ebookService = new EbookService();
                EBookDAO eBookDAO = injector.getInstance(EBookDAO.class);
                eBookDAO.update((EBookDTO) productDTO);
                try {
                    ebookService.updateService(productDTO);
                } catch (ObjectValidationException e) {
                    invalidProductsIds.add(productDTO.getId());
                }

            }
            if (productDTO instanceof PaperBookDTO) {
                PaperBookService PaperBookService = new PaperBookService();
                PaperBookDAO paperBookDAO = injector.getInstance(PaperBookDAO.class);
                paperBookDAO.update((PaperBookDTO) productDTO);
                try {
                    PaperBookService.updateService(productDTO);
                } catch (ObjectValidationException e) {
                    invalidProductsIds.add(productDTO.getId());
                }
            }
            if (productDTO instanceof AudioBookDTO) {
                AudioBookService audioBookService = new AudioBookService();
                AudioBookDAO audioBookDAO = injector.getInstance(AudioBookDAO.class);
                audioBookDAO.update((AudioBookDTO) productDTO);
                try {
                    audioBookService.updateService(productDTO);
                } catch (ObjectValidationException e) {
                    invalidProductsIds.add(productDTO.getId());
                }
            }
            if (productDTO instanceof CdDTO) {
                CdService CdService = new CdService();
                CdDAO cdDAO = injector.getInstance(CdDAO.class);
                cdDAO.update((CdDTO) productDTO);
                try {
                    CdService.updateService(productDTO);
                } catch (ObjectValidationException e) {
                    invalidProductsIds.add(productDTO.getId());
                }
            }
            if (productDTO instanceof DigitalDTO) {
                DigitalService DigitalService = new DigitalService();
                DigitalDAO digitalDAO = injector.getInstance(DigitalDAO.class);
                digitalDAO.update((DigitalDTO) productDTO);
                try {
                    DigitalService.updateService(productDTO);
                } catch (ObjectValidationException e) {
                    invalidProductsIds.add(productDTO.getId());
                }
            }
            if (productDTO instanceof VinylDTO) {
                VinylService vinylService = new VinylService();
                VinylDAO vinylDAO = injector.getInstance(VinylDAO.class);
                vinylDAO.update((VinylDTO) productDTO);
                try {
                    vinylService.updateService(productDTO);
                } catch (ObjectValidationException e) {
                    invalidProductsIds.add(productDTO.getId());
                }
            }
        }
        return invalidProductsIds;
    }

    public void handleStockReport(){
        String hql = " FROM ProductDTO";
        Session session = DbConnection.getSession();
        Query<ProductDTO> query = session.createQuery(hql, ProductDTO.class);
        List<ProductDTO> products = query.getResultList();
        try {
            ObjectFileWriter.writeStockReportToFile(products);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
