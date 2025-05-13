package org.example.model.Service;

import org.example.model.DTO.AccessControl.OperationDTO;
import org.example.model.DTO.AccessControl.PermissionDTO;
import org.example.model.DTO.AccessControl.ResourceDTO;

import java.util.HashSet;

/**
 * deals with checking if current user has given permission
 * any new permission check can be added here
 */
public class PermissionManager {


    // available Resources
   private static final ResourceDTO productResource = new ResourceDTO("PRODUCT");
   private static final ResourceDTO userResource = new ResourceDTO("USER");
   private static final  ResourceDTO stockReportResource = new ResourceDTO("STOCK_REPORT");

    // possible operations
    private static final OperationDTO printOperation = new OperationDTO("PRINT");
    private static final OperationDTO readOperation= new OperationDTO("READ");
    private static final OperationDTO createOperation= new OperationDTO("CREATE");
    private static final OperationDTO updateOperation= new OperationDTO("UPDATE");
    private static final OperationDTO activateOperation= new OperationDTO("ACTIVATE");


    public static boolean canSearchProducts( HashSet<PermissionDTO> currentUserPermissions ) {
        PermissionDTO pm = new PermissionDTO(readOperation, productResource);
        return currentUserPermissions.contains(pm);
    }
    public static boolean canUpdateProducts(HashSet<PermissionDTO> currentUserPermissions){
        PermissionDTO pm = new PermissionDTO(updateOperation, productResource);
        return currentUserPermissions.contains(pm);
    }
    public static boolean canCreateProduct(HashSet<PermissionDTO> currentUserPermissions){
        PermissionDTO pm = new PermissionDTO(createOperation, productResource);
        return currentUserPermissions.contains(pm);
    }

    public static boolean canPrintProduct(HashSet<PermissionDTO> currentUserPermissions){
        PermissionDTO pm = new PermissionDTO(printOperation, productResource);
        return currentUserPermissions.contains(pm);
    }

    public static boolean canPrintStockReport(HashSet<PermissionDTO> currentUserPermissions){
        PermissionDTO pm = new PermissionDTO(printOperation, stockReportResource);
        return currentUserPermissions.contains(pm);
    }
    public static boolean canActivateAccount(HashSet<PermissionDTO> currentUserPermissions){
        PermissionDTO pm = new PermissionDTO(activateOperation, userResource);
        return currentUserPermissions.contains(pm);
    }

}
