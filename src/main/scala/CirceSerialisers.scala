object CirceSerialisers {

  def main(args: Array[String]): Unit = {

    import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

    sealed trait Order
    case class DeliveryOrder(items: Vector[String]) extends Order
    case class InStoreOrder(orderId: Int, price: Option[Double]) extends Order

    val storeOrder: Order = InStoreOrder(1, Some(14.0))

    val json = storeOrder.asJson.noSpaces
    println(json)

    val decodedOrder: Either[Error, Order] = decode[Order](json)
    println(decodedOrder)

    //another way
    //https://circe.github.io/circe/codec.html

    import io.circe._, io.circe.generic.semiauto._

    case class Product(sku: Int, name: String, available: Boolean)

    implicit val productDecoder: Decoder[Product] = deriveDecoder[Product]
    implicit val productEncoder: Encoder[Product] = deriveEncoder[Product]

    val data =
      """
        {
         "sku": 1,
         "name": "Bag",
         "available": false
        }
      """.stripMargin


    //forProductN helper methods

    import io.circe.{Decoder, Encoder}

    case class User(id: Long, firstName: String, lastName: String)

    object UserCodec {
      implicit val decodeUser: Decoder[User] = Decoder.forProduct3("id", "first_name", "last_name")(User.apply)

      implicit val encodeUser: Encoder[User] = Encoder.forProduct3("id", "first_name", "last_name")(u =>
        (u.id, u.firstName, u.lastName))
    }

    import UserCodec._

    val userDataStr =
      """
        {
         "id": 1,
         "first_name": "prayag",
         "last_name": "upd"
        }
      """.stripMargin

    val userData = decode[User](userDataStr)

    println(userData)
  }
}
