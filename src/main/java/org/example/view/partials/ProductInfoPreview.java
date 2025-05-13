package org.example.view.partials;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.example.controller.DashBoardController;
import org.example.model.DTO.products.ProductDTO;
import org.example.view.DashboardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ProductInfoPreview extends VBox {

    /**
     * observable list used to dynamically update ui once change in list is detected
     */
    private final  ObservableList<ProductDTO> productResult = FXCollections.observableArrayList();
    private final List<ProductDTO> productsToUpdate = new ArrayList<>();
    private final TableView<ProductDTO> productsTable;
    private Set<Integer> invalidProductsIds;

    // this controls the triggering of feedback for save actions in the setRowFactory
    Boolean lastActionWasSaved = false;

    @SuppressWarnings("unchecked")
    public ProductInfoPreview() {

        productsTable = new TableView<>(this.productResult);
        productsTable.setEditable(true);

        TableColumn<ProductDTO, String> titleCol = createTitleCol();
        TableColumn<ProductDTO, String> formatCol = new TableColumn<>("Format");
        formatCol.setCellValueFactory(new PropertyValueFactory<>("format"));
        TableColumn<ProductDTO, Integer> stockCol = createStockColumn();
        TableColumn<ProductDTO, Float> buyingPriceCol = createBuyingPriceColumn();
        TableColumn<ProductDTO, Float> sellingPriceCol = createSellingPriceColumn();

        titleCol.setPrefWidth(315);
        formatCol.setPrefWidth(175);
        stockCol.setPrefWidth(80);
        buyingPriceCol.setPrefWidth(94);
        sellingPriceCol.setPrefWidth(94);


        titleCol.setResizable(true);
        formatCol.setResizable(true);
        stockCol.setResizable(true);
        buyingPriceCol.setResizable(true);
        sellingPriceCol.setResizable(true);

        // row highlighting feature, to show user what products have been modified and are waiting to be updated
        productsTable.setRowFactory(tv -> new TableRow<ProductDTO>() {
            @Override
            protected void updateItem(ProductDTO item, boolean empty) {
                super.updateItem(item, empty);
                productsTable.getSelectionModel().clearSelection();

                if (empty || item == null) {
                    setStyle("");
                    setTooltip(null);
                    return;
                }

                int id = item.getId();

                if (lastActionWasSaved) {
                    boolean isModified = productsToUpdate.stream()
                            .anyMatch(p -> p.getId() == id);

                    if (isModified) {
                        if (invalidProductsIds.contains(id)) {
                            setStyle("-fx-background-color: red;");
                            setTooltip(new Tooltip("Stock must not be negative. Selling price must be at least 1.5x buying price."));
                        } else {
                            setStyle("-fx-background-color: green;");
                            setTooltip(new Tooltip("Product updated successfully."));
                        }
                    } else {
                        setStyle("");
                        setTooltip(null);
                    }
                } else {
                    boolean isModified = productsToUpdate.stream()
                            .anyMatch(p -> p.getId() == id);
                    if (isModified) {
                        setStyle("-fx-background-color: yellow;");
                        setTooltip(new Tooltip("Product was modified but not yet saved."));
                    } else {
                        setStyle("");
                        setTooltip(null);
                    }
                }
            }
        });

        productsTable.getColumns().addAll(titleCol, formatCol, stockCol, buyingPriceCol, sellingPriceCol);
        this.getChildren().add(productsTable);
    }

    /**
     * Binds title filed to opening of product details
     */
    private TableColumn<ProductDTO, String> createTitleCol() {
        TableColumn<ProductDTO, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        titleCol.setCellFactory(column -> {
            return new TableCell<ProductDTO, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }

                {
                    setOnMouseClicked(event -> {
                        if (!isEmpty() && event.getClickCount() == 2) {
                            ProductDTO product = getTableView().getItems().get(getIndex());
                            DashboardView.showProductDetailWindow(product);
                        }
                    });
                }
            };
        });
        return titleCol;
    }

    private TableColumn<ProductDTO, Integer> createStockColumn() {
        TableColumn<ProductDTO, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockCol.setOnEditCommit(event -> {
            ProductDTO originalProduct = event.getRowValue();  // Get the original product
            Integer newStock = event.getNewValue();            // Get the new stock value from the edit
            AddProductToProductsToUpdate(originalProduct, p -> p.updateStock(newStock));
            productsTable.refresh();

        });
        return stockCol;
    }

    private TableColumn<ProductDTO, Float> createBuyingPriceColumn() {
        TableColumn<ProductDTO, Float> buyingPriceCol = new TableColumn<>("Buying price");
        buyingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        buyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        buyingPriceCol.setOnEditCommit(event -> {
            ProductDTO originalProduct = event.getRowValue();  // Get the original product
            Float newBuyingPrice = event.getNewValue();        // Get the new buying price value from the edit
            AddProductToProductsToUpdate(originalProduct, p -> p.updateBuyingPrice(newBuyingPrice));
            productsTable.refresh();
        });
        return buyingPriceCol;
    }


    private TableColumn<ProductDTO, Float> createSellingPriceColumn() {
        TableColumn<ProductDTO, Float> sellingPriceCol = new TableColumn<>("Selling price");
        sellingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        sellingPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        sellingPriceCol.setOnEditCommit(event -> {
            ProductDTO originalProduct = event.getRowValue();  // Get the original product
            Float newSellingPrice = event.getNewValue();         // Get the new selling price value from the edit
            AddProductToProductsToUpdate(originalProduct, p -> p.updateSellingPrice( newSellingPrice));
            productsTable.refresh();

        });
        return sellingPriceCol;
    }

    /**
     *
     * @param product the original product we are making updates on
     * @param fieldToUpdate the corresponding method passed by the field (stock, buyingPrice, sellingPrice)
     */
    private void AddProductToProductsToUpdate(ProductDTO product, Consumer<ProductDTO> fieldToUpdate){
        for (ProductDTO existing : productsToUpdate) {
            if (existing.getId() == (product.getId())) {
                fieldToUpdate.accept(existing); // Apply just the changed field
                return;
            }
        }

        // If not already in the list, make a copy and apply the change
        ProductDTO copy = product.clone(); // Use your copy constructor
        fieldToUpdate.accept(copy);
        productsToUpdate.add(copy);
    }

    /**
     * this automatically triggers table to update due to productResult being an ObservableList
     * @param products results from search operation.
     *
     */
    public void updateTableEntries(List<ProductDTO> products) {
        productResult.setAll(products);
    }

    public void undoChanges(){
        productsToUpdate.clear();
        productsTable.refresh();
    }

    public void saveChanges(DashBoardController dashBoardController) {
        System.out.println("call to saveChanges");
        invalidProductsIds = dashBoardController.handleUpdateProduct(productsToUpdate);
        lastActionWasSaved = true;
        productsTable.refresh();

        // delay clearing of productsToUpdate so that the refresh as time to update rows colours
        new Thread(() -> {
            try {
                Thread.sleep(200); // Wait a bit before clearing
                Platform.runLater(() -> {
                    lastActionWasSaved = false;
                    productsToUpdate.clear();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /**
     * needed by dashboard to set editability according to user permissions
     * @return the dashboard's product tables
     */
    public TableView<ProductDTO> getProductsTable() {
        return productsTable;
    }
}


