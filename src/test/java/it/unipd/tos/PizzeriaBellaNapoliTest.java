////////////////////////////////////////////////////////////////////
// [GIOVANNI] [SORICE] [1144588]
////////////////////////////////////////////////////////////////////

package it.unipd.tos;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unipd.tos.business.RestaurantBill;
import it.unipd.tos.business.RestaurantBillImplementation;
import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.ItemType;
import it.unipd.tos.model.MenuItem;
import org.junit.Test;

public class PizzeriaBellaNapoliTest {

private List<MenuItem> getMenuMaggioreDi100() {
        return Stream.of(
                new MenuItem(ItemType.PIZZE, "pizza1", 3),
                new MenuItem(ItemType.PIZZE, "pizza2", 4),
                new MenuItem(ItemType.PIZZE, "pizza3", 5),
                new MenuItem(ItemType.PIZZE, "pizza4", 6),
                new MenuItem(ItemType.PIZZE, "pizza5", 7),
                new MenuItem(ItemType.PIZZE, "pizza6", 8),
                new MenuItem(ItemType.PIZZE, "pizza7", 9),
                new MenuItem(ItemType.PRIMI, "primo1", 10),
                new MenuItem(ItemType.PRIMI, "primo2", 11),
                new MenuItem(ItemType.PRIMI, "primo3", 12),
                new MenuItem(ItemType.PRIMI, "primo4", 13),
                new MenuItem(ItemType.PRIMI, "primo5", 11),
                new MenuItem(ItemType.PRIMI, "primo6", 11),
                new MenuItem(ItemType.PRIMI, "primo7", 11)
        ).collect(Collectors.toList());
    }
private List<MenuItem> getMenuMinoreDi100() {
    return Stream.of(
            new MenuItem(ItemType.PIZZE, "pizza1", 3),
            new MenuItem(ItemType.PIZZE, "pizza2", 4),
            new MenuItem(ItemType.PIZZE, "pizza3", 5),
            new MenuItem(ItemType.PIZZE, "pizza4", 6),
            new MenuItem(ItemType.PRIMI, "primo1", 10),
            new MenuItem(ItemType.PRIMI, "primo2", 11),
            new MenuItem(ItemType.PRIMI, "primo3", 12),
            new MenuItem(ItemType.PRIMI, "primo4", 13)
    ).collect(Collectors.toList());
}

    private static double getTotale(List<MenuItem> itemsOrdered) {
        return itemsOrdered
                .stream()
                .mapToDouble(m -> m.getPrice())
                .sum();
    }
    @Test
    public void testItemTypeEnumValues() {
        assertEquals("PIZZE", ItemType.valueOf(ItemType.PIZZE.toString()).name());
        assertEquals("PRIMI", ItemType.valueOf(ItemType.PRIMI.toString()).name());
    }

    
    @Test
    public void testCasoBase() throws RestaurantBillException {
    	RestaurantBill bill = new RestaurantBillImplementation();
        List<MenuItem> itemsOrdered = getMenuMinoreDi100();
        assertEquals( getTotale(itemsOrdered), bill.getOrderPrice(itemsOrdered), 0.001);
    }

    
    @Test
    public void testSconto5Percento() throws RestaurantBillException {
    	RestaurantBill bill = new RestaurantBillImplementation();
        List<MenuItem> itemsOrdered = getMenuMaggioreDi100();
        itemsOrdered.add(new MenuItem(ItemType.PIZZE, "Oro e argento", 100.00));
        double rawPrice = getTotale(itemsOrdered);
        double discount = rawPrice * 5 / 100;
        assertEquals(bill.getOrderPrice(itemsOrdered), rawPrice - discount, 0.001);
    }

   
    @Test(expected = RestaurantBillException.class)
    public void testOrdineConPiùDi20Elementi() throws RestaurantBillException {
    	RestaurantBill bill = new RestaurantBillImplementation();
        List<MenuItem> list = getMenuMaggioreDi100();
        list.addAll(list);

        assert(list.size() >= 20);
        bill.getOrderPrice(list);
    }

    
    @Test
    public void testSconto5PercentoGratisPizzaMenoCostosa() throws RestaurantBillException {
    	RestaurantBill bill = new RestaurantBillImplementation();
        List<MenuItem> list = getMenuMaggioreDi100();
        list.add(new MenuItem(ItemType.PIZZE, "pizza8", 7));
        list.add(new MenuItem(ItemType.PIZZE, "pizza9", 7));
        list.add(new MenuItem(ItemType.PIZZE, "pizza10", 7));
        list.add(new MenuItem(ItemType.PIZZE, "pizza11", 7));
        list.add(new MenuItem(ItemType.PIZZE, "pizza12", 7));
        double totalRawPrice = getTotale(list);
        double minPrice = 3;
        assertEquals((totalRawPrice - minPrice)*0.95, bill.getOrderPrice(list) , 0.0001);
    }
    @Test
    public void testRegaloPizzaMenoCostosa() throws RestaurantBillException {
    	RestaurantBill bill = new RestaurantBillImplementation();
        List<MenuItem> list = getMenuMinoreDi100();    
        list.add(new MenuItem(ItemType.PIZZE, "pizza5", 3));
        list.add(new MenuItem(ItemType.PIZZE, "pizza6", 3));
        list.add(new MenuItem(ItemType.PIZZE, "pizza7", 3));
        list.add(new MenuItem(ItemType.PIZZE, "pizza8", 7));
        list.add(new MenuItem(ItemType.PIZZE, "pizza9", 7));
        list.add(new MenuItem(ItemType.PIZZE, "pizza10", 7));
        list.add(new MenuItem(ItemType.PIZZE, "pizza11", 7));
        double totalRawPrice = getTotale(list);
        double minPrice = 3;
        assertEquals(totalRawPrice - minPrice, bill.getOrderPrice(list) , 0.0001);
    }

    
    @Test
    public void testMenuNull() throws RestaurantBillException {
    	RestaurantBill bill = new RestaurantBillImplementation();
        assertEquals(0, bill.getOrderPrice(null), 0.0001);
    }
}
