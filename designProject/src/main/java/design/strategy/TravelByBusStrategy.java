package design.strategy;

/**
 * @Author wangsl
 * @Date Create In 10:30 2019/4/1
 * @Description:
 */
public class TravelByBusStrategy implements TransportStrategy {
    @Override
    public void travel() {
        System.out.println("乘公共汽车出门");
    }
}
