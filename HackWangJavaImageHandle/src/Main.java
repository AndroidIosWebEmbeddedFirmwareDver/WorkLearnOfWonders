import hack.wang.java.image.handle.BlockChainCertificateGeneror;
import hack.wang.java.image.handle.ImageHandleUtilsTest;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");

//        ImageHandleUtilsTest.test();

        BlockChainCertificateGeneror.doCreateChainCretificate();
        BlockChainCertificateGeneror.doCreateChainCretificateFor10();
    }
}
