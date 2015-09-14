package me.axiometry.judgementalist.judge

import me.axiometry.judgementalist.Judge
import me.axiometry.judgementalist.Judge.{ Request, Response, Requests, Responses }

class DefaultJudge extends Judge {
  def receive = {
    case request: Requests.Submit =>
    case          Requests.Languages =>
    case request: Request =>
      // error
  }
}