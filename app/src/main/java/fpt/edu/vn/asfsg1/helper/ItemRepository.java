package fpt.edu.vn.asfsg1.helper;

import fpt.edu.vn.asfsg1.services.ItemService;

public class ItemRepository {
    public static ItemService getItemService(){
        return APIClient.getClient().create(ItemService.class);
    }
}
