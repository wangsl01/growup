package design.strategy;

/**
 * @Author wangsl
 * @Date Create In 10:30 2019/4/1
 * @Description:
 */
public class TravelByCarStrategy implements TransportStrategy {

    @Override
    public void travel() {
        System.out.println("乘小汽车出门");
    }
}
