package design.factory.simple;

/**
 * @Author:wangsl
 * @Description: 说明，要加说明!!!!!!
 * @Date:22:212019/3/17
 */
public class SimpleFactory {

    public static final String BYD = "BYD";
    public static final String BMW = "BMW";

    public Car createCar(String type){
        if(BYD.equals(type)){
            return new BYDCar();
        }else if(BMW.equals(type)){
            return new BMWCar();
        }
        return null;
    }
}
