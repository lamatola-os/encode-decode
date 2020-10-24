import org.json.JSONObject;

public class JsonOrgEncoder {

    public static void main(String[] args) {

        var json = "{\"data\":\"[{\\\"lng\\\":-122.3097,\\\"lat\\\":47.804371}]\"}";
        var json1 = "{\"data\":[{\"lng\":-122.3097,\"lat\":47.804371}]}";

        System.out.println(json);
        var js = new JSONObject(json1);
        var points = js.optJSONArray("data");
        System.out.println(points.length());
    }
}
