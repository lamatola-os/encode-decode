
import io.circe._, io.circe.parser._

object CirceAgainSerialisers {

  def main(args: Array[String]): Unit = {

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

  def d = {
    val json =
      """
        {
          "customerNam"
        }
      """.stripMargin
  }
}
