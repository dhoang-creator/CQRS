package Service

import API.BlogEntity
import Repository.PostContent
import akka.actor.Props

import scala.concurrent.Future

trait BlogService extends AkkaConfiguration {

  import BlogEntity._

  private val blogEntity = ActorRefFactory.actorOf(Props[BlogEntity])

  def getPost(id: PostId): Future[MaybePost[PostContent]] =
    (blogEntity ? GetPost(id)).mapTo[MaybePost[PostContent]]

  def addPost(content: PostContent): Future[PostAdded] =
    (blogEntity ? AddPost(content)).mapTo[PostAdded]

  def updatePost(id: PostId, content: PostContent): Future[MaybePost[PostUpdated]] =
    (blogEntity ? UpdatePost(id, content)).mapTo[MaybePost[PostUpdated]]

}
