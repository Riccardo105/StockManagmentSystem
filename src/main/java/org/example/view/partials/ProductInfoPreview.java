package org.example.view.partials;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.List;

public class ProductInfoPreview extends VBox {

    /**
     * observable list used to dynamically update ui once change in list is detected
     */
   private final  ObservableList<ProductDTO> productResult = FXCollections.observableArrayList();

    @SuppressWarnings("unchecked")
    public ProductInfoPreview() {

        TableView<ProductDTO> productsTable = new TableView<>(this.productResult);
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
            Integer newStock = event.getNewValue();            // Get the new stock value from the edit
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
        });
        return sellingPriceCol;
    }

    public void updateTableEntries(List<ProductDTO> products) {
        productResult.setAll(products);
    }
}


