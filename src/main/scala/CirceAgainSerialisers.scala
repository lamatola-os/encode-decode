
import io.circe._, io.circe.parser._

// https://github.com/circe/circe/pull/375

object CirceAgainSerialisers {

  def main(args: Array[String]): Unit = {

    customDecoding

  }

  def stuf = {

    val rawFakeJson: String =
      """[
        |  {
        |    "field1": "aa11",
        |    "field2": "aa22",
        |    "structField": {
        |      "sf1": "aaa11",
        |      "sf2": "aaa22"
        |    }
        |  },
        |  {
        |    "field1": "bb11",
        |    "field2": "bb22",
        |    "structField": {
        |      "sf1": "bbb11",
        |      "sf2": "bbb22"
        |    }
        |  },
        |  {
        |    "field1": "cc11",
        |    "field2": "cc22",
        |    "structField": {
        |      "sf1": "ccc11",
        |      "sf2": "ccc22"
        |    }
        |  }
        |  ]
      """.stripMargin

    val deserialised: Either[ParsingFailure, Json] = parse(s"$rawFakeJson")

    val fakeSerialise = deserialised.map(json => json.asArray.getOrElse(Vector.empty).mkString(","))

    fakeSerialise match {
      case Right(json) => println(json)
      case Left(failed) => println(failed)
    }
  }

  def customDecoding = {

    import io.circe.Decoder, io.circe.jawn.decode
    import cats.instances.either._

    case class Customer(customerName: String, age: Int, active: Boolean)

    implicit val decodeFoo: Decoder[Customer] = Decoder.fromState(
      for {
        name <- Decoder.state.decodeField[String]("customerName")
        age <- Decoder.state.decodeField[Int]("age")
        active <- Decoder.state.decodeField[Boolean]("active")
        _ <- Decoder.state.requireEmpty
      } yield Customer(name, age, active)
    )

    val json =
      """
        {
          "customerName": "ABC",
          "age": 10,
          "active": true
        }
      """.stripMargin

    val decodedCustomer = decode[Customer](json)
    println(decodedCustomer)


    val badJson = decode[Customer](
      """
        {
          "customerName": 1,
          "age": 10,
          "active": true
        }
      """.stripMargin)
    badJson match {
      case Right(r) =>
        println(r)
      case Left(l) =>
        println(l.fillInStackTrace())
    }
  }
}
