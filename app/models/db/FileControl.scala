object FileControl {
  def using[Closeable <: {def close(): Unit}, B](closeable: Closeable)(getB: Closeable => B): B = 
    try {
      getB(closeable)
    } finally {
      closeable.close()
    }
}