package Service

import API.BlogEntity
import Repository.PostContent
import akka.actor.{ActorSystem, Props}
import akka.actor._

import scala.concurrent.Future

trait BlogService {

  import BlogEntity._

  // we need to establish whether the below ActorSystem is in reference to the actors which have been created from the case class below?
  val BlogAkkaSystem = ActorSystem("BlogService")

  private val blogEntity = system.actorOf(Props[BlogAkkaSystem])

  def getPost(id: PostId): Future[MaybePost[PostContent]] =
    (blogEntity ! GetPost(id)).mapTo[MaybePost[PostContent]]

  def addPost(content: PostContent): Future[PostAdded] =
    (blogEntity ! AddPost(content)).mapTo[PostAdded]

  def updatePost(id: PostId, content: PostContent): Future[MaybePost[PostUpdated]] =
    (blogEntity ! UpdatePost(id, content)).mapTo[MaybePost[PostUpdated]]

}
