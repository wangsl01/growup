package design.strategy;

/**
 * @Author wangsl
 * @Date Create In 10:34 2019/4/1
 * @Description:
 */
public class TestStrtegy {

    public static void main(String[] args) {
        TransportContext bybus = new TransportContext(new TravelByBusStrategy());
        bybus.travel();

        TransportContext bycar = new TransportContext(new TravelByCarStrategy());
        bycar.travel();

        TransportContext bybike = new TransportContext(new TravelByBikeStrategy());
        bybike.travel();
    }
}
