package encoder;

import data.SkuOuterClass;

import java.io.*;
import java.util.Base64;

public class ProtobufEncoders {

    private static final String SERIALISED_FILE = "sku_data.bin";

    public static void main(String[] args) throws IOException {

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

        String toFile = protobufToFile(sku1);
        for (int i = 0; i < 100; i++) {
            String encoded = protobufToB64(sku1);
            byte[] decoded = Base64.getDecoder().decode(encoded);
            var skuDecodedFromBytes = SkuOuterClass.Sku.parseFrom(new ByteArrayInputStream(decoded));
            System.out.println("sku decoded ---------- \n" + skuDecodedFromBytes);
        }

        //reader
        var skuDecoded = SkuOuterClass.Sku.parseFrom(new FileInputStream(toFile));
        var skuDecodedFromFile = SkuOuterClass.Sku.parseFrom(new FileInputStream(toFile));
        System.out.println("sku decoded ---------- \n" + skuDecodedFromFile);
    }

    /**
     * time: 1 millis
     */
    private static String protobufToB64(SkuOuterClass.Sku sku) throws IOException {
        var start = System.currentTimeMillis();
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        sku.writeTo(bstream);
        byte[] bytes = bstream.toByteArray();
        byte[] encode = Base64.getEncoder().encode(bytes);
        String s = new String(encode);
        System.out.println("time: " + (System.currentTimeMillis() - start) + " millis");
        return s;
    }

    private static String protobufToFile(SkuOuterClass.Sku sku) throws IOException {
        String serialisedFile = "sku_data_"+ sku.getName() + ".bin";
        var stream = new FileOutputStream(serialisedFile);
        sku.writeTo(stream);
        stream.close();
        return serialisedFile;
    }

}
