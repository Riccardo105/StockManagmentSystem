package org.example.view.partials;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.example.model.DTO.products.ProductDTO;
import org.example.view.DashboardView;

import java.util.ArrayList;

public class ProductInfoPreview extends VBox {

    /**
     * this list holds all the products that were  modified
     * and is shipped off once the user clicks on the save button
     */
    ArrayList<ProductDTO> productsToUpdate = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public ProductInfoPreview() {
        TableView<ProductDTO> productsTable = new TableView<>();
        productsTable.setEditable(true);

        TableColumn<ProductDTO, String> titleCol = createTitleCol();
        TableColumn<ProductDTO, String> formatCol = new TableColumn<>("Format");
        formatCol.setCellValueFactory(new PropertyValueFactory<>("format"));
        TableColumn<ProductDTO, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        TableColumn<ProductDTO, Float> buyingPriceCol = new TableColumn<>("Buying price");
        buyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        TableColumn<ProductDTO, Float> sellingPriceCol = new TableColumn<>("Selling price");
        sellingPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));


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
                            ProductInfo productInfo = new ProductInfo(product);
                            DashboardView.showProductDetailWindow(productInfo);
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
            Integer newStock = event.getNewValue();             // Get the new stock value from the edit

            ProductDTO existingProduct = checkProductIsAlreadyUpdated(originalProduct);
            if (existingProduct != null) {
                // If product is already in the list, update it
                existingProduct.updateStock(newStock);
            } else {
                // If product is not in the list, create a new product and add to the list
                 

            }
        });
        return stockCol;
    }

    private TableColumn<ProductDTO, Float> createBuyingPriceColumn() {
        TableColumn<ProductDTO, Float> buyingPriceCol = new TableColumn<>("Buying price");
        buyingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        buyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        buyingPriceCol.setOnEditCommit(event -> {
            ProductDTO originalProduct = event.getRowValue();  // Get the original product
            Float newBuyingPrice = event.getNewValue();         // Get the new buying price value from the edit

            ProductDTO existingProduct = checkProductIsAlreadyUpdated(originalProduct);
            if (existingProduct != null) {
                // If product is already in the list, update it
                existingProduct.updateBuyingPrice(newBuyingPrice);
            } else {
                // If product is not in the list, create a new product and add to the lis
            }
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

            ProductDTO existingProduct = checkProductIsAlreadyUpdated(originalProduct);
            if (existingProduct != null) {
                // If product is already in the list, update it
                existingProduct.updateSellingPrice(newSellingPrice);
            } else {
                // If product is not in the list, create a new product and add to the list
            }
        });
        return sellingPriceCol;
    }

    /**
     * used to check if a product was already modified
     * @param product the product that is being updated
     * @return the product if it finds it in the updateProduct list
     */
    private ProductDTO checkProductIsAlreadyUpdated(ProductDTO product) {
        for (ProductDTO p : productsToUpdate) {
            if (p.getId() == product.getId()) {}
                return p;
        }
        return null;
    }

}


