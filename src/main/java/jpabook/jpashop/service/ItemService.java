package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 변경 감지 기능 사용 (권장)
    // merge 사용할 경우 모든 값을 변경 (기존 값이 유지 되는게 아님. 즉, 값이 없는 경우 null로 업데이트)
    // 실무에서는 제약 조건이 많으므로 merge보다는 변경 감지를 사용하여 업데이트
//    @Transactional
//    public void updateItem(Long itemId, Book param) {
//        Item findItem = itemRepository.findOne(itemId);
//        // setter를 사용하지 말고 메서드를 사용하는 것을 권장 (다시 한 번 강조!)
//        findItem.setPrice(param.getPrice());
//        findItem.setName(param.getName());
//        findItem.setStockQuantity(param.getStockQuantity());
//    }

    // parameter가 많을 경우 dto를 생성하여 전달
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        // setter를 사용하지 말고 메서드를 사용하는 것을 권장 (다시 한 번 강조!)
        // findItem.change(name, price, stockQuantity);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
