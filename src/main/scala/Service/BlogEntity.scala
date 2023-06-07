package Service

import Repository.PostContent

/*
  In the below, we clearly identify a Command, Event and State
 */

object BlogEntity {

  sealed trait BlogCommand

  final case class GetPost(id: PostId)
    extends BlogCommand

  final case class AddPost(content: PostContent)
    extends BlogCommand

  final case class UpdatePost(id: PostId, content: PostContent)
    extends BlogCommand

  sealed trait BlogEvent {
    val id: PostId
    val content: PostContent
  }

  final case class PostAdded(id: PostId, content: PostContent)
    extends BlogEvent

  final case class PostUpdated(id: PostId, content: PostContent)
    extends BlogEvent

  final case class PostNotFound(id: PostId)
    extends RuntimeException(s"Blog post not found with id $id")

  /*
    The first two parts are pretty clear cut with regards to their respective case classes and the traits that they extend
    But with the below, in order to capture the state of a system, we need to see whether it exists or not?
    And thus this is why the State is wrapped within a Map that first identifies whether a Post exists or not within an Either data type
   */

  type MaybePost[+A] = Either[PostNotFound, A]

  final case class BlogState(posts: Map[PostId, PostContent]) {
    def apply(id: PostId): MaybePost[PostContent] =
      posts.get(id).toRight(PostNotFound(id))

    def +(event: BlogEvent): BlogState =
      BlogState(posts.updated(event.id, event.content))
  }

  object BlogState {
    def apply(): BlogState =
      BlogState(Map.empty)
  }

}
