package design.strategy;

/**
 * @Author wangsl
 * @Date Create In 10:30 2019/4/1
 * @Description:
 */
public class TravelByBikeStrategy implements TransportStrategy{

    @Override
    public void travel() {
        System.out.println("骑自行车出门");
    }
}
