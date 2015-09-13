package me.axiometry.judgementalist

import akka.actor.Actor

trait Judge extends Actor

object Judge {
  object Requests {
    case class Submit(participantId: String,
                      problemId: String,
                      contestId: String = null,
                      source: String,
                      extension: String)

    case object Languages
  }
  object Responses {
    case class Submit(id: Long)
    case class SubmitFailure(reason: SubmitFailure.Reason)
    object SubmitFailure {
      sealed trait Reason
      object Reason {
        case class ParticipantNotFound(participantId: String) extends Reason
        case class ProblemNotFound(problemId: String) extends Reason
        case class ContestNotFound(contestId: String) extends Reason
        case class UnsupportedExtension(extension: String) extends Reason
      }
    }

    case class Languages(languages: Language*)

    case class SubmissionStateChanged(id: Long)
  }
}