package API

import Service.PostId
import akka.pattern.pipe
import akka.persistence.{PersistentActor, SnapshotOffer}

import scala.concurrent.{Future, Promise}

class BlogEntity extends PersistentActor {

  import Service.BlogEntity._
  import context._

  private var state = BlogState()

  override def persistenceId: String = "blog"

  override def receiveCommand: Receive = {
    case GetPost(id) =>
      sender() ! state(id)
    case AddPost(content) =>
      // Are Events baked into Akka Actors?
      handleEvent(PostAdded(PostId(), content)) pipeTo sender()
      ()
    case UpdatePost(id, content) =>
      state(id) match {
        case response @ Left(_) => sender() ! response
        case Right(_) =>
          handleEvent(PostUpdated(id, content)) pipeTo sender()
          ()
      }
  }

  // You need to look up the Future and Promises in the below as well as Success and 'future' method
  private def handleEvent[E <: BlogEvent](e: => E): Future[E] = {
    val p = Promise[E]
    persist(e) { event =>
      p.success(event)
      state += event
      system.eventStream.publish(event)
      if (lastSequenceNr != 0 && lastSequenceNr % 1000 == 0)
        saveSnapshot(state)
    }
    p.future
  }

  override def receiveRecover: Receive = {
    case event: BlogEvent =>
      state += event
    case SnapshotOffer(_, snapshot: BlogState) =>
      state = snapshot
  }
}
