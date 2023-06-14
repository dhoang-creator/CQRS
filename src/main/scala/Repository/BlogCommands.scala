package Repository

import Service.PostId

// Below is the data model for the Create and Query Requests
trait BlogCommands {
  def getPost(id: PostId)
  def addPost(content: PostContent)
  def updatePost(id: PostId, content: PostContent)
}
