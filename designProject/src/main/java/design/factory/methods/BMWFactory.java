package design.factory.methods;

/**
 * @Author:wangsl
 * @Description: 说明，要加说明!!!!!!
 * @Date:22:282019/3/17
 */
public class BMWFactory implements CarFactory {
    @Override
    public Car createCar() {
        return new BMWCar();
    }
}
