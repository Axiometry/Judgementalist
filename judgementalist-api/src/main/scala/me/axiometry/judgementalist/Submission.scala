package me.axiometry.judgementalist

case class Submission(id: Long,
                      participant: Participant,
                      problem: Problem,
                      contest: Option[Contest],
                      date: Long)

object Submission {
  case class Source(submission: Submission,
                    source: Source,
                    extension: String,
                    language: Language)

  trait State {
    case class Waiting(submission: Submission) extends State
    case class Judging(submission: Submission) extends State

    trait Judged extends State
    case class Correct(submission: Submission) extends Judged
    case class Incorrect(submission: Submission,
                         reason: String,
                         output: Map[ProblemCase, String],
                         diff: Map[ProblemCase, String]) extends Judged

    def submission: Submission
  }
}