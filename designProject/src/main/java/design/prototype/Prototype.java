package design.prototype;

/**
 * @Author:wangsl
 * @Description: 说明，要加说明!!!!!!
 * @Date:22:522019/3/18
 */
public class Prototype {

    public static void main(String[] args) throws CloneNotSupportedException {
        CarOwner carOwner = new CarOwner();
        carOwner.setName("wsl");
        CarPrototype carPrototype = new CarPrototype(4,5,4.7f,2.5f,false,carOwner) ;
        carPrototype.skyIsBlack();

        carPrototype.setOpenHeadLamp(true);
        carPrototype.skyIsBlack();

        CarPrototype cpp = (CarPrototype) carPrototype.clone();
        System.out.println(cpp.toString());
        System.out.println(cpp.equals(carPrototype));

        cpp.skyIsBlack();

    }
}
