////////////////////////////////////////////////////////////////////
// [GIOVANNI] [SORICE] [1144588]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.util.List;

import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.ItemType;

public class RestaurantBillImplementation implements RestaurantBill {
/*Il programma deve soddisfare i seguenti requisiti:
Dato  un  elenco  di  ordinazioni  (Pizze  e  Primi  piatti)  calcolare  il  totale  (somma  del  prezzo  dei
prodotti ordinati);
Se vengono ordinate più di 10 Pizze la meno costosa è in regalo (l’importo totale non considera
la pizza meno costosa);
Se  l’importo  totale  delle  ordinazioni  (Pizze  e  Primi)  supera  i  100  euro  viene  fatto  un  5%  di
sconto;
Non è possibile avere un’ordinazione con più di 20 elementi (se accade prevedere un messaggio
d’errore).*/
@Override
public double getOrderPrice(List<MenuItem> itemsOrdered) throws RestaurantBillException {
/*Calcolo del totale*/

if(itemsOrdered==null) {
return 0;
}
 if(itemsOrdered.size() > 20) {
throw new RestaurantBillException("Non puoi ordinare più di 20 elementi per ogni ordinazione");
 }
 
 double prezzo =itemsOrdered.stream().mapToDouble(item->item.getPrice()).sum();
 

 if(itemsOrdered.stream().filter(item->item.getItemType().equals(ItemType.PIZZE)).count()>10) {
prezzo-=itemsOrdered.stream().filter(item->item.getItemType().equals(ItemType.PIZZE))
.mapToDouble(o -> o.getPrice()).min().getAsDouble();
 }
if(prezzo>100) {
prezzo*=0.95;
}
 return prezzo;
}
}
