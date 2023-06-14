package Repository

// Below is the data model of the Event which is to be sourced - note that this is differentiated from the other models since it's the Event rather than the C or Q
final case class PostContent(title: String, author: String, body: String)
