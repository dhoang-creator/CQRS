package API

import Repository.PostContent
import Service.{BlogService, PostId}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{JavaUUID, complete, entity, onSuccess, pathEndOrSingleSlash, pathPrefix, post}
import akka.http.scaladsl.server.Route

trait BlogRestApi extends RestApi with BlogService {
  override def route: Route =
    pathPrefix("api" / blog) {
      (pathEndOrSingleSlash & post) {
        // Post /api/blog/
        entity(as[PostContent]) { content =>
          onSuccess(addPost(content)) { added =>
            complete((StatusCodes.Created, added))
          }
        }
      } ~
        pathPrefix(JavaUUID.map(PostId(_))) { id =>
          pathEndOrSingleSlash {
            get {
              // GET /api/blog/:id
              onSuccess((getPost(id))) {
                case Right(content) => complete((StatusCodes.OK, content))
                case Left(error) => complete((StatusCodes.NotFound, error))
              }
            } ~
              put {
                // PUT /api/blog/:id
                entity(as[PostContent]) { content =>
                  onSuccess(updatePost(id, content)) {
                    case Right(updated) => complete((StatusCodes.OK, updated))
                    case Left(error) => complete((StatusCodes.NotFound, error))
                  }
                }
              }
          }
        }
    }
}
