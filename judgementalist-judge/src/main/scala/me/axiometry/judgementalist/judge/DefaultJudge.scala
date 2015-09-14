package me.axiometry.judgementalist.judge

import me.axiometry.judgementalist.Judge
import me.axiometry.judgementalist.Judge.{ Request, Response }

class DefaultJudge extends Judge {
  def receive = {
    case request: Request.Submit =>
    case          Request.Languages =>
    case request: Request =>
      // error
  }
}