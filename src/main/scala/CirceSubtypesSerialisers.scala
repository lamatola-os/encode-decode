
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object CirceSubtypesSerialisers {

  def main(args: Array[String]): Unit = {

    sealed trait Data

    case class OptionsData(data: Seq[String]) extends Data
    case class TextData(data: String) extends Data

    object Data {
      implicit val decodeData: Decoder[Data] = Decoder[OptionsData].map[Data](identity).or(Decoder[TextData].map[Data](identity))

      implicit val encodeData: Encoder[Data] = Encoder.instance {
        case options @ OptionsData(_) => options.asJson
        case text @ TextData(_) => text.asJson
      }
    }

    val optionsJson ="""{ "data": ["option1", "option2"] }""".stripMargin

    decode[Data](optionsJson) match {
      case Right(r: OptionsData) => println(r)
      case Left(l) => println(l)
    }


    val textJson ="""{ "data": "hey, how can i help ya?" }""".stripMargin

    decode[Data](textJson) match {
      case Right(r: TextData) => println(r)
      case Left(l) => println(l)
    }

  }

}
