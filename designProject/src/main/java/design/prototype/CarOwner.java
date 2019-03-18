package design.prototype;

/**
 * @Author:wangsl
 * @Description: 说明，要加说明!!!!!!
 * @Date:22:472019/3/18
 */
public class CarOwner implements Cloneable{

    private String name;
//    private CarOwner carOwner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public CarOwner getCarOwner() {
//        return carOwner;
//    }
//
//    public void setCarOwner(CarOwner carOwner) {
//        this.carOwner = carOwner;
//    }


    @Override
    public String toString() {
        return "CarOwner{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
