package base

trait ExceptionExt {
  /**
   * 异常模拟
   * @return
   */
  def mockException():Any

  /**
   * 除0异常
   * @return
   */
  def dividZero():Any= {
    return  1/0
  }

}
