package Service

import Repository.PostContent
import io.circe.{Decoder, Encoder}

import java.util.UUID

class PostId(val id: UUID) extends AnyVal {
  // Rule of thumb that although no parameters, parenthesis are still included in side-effecting functions and omitted in pure functions
  override def toString: String = id.toString
}

object PostId {
  def apply(): PostId = new PostId(UUID.randomUUID())

  def apply(id: UUID): PostId = new PostId(id)

  // Below is the decoder and encoder to JSON of the PostId -> are we passing in the UUID and the string version of the post here?
  implicit val postIdDecoder: Decoder[PostId] =
    Decoder.decodeUUID.map(PostId(_))
  implicit val postIdEncoder: Encoder[PostId] =
    Encoder.encodeUUID.contramap(_.id)
}
