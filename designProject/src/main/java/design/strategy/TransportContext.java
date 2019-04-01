package design.strategy;

/**
 * @Author wangsl
 * @Date Create In 10:33 2019/4/1
 * @Description:
 */
public class TransportContext {

    private TransportStrategy transportStrategy;

    public TransportContext(TransportStrategy transportStrategy){
        this.transportStrategy =  transportStrategy;
    }

    public void travel(){
        this.transportStrategy.travel();
    }
}
