package me.axiometry.judgementalist

import akka.actor.Actor

trait Judge extends Actor

object Judge {
  sealed trait Request
  object Request {
    case class Submit(participantId: String,
                      problemId: String,
                      contestId: String = null,
                      source: String,
                      extension: String) extends Request

    case object Languages extends Request
  }

  sealed trait Response
  object Response {
    case class Submit(id: Long) extends Response
    case class SubmitFailure(reason: SubmitFailure.Reason) extends Response
    object SubmitFailure {
      sealed trait Reason
      object Reason {
        case class ParticipantNotFound(id: String) extends Reason
        case class ProblemNotFound(id: String) extends Reason
        case class ContestNotFound(id: String) extends Reason
        case class UnsupportedExtension(extension: String) extends Reason
      }
    }

    case class Languages(languages: Language*) extends Response

    case class SubmissionStateChanged(id: Long) extends Response
  }
}