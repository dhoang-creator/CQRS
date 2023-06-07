package Repository

import Service.PostId

trait BlogService {
  def getPost(id: PostId)
  def addPost(content: PostContent)
  def updatePost(id: PostId, content: PostContent)
}
