package design.factory.methods;



/**
 * @Author:wangsl
 * @Description: 说明，要加说明!!!!!!
 * @Date:22:262019/3/17
 */
public class BYDFactory implements CarFactory {
    public Car createCar() {
        return new BYDCar();
    }
}
