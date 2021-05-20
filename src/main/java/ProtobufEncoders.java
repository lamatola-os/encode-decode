import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProtobufEncoders {

    private static final String SERIALISED_FILE = "sku_data.bin";

    public static void main(String[] args) throws IOException {

        var stream = new FileOutputStream(SERIALISED_FILE);

        var sku1 = SkuOuterClass.Sku.newBuilder()
                .setId(10)
                .setName("Macbook")
                .setInventory(100)
                .build();


        var sku2 = SkuOuterClass.Sku.newBuilder()
                .setId(20)
                .setName("iphone")
                .setInventory(100)
                .build();

        sku1.writeTo(stream);
        stream.close();

        //reader

        var skuDecoded = SkuOuterClass.Sku.parseFrom(new FileInputStream(SERIALISED_FILE));
        System.out.println("sku decoded ---------- \n" + skuDecoded);

    }
}
