package me.axiometry.judgementalist

case class Submission(participant: Participant,
                      problem: Problem,
                      contest: Option[Contest],
                      date: Long,
                      source: String,
                      filetype: String)

object Submission {
  trait State {
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