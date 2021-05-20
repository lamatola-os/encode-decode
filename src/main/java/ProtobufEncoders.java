import java.io.FileOutputStream;
import java.io.IOException;

public class ProtobufEncoders {

    public static void main(String[] args) throws IOException {
        var sku = SkuOuterClass.Sku.newBuilder()
                .setId(1)
                .setName("Macbook")
                .setInventory(100)
                .build();

        var stream = new FileOutputStream("sku_data");

        sku.writeTo(stream);
        stream.close();
    }
}
