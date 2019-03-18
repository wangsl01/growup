package design.prototype;

/**
 * @Author:wangsl
 * @Description: 说明，要加说明!!!!!!
 * @Date:22:452019/3/17
 */
public class CarPrototype implements Cloneable{

    public CarPrototype() {
    }

    public CarPrototype(int wheel, int seat, float length, float width, boolean openHeadLamp, CarOwner carOwner) {
        this.wheel = wheel;
        this.seat = seat;
        Length = length;
        this.width = width;
        this.openHeadLamp = openHeadLamp;
        this.carOwner = carOwner;
    }

    private int wheel;
    private int seat;
    private float Length;
    private float width;
    private boolean openHeadLamp;
    private CarOwner carOwner;

    public int getWheel() {
        return wheel;
    }

    public void setWheel(int wheel) {
        this.wheel = wheel;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public float getLength() {
        return Length;
    }

    public void setLength(float length) {
        Length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public boolean isOpenHeadLamp() {
        return openHeadLamp;
    }

    public void setOpenHeadLamp(boolean openHeadLamp) {
        this.openHeadLamp = openHeadLamp;
    }

    public CarOwner getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(CarOwner carOwner) {
        this.carOwner = carOwner;
    }

    public void skyIsBlack(){
        if(this.isOpenHeadLamp()){
            System.out.println("天黑开灯了！！！！");
        }else{
            System.out.println("天黑了，还没有开灯??????");
        }
    }

    @Override
    public String toString() {
        return "CarPrototype{" +
                "wheel=" + wheel +
                ", seat=" + seat +
                ", Length=" + Length +
                ", width=" + width +
                ", openHeadLamp=" + openHeadLamp +
                ", carOwner=" + carOwner +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        CarOwner carOwner = (CarOwner) this.getCarOwner().clone();
        CarPrototype carPrototype = (CarPrototype) super.clone();
        carPrototype.setCarOwner(carOwner);
        return carPrototype;
    }
}
